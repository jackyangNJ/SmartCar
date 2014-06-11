package smartcar.Controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.NavigatorEvent;
import smartcar.Event.NavigatorListener;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Navigator.Navigator;
import smartcar.Sensor.SensorQRCode;
import smartcar.Sensor.SensorUltrasonic;
import smartcar.Sensor.SensorUltrasonicData;
import smartcar.core.Point;
import smartcar.core.SystemCoreData;
import smartcar.core.SystemProperty;
import smartcar.core.Utils;
import smartcar.map.SmartMap;
import smartcar.map.SmartMapBarrier;
import smartcar.map.SmartMapData;
import smartcar.map.SmartMapInfo;
import smartcar.map.SmartMapInterface;
import smartcar.map.SmartMapQRCode;
import smartcar.map.SmartMapQRCodeInfo;
import smartcar.motor.Motor;
import smartcar.motor.Yuntai;
import smartcar.thrift.CarOperation;

/**
 *
 * @author jack
 */
public class ControllerImpl extends TimerTask implements NavigatorListener, Controller {

    private enum DriveModeType {

        AUTO, MANUAL
    };

    boolean flag = false;

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

    private final Navigator navigator;
    private SensorUltrasonic sensorUltrasonic = null;
    private final SensorQRCode qrCode;
    private final Timer controlerrtTimer;
    private Point destination;
    /**
     * Map related varibles
     */
    private final SmartMapInterface map;
    private SmartMapInfo smartMapInfo = null;
    private SmartMapQRCode smartMapQRCode = null;
    private SmartMapBarrier smartMapBarrier = null;
    private SmartMapData scheduledPath = null;

    /**
     * constants
     */
    private static final int eventCheckFrequency = Integer.parseInt(SystemProperty.getProperty("Controller.RunFrequency"));
    private static final double positionDeviation = Double.parseDouble(SystemProperty.getProperty("Controller.PositionDeviation"));
    private static final double angleDeviation = Double.parseDouble(SystemProperty.getProperty("Controller.AngleDeviation"));
    private static final int initCaliNum = Integer.parseInt(SystemProperty.getProperty("GYRO.CalibrateDataNum"));
    private static final int runCaliNum = Integer.parseInt(SystemProperty.getProperty("GYRO.RunCalibrateNum"));

    /**
     * 超声波事件处理函数
     */
    private final SensorListener sensorUltrasonicListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            SensorUltrasonicData sensorUltrasonicData = (SensorUltrasonicData) e.getData();
            double dis1 = sensorUltrasonicData.getDistance1();
            logger.debug("Receive Ultrasonic Data: 1:" + dis1);
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

