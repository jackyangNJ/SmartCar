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
import smartcar.Interactor.InteractorIf;
import smartcar.motor.Motor;
import smartcar.Navigator.NavigatorIf;
import smartcar.Sensor.QRCode;
import smartcar.Sensor.SensorUltrasonic;
import smartcar.Sensor.SensorUltrasonicData;
import smartcar.map.SmartMapInterface;
import smartcar.core.Point;
import smartcar.core.SystemCoreData;
import smartcar.core.SystemProperty;
import smartcar.core.Utils;
import smartcar.map.SmartMapData;

/**
 *
 * @author jack
 */
public class ControllerImpl extends TimerTask implements NavigatorListener, Controller {

    private enum DriveModeType {

        AUTO, MANUAL
    };

    /**
     * DriveStrategy 中，SIMPLE是在小车的行驶过程中，不会转弯，意即直来直去，只会在静止的时候转动角度，
     * COMPLICATE则组合以上策略，会采用斜着走的方式
     */
    enum DriveStrategyType {

        SIMPLE, COMPLICATE
    }
    static Log logger = LogFactory.getLog(ControllerImpl.class.getName());
    /**
     * 默认进入手动驾驶模式
     */
    DriveModeType driveMode = DriveModeType.MANUAL;
    
    /**
     * 默认采用简单行驶策略
     */
    DriveStrategyType driveStrategy = DriveStrategyType.SIMPLE;

    private final SmartMapInterface map;
    private final NavigatorIf navigator;
    private final SensorUltrasonic sensorUltrasonic;
    private final QRCode qrCode;
    private final Timer controlerrtTimer;
    private Point destination;
    private SmartMapData scheduledPath;

    /**
     * constants
     */
    private static final int eventCheckFrequency = Integer.parseInt(SystemProperty.getProperty("Controller.RunFrequency"));
    private static final double positionDeviation = Double.parseDouble(SystemProperty.getProperty("Controller.PositionDeviation"));
    private static final double angleDeviation = Double.parseDouble(SystemProperty.getProperty("Controller.AngleDeviation"));

    /**
     * 超声波事件处理函数
     */
    private final SensorListener sensorUltrasonicListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            SensorUltrasonicData sensorUltrasonicData = (SensorUltrasonicData) e.getData();
            double dis1 = sensorUltrasonicData.getDistance1();
            double dis2 = sensorUltrasonicData.getDistance2();
            double dis3 = sensorUltrasonicData.getDistance3();
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

        sensorUltrasonic = new SensorUltrasonic();
        qrCode = new QRCode();

        //更新系统状态
        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);

        //建立navigator,并校正传感器数据
        navigator = new Navigator(map);
        navigator.calibrateSensors();
        logger.info("calibrate sensors complete!");

        //连接事件处理程序
        sensorUltrasonic.addSenserListener(sensorUltrasonicListener);
        qrCode.addSenserListener(qrCodeListener);

        //启动controller线程
        controlerrtTimer = new Timer("Controller");
        controlerrtTimer.scheduleAtFixedRate(this, 0, 1000 / eventCheckFrequency);
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

        if (scheduledPath == null) {
            logger.info("Schedule Path");
            Point currentLocation = SystemCoreData.getLocation();
            scheduledPath = map.getPath(currentLocation, destination);
        }
        setMotorCmdAccordingSchedulePath();
    }

    /**
     *
     */
    private void setMotorCmdAccordingSchedulePath() {

        Point currentLocation = SystemCoreData.getLocation();

        //检查是否到终点
        if (Math.abs(Utils.getDistance(currentLocation, destination)) < positionDeviation) {
            logger.info("Reach Endpoint!!!");
            return;
        }

        //更新schedulePath,因为schedulePath是一个嵌套的数据结构
        if (Math.abs(Utils.getDistance(currentLocation, scheduledPath.getEndPoint())) < positionDeviation) {
            scheduledPath = scheduledPath.getChild();
        }

        //检查是否旋转车头
        if (driveStrategy == DriveStrategyType.SIMPLE) {
            double driveDirection = Utils.getAngle(currentLocation, scheduledPath.getEndPoint());
            if (Math.abs(driveDirection - SystemCoreData.getAngle()) > angleDeviation) {
                rotateToAbsoluteAngle(driveDirection, angleDeviation);
            }
            Motor.set_go();
        } else {
            /**
             * TODO
             */
        }
    }

    /**
     * 检查小车当前位置是否存在二维码信息
     *
     * @return
     */
    private boolean checkQRCode() {
        return false;
    }

    /**
     * 旋转小车到一定角度，deviation指明了旋转的正负误差, 当小车旋转角度的偏差在误差值内时，停止旋转，
     * 这里旋转的策略总是往旋转最小角度的方向旋转
     *
     * @param angle 表示绝对旋转角度，而非相对角度
     * @param deviation
     */
    public void rotateToAbsoluteAngle(double angle, double deviation) {
        while (Math.abs(SystemCoreData.getAngle() - angle) > deviation) {
            if (SystemCoreData.getAngle() > angle) {
                if (Math.abs(SystemCoreData.getAngle() - angle) < 180) {
                    Motor.set_clockwise();
                } else {
                    Motor.set_counterclockwise();
                }
            } else if (Math.abs(SystemCoreData.getAngle() - angle) < 180) {
                Motor.set_counterclockwise();
            } else {
                Motor.set_clockwise();
            }
        }
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
        scheduledPath = null;
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

    public void setOperation(int op) {
        if (op == InteractorIf.FORWARD) {
            logger.info("setOperation:FORWARD");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GOFORWARD);
            setCar(50, 0);
            return;
        }
        
        if (op == InteractorIf.BACK) {
            logger.info("setOperation:BACK");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GOBACK);
            setCar(-50, 0);
            return;
        }
        if (op == InteractorIf.LEFT) {
            logger.info("setOperation:LEFT");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GOLEFT);
            setCar(50, -90);
            return;
        }
        if (op == InteractorIf.RIGHT) {
            logger.info("setOperation:RIGHT");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GORIGHT);
            setCar(50, 90);
            return;
        }
        if (op == InteractorIf.CLOCKWISE) {
            logger.info("setOperation:CLOCKWISE");
            SystemCoreData.setSystemState(SystemCoreData.STATE_CLOCKWISE);
            setCarClockwise();
            return;
        }
        if (op == InteractorIf.COUNTERCLOCKWISE) {
            logger.info("setOperation:COUNTERCLOCKWISE");
            SystemCoreData.setSystemState(SystemCoreData.STATE_COUNTERCLOCKWISE);
            setCarCounterClockwise();
            return;
        }
        if (op == InteractorIf.STOP) {
            logger.info("setOperation:STOP");
            SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);
            setCar(0, 0);
            return;
        }
    }
}
