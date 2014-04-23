package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import static com.googlecode.javacv.cpp.opencv_core.cvRealScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSetIdentity;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_video.CvKalman;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.core.SystemProperty;
import spiLib.SPIFunc;

/**
 *
 * @author cs
 */
public class SensorGyro implements SensorGyroIf {

    public static Log logger = LogFactory.getLog(SensorGyro.class);
    private ArrayList<SensorListener> SensorListeners;
    private SPIFunc spifunc;
    private SensorGyroData gyroData;
    /**
     * constant
     */
    private final float UNIT = 8.75f;
    private final byte OUT_Z_L_addr = 0x2C;
    private final byte OUT_Z_H_addr = 0x2D;
    private final byte L3G4200D_CTRL_REG1 = 0x20;
    private final byte L3G4300D_ID = (byte) 0xd3;
    private final String routePath = SystemProperty.getProperty("Gyro.DevFile");
    private final int readFrequency = Integer.parseInt(SystemProperty.getProperty("Gyro.Frequency"));

    private CvMat z_k;
    private CvMat y_k;//预测值
    private CvKalman kalman;
    public static final double deltaT = 0.01;

    Timer timer = new Timer("gyro");
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            read_HoriAngleSpeed();
            gyroData = kalmanData(gyroData);
            fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_GYRO_TYPE, gyroData));
        }
    };

    public SensorGyro() {
        gyroData = new SensorGyroData();
        spifunc = new SPIFunc(routePath);
        timer.scheduleAtFixedRate(task, 0, readFrequency);

        //初始化kalman
        initKalmanFilter();

        //初始化sensor
        sensorConfig();
    }

    private void sensorConfig() {
        byte[] data = new byte[2];
        data[0] = L3G4200D_CTRL_REG1;
        data[1] = 0x0F;
        Gyr_write(data);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            logger.equals(ex);
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
        gyroData.setHori_angleSpeed((float) z * UNIT / 1000);
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
        y_k = opencv_video.cvKalmanPredict(kalman, null);//获取值
        z_k.put(0, 0, (GyroData.getHori_angleSpeed()));
        opencv_video.cvKalmanCorrect(kalman, z_k);
        speed.setHori_angle((float) y_k.get(0, 0));
        speed.setHori_angleSpeed((float) y_k.get(1, 0));
        return speed;
    }

    @Override
    public SensorGyroData getRawSensorGyroData() {
        return this.gyroData;
    }

}