        //更新系统状态
        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);

        //建立navigator,并校正传感器数据
        navigator = new Navigator(map);
        navigator.calibrateSensors(initCaliNum);
        logger.info("Navigator calibrate sensors complete!");

        //连接事件处理程序
        sensorUltrasonic = new SensorUltrasonic();
        navigator.arduinoBridge.registerMessageListener(SensorEvent.SENSOR_ULTRASONIC_TYPE, sensorUltrasonic);
        qrCode = new SensorQRCode();
        sensorUltrasonic.addSenserListener(sensorUltrasonicListener);
        qrCode.addSenserListener(qrCodeListener);

        //启动controller线程
        controlerrtTimer = new Timer("Controller");
        controlerrtTimer.schedule(this, 0, 1000 / eventCheckFrequency);
        logger.info("starting Controller TimerTask ");
    }

    @Override
    public Point getCarCurrentLocation() {
        return navigator.getCurrentLocation();
    }

    @Override
    public double getCarAngle() {
        return navigator.getCurrentAngle();
    }

    @Override
    public void setCar(int speed, int angle) {
        Motor.smart_car_set(speed, angle);
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
            Point currentLocation = navigator.getCurrentLocation();
            scheduledPath = map.getPath(currentLocation, destination);
            logger.info("sechdule path:" + scheduledPath);

            smartMapInfo = map.getMapInfo();
            smartMapQRCode = map.getQRCodeInformation();
            smartMapBarrier = map.getBarrierInformation();
        }

        /**
         * 检查二维码
         */
        checkQRCode();
        CarOperation op = getMotorOperationAccordingSchedulePath();
    }

    /**
     *
     */
    private CarOperation getMotorOperationAccordingSchedulePath() {
        Point currentLocation = navigator.getCurrentLocation();
        ArrayList<Point> qrcodePoints = new ArrayList<>();

        logger.info("CurrentLocation=" + currentLocation);
        logger.info("Angle = " + navigator.getCurrentAngle());
        //检查是否到终点
        if (Math.abs(Utils.getDistance(currentLocation, destination)) < positionDeviation) {
            logger.info("Reach Endpoint!!!");
            setCarOperation(CarOperation.STOP);
            System.exit(0);
            return CarOperation.STOP;
        }

        //检查二维码
        //更新schedulePath,因为schedulePath是一个嵌套的数据结构
        if (Math.abs(Utils.getDistance(currentLocation, scheduledPath.getEndPoint())) < positionDeviation) {
            scheduledPath = scheduledPath.getChild();
            logger.info("schedulePath =" + scheduledPath);
        }
        if (scheduledPath == null) {
            logger.info("Reach Endpoint!!!");
            setCarOperation(CarOperation.STOP);
            System.exit(0);
        }

        //检查是否旋转车头
        if (driveStrategy == DriveStrategyType.SIMPLE) {
            double driveDirection = Utils.getAngle(currentLocation, scheduledPath.getEndPoint());
            double rotateAngle = Math.abs(driveDirection - navigator.getCurrentAngle());

            //SET BACK
            if (Math.abs(rotateAngle - 180) < 90) {
                driveDirection = driveDirection > 180 ? driveDirection - 180 : driveDirection + 180;
                if (Math.abs(driveDirection - navigator.getCurrentAngle()) > angleDeviation) {
                    rotateToAbsoluteAngle(driveDirection, 5);
                }
                setCarOperation(CarOperation.BACK);
            } else { //SET FORWARD
                if (rotateAngle > 180) {
                    rotateAngle = 360 - rotateAngle;
                }
                if (rotateAngle > angleDeviation) {
                    rotateToAbsoluteAngle(driveDirection, 5);

                }
                setCarOperation(CarOperation.FORWARD);
            }
        } else {
            /**
             * TODO
             */
        }
        return null;
    }

    /**
     * 检查小车当前位置是否存在二维码信息
     *
     * @return
     */
    private boolean checkQRCode() {
        if (!flag) {
            Point currentLocation = navigator.getCurrentLocation();

            for (SmartMapQRCodeInfo p : smartMapQRCode.getQrcodes()) {
                logger.info("qr=" + p.getLocation());
                if (Utils.getDistance(p.getLocation(), currentLocation) < 0.35) {
                    setCarOperation(CarOperation.STOP);
                    for (int i = 180; i > 0; i -= 15) {
                        Yuntai.setAngle(i);
                        Utils.delay(600);
                    }
                    flag = true;
                    return true;
                }
            }
        }
        return false;
    }

    private String searchQRcode() {
        for (int i = 0; i < 180; i += 20) {
            Yuntai.setAngle(i);
            String result = qrCode.decode();
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * 旋转小车到一定角度，deviation指明了旋转的正负误差, 当小车旋转角度的偏差在误差值内时，停止旋转，
     * 这里旋转的策略总是往旋转最小角度的方向旋转
     *
     * @param dstAngle 表示绝对旋转角度，而非相对角度
     * @param deviation
     */
    public void rotateToAbsoluteAngle(double dstAngle, double deviation) {
        double currentAngle;
        logger.info("Rotate");

        logger.info("EndPoint =" + scheduledPath.getEndPoint());
        logger.info("Drivedirection =" + dstAngle);
        logger.info("CurrentAngle = " + navigator.getCurrentAngle());
        //矫正传感器
//        setCarOperation(CarOperation.STOP);
//        navigator.calibrateSensors(runCaliNum);
        //计算最小的旋转方向，使旋转角度达到最小
        do {
            currentAngle = navigator.getCurrentAngle();
            if (currentAngle > dstAngle) {
                if (Math.abs(currentAngle - dstAngle) < 180) {
                    setCarOperation(CarOperation.CLOCKWISE);
                } else {
                    setCarOperation(CarOperation.COUNTERCLOCKWISE);
                }
            } else {
                if (Math.abs(currentAngle - dstAngle) < 180) {
                    setCarOperation(CarOperation.COUNTERCLOCKWISE);
                } else {
                    setCarOperation(CarOperation.CLOCKWISE);
                }
            }
            Utils.delay(5);
        } while (Math.abs(currentAngle - dstAngle) > deviation);
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

    @Override
    public void setCarOperation(CarOperation carOperation) {
        if (carOperation == CarOperation.FORWARD) {
            logger.debug("setCarOperation:FORWARD");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GOFORWARD);
            setCar(5, 0);
            return;
        }

        if (carOperation == CarOperation.BACK) {
            logger.debug("setCarOperation:BACK");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GOBACK);
            setCar(-5, 0);
            return;
        }
        if (carOperation == CarOperation.LEFT) {
            logger.debug("setCarOperation:LEFT");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GOLEFT);
            setCar(5, -90);
            return;
        }
        if (carOperation == CarOperation.RIGHT) {
            logger.debug("setCarOperation:RIGHT");
            SystemCoreData.setSystemState(SystemCoreData.STATE_GORIGHT);
            setCar(5, 90);
            return;
        }
        if (carOperation == CarOperation.CLOCKWISE) {
            logger.debug("setCarOperation:CLOCKWISE");
            SystemCoreData.setSystemState(SystemCoreData.STATE_CLOCKWISE);
            Motor.set_clockwise();
            return;
        }
        if (carOperation == CarOperation.COUNTERCLOCKWISE) {
            logger.debug("setCarOperation:COUNTERCLOCKWISE");
            SystemCoreData.setSystemState(SystemCoreData.STATE_COUNTERCLOCKWISE);
            Motor.set_counterclockwise();
            return;
        }
        if (carOperation == CarOperation.STOP) {
            logger.debug("setCarOperation:STOP");
            SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);
            setCar(0, 0);
        }
    }
}
