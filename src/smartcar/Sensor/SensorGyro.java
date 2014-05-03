package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import static com.googlecode.javacv.cpp.opencv_core.cvRealScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSetIdentity;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_video.CvKalman;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.core.SystemCoreData;
import smartcar.core.SystemProperty;
import spiLib.SPIFunc;

/**
 *
 * @author cs
 */
public class SensorGyro implements SensorGyroIf {

    private int state = ON_TIMER_RUNNING;

    /**
     * 表明sensor 在执行定时任务
     */
    static final int ON_TIMER_RUNNING = 0;

    /**
     * 表明Sensor没有执行定时任务
     */
    static final int ON_TIMER_STOP = 1;

    static Log logger = LogFactory.getLog(SensorGyro.class);
    ArrayList<SensorListener> SensorListeners;
    SPIFunc spifunc;
    /**
     * gyroData is the data after the kalman filter
     */
    SensorGyroData gyroData;
    /**
     * rawData is the data acquired from sensor
     */
    SensorGyroData rawData;
    /**
     * meanData is the mean data after the excution of calibration
     */
    SensorGyroData meanData;

    /**
     * constant
     */
    private final float UNIT = 8.75f;
    private final byte OUT_Z_L_addr = 0x2C;
    private final byte OUT_Z_H_addr = 0x2D;
    private final byte L3G4200D_CTRL_REG1 = 0x20;
    private final byte L3G4300D_ID = (byte) 0xd3;
    private static final String devicePath = SystemProperty.getProperty("Gyro.DevFile");
    private static final int readFrequency = Integer.parseInt(SystemProperty.getProperty("Gyro.Frequency"));
    private static final int calibrationDataNum = Integer.parseInt(SystemProperty.getProperty("GYRO.CalibrateDataNum"));
    private static final int spiFrequency = Integer.parseInt(SystemProperty.getProperty("Gyro.SPI.Frequency"));
    private CvMat z_k;
    //预测值
    private CvMat y_k;
    private CvKalman kalman;
    public static final double deltaT = (float)1/readFrequency;

