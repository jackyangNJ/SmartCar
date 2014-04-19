package smartcar.Controller;

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

/**
 *
 * @author jack
 */
public class ControllerImpl implements NavigatorListener, Controller, Runnable {

    public static Log logger = LogFactory.getLog(ControllerImpl.class.getName());

    private enum DriveModeType {

        AUTO, MANUAL
    };
    private DriveModeType driveMode = DriveModeType.AUTO;

    private SmartMap map;
    private Navigator navigator;
    private SensorUltrasonic sensorUltrasonic;
    private QRCode qrCode;
    //Parameters 

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 设置小车自动驾驶的目的地
     *
     * @param location
     */
    @Override
    public void setCarAutoDriveDestination(Point location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Navigator事件处理程序
     * @param e 
     */
    @Override
    public void NavigatorEventProcess(NavigatorEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
