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
import smartcar.SmartMapInterface;
import smartcar.core.Point;
import smartcar.core.SystemCoreData;
import smartcar.core.SystemProperty;
import smartcar.map.SmartMapData;

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

    private SmartMapInterface map;
    private Navigator navigator;
    private SensorUltrasonic sensorUltrasonic;
    private QRCode qrCode;
    private Timer controlerrtTimer;
    private Point destination;
    private boolean needToSchedulePath = false;
    private SmartMapData scheduledPath;

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
        navigator = new Navigator();
        sensorUltrasonic = new SensorUltrasonic();
        qrCode = new QRCode();

        //连接事件处理程序
        sensorUltrasonic.addSenserListener(sensorUltrasonicListener);
        qrCode.addSenserListener(qrCodeListener);

        //启动controller线程
        controlerrtTimer = new Timer("Controller");
        controlerrtTimer.scheduleAtFixedRate(this, 0, 1000 / RunFrequency);
        logger.info("starting Controller TimerTask ");
    }

    @Override
    public Point getCarCurrentLocation() {
        return SystemCoreData.getLocation();
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
        //规划路径
        logger.info("Enter autoDriveDealer");
        if (needToSchedulePath) {
            logger.info("Schedule Path");
            needToSchedulePath =false;
            Point currentLocation=SystemCoreData.getLocation();
            scheduledPath = map.getPath(currentLocation, destination);
        }
        
        
    }
    /**
     * 旋转小车到一定角度，deviation指明了旋转的正负误差
     * @param angular
     * @param deviation 
     */
    private void rotateToAngular(float angular,float deviation){
        
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
        logger.info("Change to Auto Drive Mode");
        driveMode = DriveModeType.AUTO;
        this.destination = destination;
        needToSchedulePath = true;

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
