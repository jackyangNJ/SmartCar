package smartcar.Sensor;

import java.io.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.core.SystemProperty;

/**
 *
 * @author cshuo
 */
public class SensorUltrasonic implements SensorUltrasonicIf {

    public static Log logger = LogFactory.getLog(SensorUltrasonic.class);
    private ArrayList<SensorListener> SensorListeners;
    private SensorUltrasonicData UltrasonicData;
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
        UltrasonicData = new SensorUltrasonicData();
        //timer.scheduleAtFixedRate(task, 0, Frequency);
//        timer.schedule(task, 0, 1000);
    }

    /**
     * 触发超声波传感器发出探测脉冲， 一直等到是yes才能读数据，？？？？一直不是yes,陷于循环？？？(是否限时)
     *
     * @throws java.lang.InterruptedException
     */
    public void trigger() throws InterruptedException {
        char[] bufRead = new char[10];

        try {            //向trigger文件中写1
            FileWriter fw = new FileWriter(triggerFilePath);
            fw.write("1\n");
            fw.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        //直到从trigger文件读到yes，数据才准备好
        try {
            FileReader fr = new FileReader(triggerFilePath);
            fr.read(bufRead);
            logger.info(new String(bufRead));
            int i;
            while (!new String(bufRead, 0, 3).equals("yes")) {
                fr = new FileReader(triggerFilePath);                
                i = fr.read(bufRead);
                fr.close();
                Thread.sleep(1);
            }
            fr.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    /**
     * 从distance文件中获取测量数据
     */
    public void getDistance() throws InterruptedException {
        char[] disRead = new char[20];
        long dis_cnt1 = 0, dis_cnt2 = 0, dis_cnt3 = 0;                            //time ulwave giving back

        trigger();    //触发之；

        try {            //读取三个distance文件的数值
            FileReader fr_dis1 = new FileReader(distance1FilePath);
            FileReader fr_dis2 = new FileReader(distance2FilePath);
            FileReader fr_dis3 = new FileReader(distance3FilePath);
            fr_dis1.read(disRead);
//            System.out.println("distance1 is: " + new String(disRead));
            String[] disarray = new String(disRead).split(" ");
            dis_cnt1 = Integer.parseInt(disarray[0].trim());
//            System.out.println("distance1 is: " + dis_cnt1);

            fr_dis2.read(disRead);
//            System.out.println("distance2 is: " + new String(disRead));
            disarray = new String(disRead).split(" ");
            dis_cnt2 = Integer.parseInt(disarray[0].trim());
//            System.out.println("distance1 is: " + dis_cnt2);

            fr_dis3.read(disRead);
//            System.out.println("distance3 is: " + new String(disRead));
            disarray = new String(disRead).split(" ");
            dis_cnt3 = Integer.parseInt(disarray[0].trim());
//            System.out.println("distance1 is: " + dis_cnt3);
            fr_dis1.close();
            fr_dis1.close();
            fr_dis1.close();
        } catch (IOException ex) {

        }

        //单位(m)
        UltrasonicData.setDistance1(dis_cnt1 * 170 / 1000000000);
        UltrasonicData.setDistance2(dis_cnt2 * 170 / 1000000000);
        UltrasonicData.setDistance3(dis_cnt3 * 170 / 1000000000);
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
        SensorUltrasonicData sensorUltrasonicData = new SensorUltrasonicData();
        sensorUltrasonicData.setDistance1(UltrasonicData.getDistance1());
        sensorUltrasonicData.setDistance2(UltrasonicData.getDistance2());
        sensorUltrasonicData.setDistance3(UltrasonicData.getDistance3());
        //传送数据之后，将储存的数据置为最大
//        UltrasonicData.setDistance1(3.0f);
//        UltrasonicData.setDistance1(3.0f);
//        UltrasonicData.setDistance1(3.0f);

        return sensorUltrasonicData;
    }

}
