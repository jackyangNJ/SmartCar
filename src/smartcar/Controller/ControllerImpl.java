package smartcar.Controller;

import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.map.SmartMap;
import smartcar.Navigator.Navigator;
import smartcar.Event.NavigatorEvent;
import smartcar.Event.NavigatorListener;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Motor;
import smartcar.Sensor.QRCode;
import smartcar.Sensor.SensorUltrasonic;
import smartcar.Sensor.SensorUltrasonicData;
import smartcar.core.Point;
import smartcar.core.SystemCoreData;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class ControllerImpl extends TimerTask implements NavigatorListener, Controller {

    private enum DriveModeType {

        AUTO, MANUAL
    };
    
    public static Log logger = LogFactory.getLog(ControllerImpl.class.getName());
    private DriveModeType driveMode = DriveModeType.AUTO;

    private SmartMap map;
    private Navigator navigator;
    private SensorUltrasonic sensorUltrasonic;
    private QRCode qrCode;
    private Timer controlerrtTimer;
    private Point destination;
    
    //Parameters 
    private static int RunFrequency = Integer.parseInt(SystemProperty.getProperty("Controller.RunFrequency"));
    /**
     * 超声波事件处理函数
     */
    private final SensorListener sensorUltrasonicListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            SensorUltrasonicData sensorUltrasonicData = (SensorUltrasonicData) e.getData();
            float dis1 = sensorUltrasonicData.getDistance1();
            float dis2 = sensorUltrasonicData.getDistance2();
            float dis3 = sensorUltrasonicData.getDistance3();
            logger.info("Receive Ultrasonic Data: 1:" + dis1 + " 2 :" + dis2 + " 3: " + dis3);

        }
    };
    /**
     * 二维码事件处理程序
     */
    private final SensorListener qrCodeListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    public ControllerImpl(SmartMap map) {
        this.map = map;
        navigator = new Navigator(map);
        sensorUltrasonic = new SensorUltrasonic();
        qrCode = new QRCode();

        //连接事件处理程序
        sensorUltrasonic.addSenserListener(sensorUltrasonicListener);
        qrCode.addSenserListener(qrCodeListener);

        //启动controller线程
        controlerrtTimer = new Timer("Controller");
        controlerrtTimer.scheduleAtFixedRate(this, 0, 1000 / RunFrequency);
    }

    @Override
    public Point getCarCurrentLocation() {
        Point location = new Point(SystemCoreData.getX(), SystemCoreData.getY());
        return location;
    }

    @Override
    public void setCar(int speed, int angle) {
        Motor.smart_car_set(speed, angle);
    }

    @Override
    public void setCarClockwise() {
        Motor.set_clockwise();
    }

    @Override
    public void setCarCounterClockwise() {
        Motor.set_counterclockwise();
    }

    @Override
    public void run() {
        if (driveMode == DriveModeType.AUTO) {
            autoDriveDealer();
        } else {
            mannualDriveDealer();
        }

    }

    private void autoDriveDealer() {

    }

    private void mannualDriveDealer() {

    }

    /**
     * 设置小车自动驾驶的目的地
     *
     * @param destination
     */
    @Override
    public void setCarAutoDriveDestination(Point destination) {
        driveMode = DriveModeType.AUTO;
        this.destination = destination;
    }

    /**
     * Navigator事件处理程序
     *
     * @param e
     */
    @Override
    public void NavigatorEventProcess(NavigatorEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
