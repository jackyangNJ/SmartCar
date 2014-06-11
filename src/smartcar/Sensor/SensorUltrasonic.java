package smartcar.Sensor;

import java.io.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.core.SystemProperty;
import smartcar.core.Utils;

/**
 *
 * @author cshuo
 */
public class SensorUltrasonic implements SensorUltrasonicIf, SensorListener {

    public static Log logger = LogFactory.getLog(SensorUltrasonic.class);
    private ArrayList<SensorListener> SensorListeners;
    private SensorUltrasonicData ultrasonicData;
    public static final String triggerFilePath = SystemProperty.getProperty("Ultrasonic.File.trigger");
    public static final String distance1FilePath = SystemProperty.getProperty("Ultrasonic.File.distance1");
    public static final String distance2FilePath = SystemProperty.getProperty("Ultrasonic.File.distance2");
    public static final String distance3FilePath = SystemProperty.getProperty("Ultrasonic.File.distance3");
    /*unit:ms;  40Hz*/
    public static final int Frequency = 2500;

    private final Timer timer = new Timer("ultro");
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            try {
                trigger();
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
            try {
                getDistance();
            } catch (InterruptedException ex) {
                logger.error(ex);
            }

            fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_ULTRASONIC_TYPE, getData()));
        }
    };

    public SensorUltrasonic() {
        ultrasonicData = new SensorUltrasonicData();
    }

    /**
     * 触发超声波传感器发出探测脉冲， 一直等到是yes才能读数据，？？？？一直不是yes,陷于循环？？？(是否限时)
     *
     * @throws java.lang.InterruptedException
     */
    public void trigger() throws InterruptedException {
        try {            //向trigger文件中写1
            FileWriter fw = new FileWriter(triggerFilePath);
            fw.write("1\n");
            fw.close();
        } catch (IOException ex) {
            logger.error(ex);
        }

        //直到从trigger文件读到yes，数据才准备好
        String resultString;
        resultString = Utils.excuteSysCommand("cat " + triggerFilePath);
        logger.info(resultString);
        while (!resultString.equals("yes")) {
            resultString = Utils.excuteSysCommand("cat " + triggerFilePath);
            Utils.delay(10);
        }
    }

    /**
     * 从distance文件中获取测量数据
     *
     * @throws java.lang.InterruptedException
     */
    public void getDistance() throws InterruptedException {
        String resultString;
        long dis_cnt1 = 0, dis_cnt2 = 0, dis_cnt3 = 0;                            //time ulwave giving back

        trigger();    //触发之；

        //读取三个distance文件的数值
        resultString = Utils.excuteSysCommand("cat " + distance1FilePath);
        String[] disarray = resultString.split(" ");
        dis_cnt1 = Integer.parseInt(disarray[0].trim());
        logger.debug("distance3 is: " + dis_cnt1);

        resultString = Utils.excuteSysCommand("cat " + distance2FilePath);
        disarray = resultString.split(" ");
        dis_cnt2 = Integer.parseInt(disarray[0].trim());
        logger.debug("distance3 is: " + dis_cnt2);

        resultString = Utils.excuteSysCommand("cat " + distance3FilePath);
        disarray = resultString.split(" ");
        dis_cnt3 = Integer.parseInt(disarray[0].trim());
        logger.debug("distance3 is: " + dis_cnt3);

        //单位(m)
        ultrasonicData.setDistance1((double) dis_cnt1 * 170 / 1000000000);
        ultrasonicData.setDistance2((double) dis_cnt2 * 170 / 1000000000);
        ultrasonicData.setDistance3((double) dis_cnt3 * 170 / 1000000000);
    }

    /**
     * trigger the SensorEvent
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
     * 获取Ultrosonic测得三个距离数据
     *
     * @return
     */
    @Override
    public SensorUltrasonicData getData() {
        return ultrasonicData;
    }

    /**
     * update ultrasonic data
     * @param e 
     */
    @Override
    public void SensorEventProcess(SensorEvent e) {
        byte[] buffer = (byte[]) e.getData();
        //get rid of msg head ang tail ,and parse double
        double cm = Double.parseDouble(new String(buffer, 1, buffer.length - 2));
        double distance = cm / 100.0;
        ultrasonicData.setDistance1(distance);
        fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_ULTRASONIC_TYPE,ultrasonicData));
    }

}
