package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core;
import smartcar.Event.SensorListener;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_video.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import spiLib.SPIFunc;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class SensorAcc implements SensorAccIf {

    public static Log logger = LogFactory.getLog(SensorAcc.class);
    private ArrayList<SensorListener> SensorListeners;
    Timer timer = new Timer("Acc");

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            readAccData();
            fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_ACC_TYPE, getSensorRawData()));
        }
    };

    private CvKalman kalman;
    private CvMat z_k;
    private CvMat xy_axle;
    public static final double time = 0.01;
    private SensorAccData data;
    private SPIFunc spi;

    /**
     * constant
     */
    public static final int frequency = Integer.parseInt(SystemProperty.getProperty("ACC.Frequency"));
    private final String routePath = SystemProperty.getProperty("ACC.DevFile");

    public SensorAcc() {
        //init SPI function
        spi = new SPIFunc(routePath);
        //config sensor ADXL362
        sensorConfig();

        data = new SensorAccData(0, 0, 0, 0, 0, 0);
        //start timely work
        timer.scheduleAtFixedRate(task, 0, frequency);

        //init Kalman filter
        initKalmanFilter();
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
        kalman.transition_matrix().put(0, 2, time);
        kalman.transition_matrix().put(1, 3, time);
        kalman.transition_matrix().put(2, 4, time);
        kalman.transition_matrix().put(3, 5, time);

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
    }

    private void readxalxe() {
        byte xalxeLow;
        byte xalxeHigh;
        xalxeLow = (byte) this.read((byte) 0x0e);
        xalxeHigh = (byte) this.read((byte) 0x0f);
        int value = xalxeHigh << 8 | xalxeLow;
        data.seta_x(value);
    }

    private void readyalxe() {
        byte yalxeLow;
        byte yalxeHigh;
        yalxeLow = (byte) this.read((byte) 0x10);
        yalxeHigh = (byte) this.read((byte) 0x11);
        int value = yalxeHigh << 8 | yalxeLow;
        data.seta_y(value);
    }

    private byte read(byte addr) {
        byte[] input = new byte[3];
        input[0] = 0x0b;
        input[1] = addr;
        input[2] = 0x00;
        spi.RWBytes(input, 3);
        return input[2];
    }

    private void write(byte addr, byte value) {
        byte[] output = new byte[3];
        output[0] = 0x0a;
        output[1] = addr;
        output[2] = value;
        spi.RWBytes(output, 3);
    }

    public SensorAccData kalmanData(SensorAccData sensoraccdata) {
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
        logger.info("Reg 0x20" + read((byte) 0x20));
        logger.info("Reg 0x21" + read((byte) 0x21));
        logger.info("Reg 0x22" + read((byte) 0x22));
        logger.info("Reg 0x23" + read((byte) 0x23));
        logger.info("Reg 0x24" + read((byte) 0x24));
        logger.info("Reg 0x25" + read((byte) 0x25));
        logger.info("Reg 0x26" + read((byte) 0x26));
        logger.info("Reg 0x27" + read((byte) 0x27));
        logger.info("Reg 0x28" + read((byte) 0x28));
        logger.info("Reg 0x29" + read((byte) 0x29));
        logger.info("Reg 0x2A" + read((byte) 0x2A));
        logger.info("Reg 0x2B" + read((byte) 0x2B));
        logger.info("Reg 0x2C" + read((byte) 0x2C));
        logger.info("Reg 0x2D" + read((byte) 0x2D));
        logger.info("Reg 0x2E" + read((byte) 0x2E));
    }

    @Override
    public SensorAccData getSensorRawData() {
        return this.data;
    }

    @Override
    public SensorAccData getSensorData() {
        return this.kalmanData(data);
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
        logger.info("fire Sensor event");
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
}
