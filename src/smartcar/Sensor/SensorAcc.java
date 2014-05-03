package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core;
import smartcar.Event.SensorListener;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_video.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.core.SystemCoreData;
import spiLib.SPIFunc;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class SensorAcc implements SensorAccIf {

    public static Log logger = LogFactory.getLog(SensorAcc.class);
    int state = ON_TIMER_RUNNING;
    /**
     * 表明sensor 在执行定时任务
     */
    static final int ON_TIMER_RUNNING = 0;

    /**
     * 表明Sensor没有执行定时任务
     */
    static final int ON_TIMER_STOP = 1;

    private ArrayList<SensorListener> SensorListeners;
    Timer timer = new Timer("Acc");

    

    public CvKalman kalman;
    private CvMat z_k;
    private CvMat xy_axle;
    
    /**
     * accData is the data after kalman filter
     */
    SensorAccData accData;
    /**
     * rawData is the data acquired from sensor
     */
    SensorAccData rawData;
    /**
     * meanData is mean value after calibration
     */
    SensorAccData meanData;
    SPIFunc spi;

    /**
     * constant
     */
    public static final int frequency = Integer.parseInt(SystemProperty.getProperty("ACC.Frequency"));
    private final String devicePath = SystemProperty.getProperty("ACC.DevFile");
    private static final int calibrationDataNum = Integer.parseInt(SystemProperty.getProperty("ACC.CalibrateDataNum"));
    private static final int spiFrenquency = Integer.parseInt(SystemProperty.getProperty("ACC.SPI.Frequency"));
    public static final double deltaT = (float)1/frequency;
    
    
    
    /**
     * timertask
     */
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            logger.debug("timer triggered");
            if (state == ON_TIMER_RUNNING) {
                readAccData();
                rawData = calibrateRawData(rawData, meanData);
                accData = kalmanData(rawData);
                fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_ACC_TYPE, accData));
            }
        }
    };
    
    public SensorAcc() {
        //init SPI function
        spi = new SPIFunc(devicePath,spiFrenquency);
        //config sensor ADXL362
        sensorConfig();

        accData = new SensorAccData();
        rawData = new SensorAccData();
        meanData = new SensorAccData();
        
        //init Kalman filter
        initKalmanFilter();

        //start timely work
        timer.scheduleAtFixedRate(task, 0, 1000 / frequency);
    }

    /**
     * This is a 6 dimention Kalamn struct,including posX,posY,vX,vY,accX,accY
     */
    private void initKalmanFilter() {
        kalman = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(6, 2, 0);
        z_k = CvMat.create(2, 1, opencv_core.CV_32FC1);

        //initial transition matrix,6x6,对角线赋值为1
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (i != j) {
                    kalman.transition_matrix().put(i, j, 0);
                } else {
                    kalman.transition_matrix().put(i, j, 1);
                }
            }
        }
        kalman.transition_matrix().put(0, 2, deltaT);
        kalman.transition_matrix().put(1, 3, deltaT);
        kalman.transition_matrix().put(2, 4, deltaT);
        kalman.transition_matrix().put(3, 5, deltaT);

        //initial measurement matric,2x6
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                kalman.measurement_matrix().put(i, j, 0);
            }
        }
        kalman.measurement_matrix().put(0, 4, 1);
        kalman.measurement_matrix().put(1, 5, 1);

        //initial cov parameter
        opencv_core.cvSetIdentity(kalman.process_noise_cov(), opencv_core.cvRealScalar(1e-5));
        opencv_core.cvSetIdentity(kalman.measurement_noise_cov(), opencv_core.cvRealScalar(1e-5));
        opencv_core.cvSetIdentity(kalman.error_cov_post(), opencv_core.cvRealScalar(1));
    }

    private void sensorConfig() {
        //Write 0x52 to soft reset register,to reset softly
        write((byte) 0x1f, (byte) 0x52);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        logger.info("Sensor acc reset softly");

//        write((byte) 0x26, (byte) 0x20);
//        write((byte) 0x2c, (byte) 0x13);
        //start acc measurements
        byte temp = (byte) this.read((byte) 0x2d);
        temp = (byte) (temp | 0x02);
        write((byte) 0x2d, temp);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        logger.info("Sensor acc start measuring");

        checkAllControlRegs();
    }

    private void readAccData() {
        readxalxe();
        readyalxe();
        readzalxe();
    }

    private void readzalxe() {
        byte dataLow;
        byte dataHigh;
        dataLow = (byte) this.read((byte) 0x12);
        dataHigh = (byte) this.read((byte) 0x13);
        int value = (dataHigh << 8)+ dataLow;
//        int value = (byte)this.read((byte)0x0A);
        logger.info("z:"+value);
//        rawData.seta_x(value);
    }

    private void readxalxe() {
        int xalxeLow;
        int xalxeHigh;
        xalxeLow = this.read((byte) 0x0e);
        xalxeHigh = this.read((byte) 0x0f);
        int value = (xalxeHigh << 8) + xalxeLow;
//        int value = (byte) this.read((byte)0x08);
//        logger.info(value);
        rawData.seta_x(value);
    }

    private void readyalxe() {
        int yalxeLow;
        int yalxeHigh;
        yalxeLow =  this.read((byte) 0x10);
        yalxeHigh = this.read((byte) 0x11);
        int value = (yalxeHigh << 8)+ yalxeLow;
//        int value =  (byte) this.read((byte) 0x09);
        logger.info(value);
        rawData.seta_y(value);
    }

    private byte read(byte addr) {
        byte[] input = new byte[3];
        input[0] = 0x0B;
        input[1] = addr;
        input[2] = 0x00;
        spi.RWBytes(input, 3);
        return input[2];
    }

    private void write(byte addr, byte value) {
        byte[] output = new byte[3];
        output[0] = 0x0A;
        output[1] = addr;
        output[2] = value;
        spi.RWBytes(output, 3);
    }

    private SensorAccData kalmanData(SensorAccData sensoraccdata) {
        SensorAccData accdata = new SensorAccData(0, 0, 0, 0, 0, 0);
        xy_axle = opencv_video.cvKalmanPredict(kalman, null);
        z_k.put(0, 0, sensoraccdata.geta_x());
        z_k.put(1, 0, sensoraccdata.geta_y());
        opencv_video.cvKalmanCorrect(kalman, z_k);
        accdata.seta_x((float) xy_axle.get(4, 0));
        accdata.seta_y((float) xy_axle.get(5, 0));
        accdata.setv_x((float) xy_axle.get(2, 0));
        accdata.setv_y((float) xy_axle.get(3, 0));
        accdata.setx((float) xy_axle.get(0, 0));
        accdata.sety((float) xy_axle.get(1, 0));
        return accdata;
    }

    private void checkAllControlRegs() {
        logger.info("Reg 0x20 " + read((byte) 0x20));
        logger.info("Reg 0x21 " + read((byte) 0x21));
        logger.info("Reg 0x22 " + read((byte) 0x22));
        logger.info("Reg 0x23 " + read((byte) 0x23));
        logger.info("Reg 0x24 " + read((byte) 0x24));
        logger.info("Reg 0x25 " + read((byte) 0x25));
        logger.info("Reg 0x26 " + read((byte) 0x26));
        logger.info("Reg 0x27 "  + read((byte) 0x27));
        logger.info("Reg 0x28 " + read((byte) 0x28));
        logger.info("Reg 0x29 " + read((byte) 0x29));
        logger.info("Reg 0x2A " + read((byte) 0x2A));
        logger.info("Reg 0x2B " + read((byte) 0x2B));
        logger.info("Reg 0x2C " + read((byte) 0x2C));
        logger.info("Reg 0x2D " + read((byte) 0x2D));
        logger.info("Reg 0x2E " + read((byte) 0x2E));
    }

    @Override
    public SensorAccData getSensorRawData() {
        return this.rawData;
    }

    @Override
    public SensorAccData getSensorData() {
        return this.accData;
    }

    @Override
    public synchronized void addSenserListener(SensorListener listener) {

        if (SensorListeners == null) {
            SensorListeners = new ArrayList<>(2);
        }
        if (!SensorListeners.contains(listener)) {
            SensorListeners.add(listener);
        }
    }

    @Override
    public synchronized void removeSenserListener(SensorListener listener) {
        if ((SensorListeners != null) && SensorListeners.contains(listener)) {
            SensorListeners.remove(listener);
        }
    }

    private void fireSensorEventProcess(SensorEvent e) {
        logger.debug("fire Sensor event");
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
     * 用于在静止时矫正传感器的数据，系统状态SystemCoreData.state应处于静止
     */
    @Override
    public void calibrate() {
        //wait until car is still
        while (SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL) {

        }
        //pause timer task
        state = ON_TIMER_STOP;
        ArrayList<SensorAccData> dataList = new ArrayList<>(calibrationDataNum);
        for (int i = 0; i < calibrationDataNum; i++) {
            getSensorRawData();
            dataList.add(new SensorAccData(0, 0, 0, 0, rawData.geta_x(), rawData.geta_y()));
            
            try {
                Thread.sleep(1000/frequency);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }
        meanData = getMeanGyroData(dataList);
        state = ON_TIMER_RUNNING;
    }

    /**
     * 获取矫正后的数据，即用rawData中的测量加速度减去meanData中的平均加速度
     *
     * @param rawData
     * @param meanData
     * @return 返回矫正后的传感器测量值
     */
    private SensorAccData calibrateRawData(SensorAccData rawData, SensorAccData meanData) {
        SensorAccData newData = new SensorAccData();
        newData.seta_x(rawData.geta_x() - meanData.geta_x());
        newData.seta_y(rawData.geta_y() - meanData.geta_y());
        return newData;
    }

    /**
     * 获取datalist中数据的均值
     *
     * @param dataList
     * @return 存有均值的SensorAccData
     */
    private SensorAccData getMeanGyroData(List<SensorAccData> dataList) {
        int size = dataList.size();
        SensorAccData data = new SensorAccData();
        for (SensorAccData sensorAccData : dataList) {
            data.seta_x(sensorAccData.geta_x() / size + data.geta_x());
            data.seta_y(sensorAccData.geta_y() / size + data.geta_y());
        }
        return data;
    }
}