    private final Timer timer = new Timer("gyro");
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (state == ON_TIMER_RUNNING) {
                read_HoriAngleSpeed();
                //calibrate data                
                rawData = calibrateRawData(rawData, meanData);
                gyroData = kalmanData(rawData);
                fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_GYRO_TYPE, gyroData));
            }
        }
    };

    public SensorGyro() {
        gyroData = new SensorGyroData();
        rawData = new SensorGyroData();
        meanData = new SensorGyroData();
        spifunc = new SPIFunc(devicePath,spiFrequency);
        

        //初始化kalman
        initKalmanFilter();

        //初始化sensor
        sensorConfig();
        
        timer.scheduleAtFixedRate(task, 0, 1000/readFrequency);
    }

    private void sensorConfig() {
        byte[] data = new byte[2];
        data[0] = L3G4200D_CTRL_REG1;
        data[1] = 0x0F;
        Gyr_write(data);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }

        if (!id_true()) {
            logger.error("get wrong sensor gyro ID");
            System.exit(-1);
        }
    }

    private void initKalmanFilter() {
        //创建kalman
        kalman = opencv_video.cvCreateKalman(2, 1, 0);

        z_k = CvMat.create(1, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
        z_k.put(0, 0, 1);

//        final float F[][] = {{1, (float) frequency}, {0, 1}};//时间会变化
        //initial transition matrix(2x2)
        kalman.transition_matrix().put(0, 0, 1);
        kalman.transition_matrix().put(0, 1, deltaT);
        kalman.transition_matrix().put(1, 0, 0);
        kalman.transition_matrix().put(1, 1, 1);

        //initial measurement matrix(1x2)
        kalman.measurement_matrix().put(0, 0, 0);
        kalman.measurement_matrix().put(0, 1, 1);

        cvSetIdentity(kalman.process_noise_cov(), cvRealScalar(1e-5));
        cvSetIdentity(kalman.measurement_noise_cov(), cvRealScalar(1e-5));
        cvSetIdentity(kalman.error_cov_post(), cvRealScalar(1));
    }

    /**
     * 读取水平角速度，单位 度/s
     */
    public void read_HoriAngleSpeed() {
        byte Z_L = gyr_read(OUT_Z_L_addr);
        byte Z_H = gyr_read(OUT_Z_H_addr);
        int z = Z_H << 8 | Z_L;        
        rawData.setHori_angleSpeed((float) z * UNIT / 1000);
    }

    /**
     * 读取指定地址寄存器的值
     *
     * @param addr
     * @return
     */
    public byte gyr_read(byte addr) {
        byte[] read_data = new byte[2];
        read_data[0] = (byte) ((addr | (3 << 6)) - (1 << 6));       //bit0置为1,bit1置为0
        spifunc.RWBytes(read_data, 2);
        return read_data[1];
    }

    /**
     * 向指定地址寄存器写值
     *
     * @param data
     */
    public void Gyr_write(byte[] data) {
        byte[] write_data = new byte[2];
        write_data[0] = (byte) ((data[0] | (3 << 6)) - (3 << 6));//   bit0，1置为0
        write_data[1] = data[1];
        spifunc.RWBytes(write_data, 2);
    }

    /**
     * 根据寄存器的值判断设备是否正确
     *
     * @return
     */
    private boolean id_true() {
        byte ID_Addr = 0x0f;
        return gyr_read(ID_Addr) == L3G4300D_ID;
    }

    /**
     * 触发监听事件
     *
     * @param e
     */
    private void fireSensorEventProcess(SensorEvent e) {
        ArrayList list;
        synchronized (this) {
            if (SensorListeners == null) {
                return;
            }
            list = (ArrayList) SensorListeners.clone();
        }
        for (int i = 0; i < list.size(); i++) {
            SensorListener listener = (SensorListener) list.get(i);
            listener.SensorEventProcess(e);
        }
    }

    /**
     * add SensorListener
     *
     * @param listener
     */
    @Override
    public void addSenserListener(SensorListener listener) {
        if (SensorListeners == null) {
            SensorListeners = new ArrayList<>(2);
        }
        if (!SensorListeners.contains(listener)) {
            SensorListeners.add(listener);
        }
    }

    /**
     * remove SensorListener
     *
     * @param listener
     */
    @Override
    public void removeSenserListener(SensorListener listener) {
        if ((SensorListeners != null) && SensorListeners.contains(listener)) {
            SensorListeners.remove(listener);
        }
    }

    /**
     * 获取处理后的数据
     *
     * @return
     */
    @Override
    public SensorGyroData getSensorGyroData() {
        return gyroData;
    }

    public SensorGyroData kalmanData(SensorGyroData GyroData) {
        SensorGyroData speed = new SensorGyroData();
        //获取值
        y_k = opencv_video.cvKalmanPredict(kalman, null);
        z_k.put(0, 0, (GyroData.getHori_angleSpeed()));
        opencv_video.cvKalmanCorrect(kalman, z_k);
        speed.setHori_angle((float) y_k.get(0, 0));
        speed.setHori_angleSpeed((float) y_k.get(1, 0));
        return speed;
    }

    @Override
    public SensorGyroData getRawSensorGyroData() {
        return this.rawData;
    }

    /**
     * 用于在静止时矫正传感器的数据，系统状态SystemCoreData.state应处于静止 数据的平均值会存在meanData中
     */
    @Override
    public void calibrate() {
        //wait until car is still
        while (SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL) {            
        }
        //pause timer task
        logger.debug("calibrate starting!!!!!!!");
        setState(ON_TIMER_STOP);
        ArrayList<SensorGyroData> dataList = new ArrayList<>(calibrationDataNum);
        for (int i = 0; i < calibrationDataNum; i++) {            //timeval      
            read_HoriAngleSpeed();
            dataList.add(new SensorGyroData(rawData.getHori_angleSpeed(), 0));
            
            try {
                Thread.sleep(1000/readFrequency);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
               
        }
        meanData = getMeanGyroData(dataList);     
        setState(ON_TIMER_RUNNING);
        
    }

    /**
     * 获取datalist中数据的均值
     *
     * @param dataList
     * @return 存有均值的SensorAccData
     */
    private SensorGyroData getMeanGyroData(List<SensorGyroData> dataList) {
        int size = dataList.size();
        SensorGyroData data = new SensorGyroData();
        for (SensorGyroData sensorGyroData : dataList) {
            data.setHori_angleSpeed(sensorGyroData.getHori_angleSpeed() / size + data.getHori_angleSpeed());
        }
        return data;
    }

    /**
     * 获取矫正后的数据，即用rawData中的测量加速度减去meanData中的平均加速度
     *
     * @param rawData
     * @param meanData
     * @return 返回矫正后的传感器测量值
     */
    private SensorGyroData calibrateRawData(SensorGyroData rawData, SensorGyroData meanData) {        
        SensorGyroData newData = new SensorGyroData();
        newData.setHori_angleSpeed(rawData.getHori_angleSpeed() - meanData.getHori_angleSpeed());
//        newData.setHori_angle(rawData.getHori_angle());
        return newData;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }
    
}
