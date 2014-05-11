package smartcar.Navigator;

import com.sun.corba.se.impl.activation.ServerMain;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.map.SmartMap;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;
import smartcar.Sensor.SensorAcc;
import smartcar.Sensor.SensorAccData;
import smartcar.Sensor.SensorAccIf;
import smartcar.Sensor.SensorGyro;
import smartcar.Sensor.SensorGyroData;
import smartcar.Sensor.SensorGyroIf;
import smartcar.Sensor.SensorHall;
import smartcar.Sensor.SensorHallData;
import smartcar.Sensor.SensorHallIf;
import smartcar.Sensor.SensorMagnetic;
import smartcar.Sensor.SensorMagneticData;
import smartcar.Sensor.SensorMagneticIf;
import smartcar.core.SystemCoreData;
import smartcar.core.SystemProperty;
import smartcar.map.SmartMapInterface;
import smartcar.test.sensor.CameraTest;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class Navigator implements NavigatorIf {    
    public static Log logger = LogFactory.getLog(CameraTest.class.getName());
    
    private static final double possibility_hall = (double) 1 / 2;
    private static final double possibility_acc = (double) 1 / 2;
    private static final double possibility_gory = (double) 1 / 2;
    double wheelgirth = Double.parseDouble(SystemProperty.getProperty("Car.WheelGirth"));
    double timeInterval;    
    double beginTime,endTime;    
    SmartMapInterface map;
    //传感器对象
    String serialName = SystemProperty.getProperty("ArduinoBridge.serialComName");
    int serialRate =Integer.parseInt(SystemProperty.getProperty("ArduinoBridge.serialComRate"));
    ArduinoBridge arduinoBridge = new ArduinoBridgeImpl(serialName,serialRate);
    
    SensorHall sensorHall = new SensorHall();
    SensorAccIf sensorAcc = new SensorAcc();
    SensorGyroIf sensorGyro = new SensorGyro();
    SensorMagneticIf sensorMagnetic = new SensorMagnetic();
    
    //传感器数据
    SensorHallData sensorHallData;
    SensorAccData sensorAccData;
    SensorGyroData sensorGyroData;
    SensorMagneticData sensorMagneticData;
    NavigatorData navigatorData;
    ArrayList list_angular = new ArrayList(10);
    ArrayList list_vx = new ArrayList(10);
    ArrayList list_vy = new ArrayList(10);
    ArrayList list_ax = new ArrayList(10);
    ArrayList list_ay = new ArrayList(10);        
    ArrayList list_angularspeed =  new ArrayList(10);
    //ArrayList list_gyroav = new ArrayList(10);
    /**
     * 霍尔传感器事件处理函数
     */
    SensorListener sensorHallListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            //To change body of generated methods, choose Tools | Templates.s
            // s not ensure            
            beginTime = endTime;
            endTime = System.currentTimeMillis();
            timeInterval = (endTime-beginTime)/1000;
            
            sensorHallData = (SensorHallData) e.getData();
//            double s = sensorHallData.getDriveDistance();            
            logger.info("angular is "+navigatorData.getangular());
            logger.info("cos is " + Math.cos(navigatorData.getangular()));
            logger.info("x + ********************"+(double) (wheelgirth * Math.cos(Math.toRadians(navigatorData.getangular()))));
            double x = navigatorData.getx() + (double) (wheelgirth * Math.cos(Math.toRadians(navigatorData.getangular())));
            logger.info("x is "+x);
            //for test
//            logger.info("navigatorData.getx():" + navigatorData.getx());
//            logger.info("sensorGyroData.getHori_angle():" + sensorGyroData.getHori_angle());                      
            navigatorData.setx(x);
                        
            double y = navigatorData.gety() + (double) (wheelgirth * Math.sin(Math.toRadians(navigatorData.getangular())));            
            logger.info("y is "+y);
            navigatorData.sety(y);
//            System.out.println("averagey:" + averagey);
            
            double vx = 0;
            double vy = 0;
            if (SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL) {
                vx = possibility_hall * navigatorData.getv_x() + (1 - possibility_hall) * (double) (wheelgirth * Math.cos(Math.toRadians(navigatorData.getangular()))) /timeInterval;
              //  navigatorData.setv_x(vx);
                vy = possibility_hall * navigatorData.getv_y() + (1 - possibility_hall) * (double) (wheelgirth * Math.sin(Math.toRadians(navigatorData.getangular()))) /timeInterval;
              //  navigatorData.setv_y(vy);
            } else {
                vx = 0;
                vy = 0;
            }
//            System.out.println("vy:" + vy);
//            System.out.println("vx:" + vx);
            navigatorData.setv_y(vy);
            navigatorData.setv_x(vx);
            //navigatorData.setdistance(sensorHallData.getDriveDistance());

        }
    };
    /**
     * 加速度传感器处理函数
     */
    SensorListener sensorAccListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            //!TODO
            //a little diffirence from instruction
            sensorAccData = (SensorAccData) e.getData();
            double sum = 0.0;
            double ax = 0;
            double ay = 0;
            double vx = 0;
            double vy = 0;
            ax = sensorAccData.geta_x();
            ay = sensorAccData.getv_y();
            if (SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL) {
                vy = possibility_acc * navigatorData.getv_y() + (1 - possibility_acc) * sensorAccData.getv_y();
                vx = possibility_acc * navigatorData.getv_x() + (1 - possibility_acc) * sensorAccData.getv_x();
               
            } else {
               vx = 0;
               vy = 0;

            }
            
            if ( list_vx.size() >= 10) {
                list_vx.remove(0);
            }
            list_vx.add(vx);
            sum = 0.0;
            for (int i = 0, size =  list_vx.size(); i < size; i++) {
                sum += (double)  list_vx.get(i);
            }
            double averagevx = sum /  list_vx.size();
            navigatorData.setv_x(averagevx);
                //navigatorData.setv_x(vx);
                //sensorAccData.setv_x(vx);
            
            if ( list_vy.size() >= 10) {
                list_vy.remove(0);
            }
            list_vy.add(vy);
            sum = 0.0;
            for (int i = 0, size =  list_vy.size(); i < size; i++) {
                sum += (double)  list_vy.get(i);
            }
            double averagevy = sum /  list_vy.size();
            navigatorData.setv_y(averagevy);
            
             
            if ( list_ax.size() >= 10) {
                list_ax.remove(0);
            }
            list_ax.add(vx);
            sum = 0.0;
            for (int i = 0, size =  list_ax.size(); i < size; i++) {
                sum += (double)  list_ax.get(i);
            }
            double averageax = sum /  list_ax.size();
            navigatorData.seta_x(averageax);
                //navigatorData.setv_x(vx);
                //sensorAccData.setv_x(vx);
            
            if ( list_ay.size() >= 10) {
                list_ay.remove(0);
            }
            list_ay.add(ay);
            sum = 0.0;
            for (int i = 0, size =  list_ay.size(); i < size; i++) {
                sum += (double)  list_ay.get(i);
            }
            double averageay = sum /  list_ay.size();
            navigatorData.seta_y(averageay);
          
        }
    };

    /**
     * 陀螺仪传感器事件处理函数
     */
    SensorListener sensorGyroListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            sensorGyroData = (SensorGyroData) e.getData();
            navigatorData.setangular( sensorGyroData.getHori_angle());
            if (list_angular.size() >= 10) {
                list_angular.remove(0);
            }
            
            double curangulat = (double) (possibility_gory * navigatorData.getangular() + (1 - possibility_gory) * sensorGyroData.getHori_angle());
            list_angular.add(curangulat);
            double sum = 0.0;
            // 遍历求和  
            for (int i = 0, size = list_angular.size(); i < size; i++) {
                sum += (double) list_angular.get(i);
            }
            double averageangular = sum / list_angular.size();
           
            sum = 0.0;
            if ( list_angularspeed.size() >= 10) {
                 list_angularspeed.remove(0);
            }
            list_angularspeed.add(sensorGyroData.getHori_angleSpeed());
             for (int i = 0, size =  list_angularspeed.size(); i < size; i++) {
                sum += (double)  list_angularspeed.get(i);
            }
            double averageangularspeed = sum /  list_angularspeed.size();
           // sensorGyroData.setHori_angle(averageangular);
           // sensorGyroData.setHori_angleSpeed(averageangularspeed);
            navigatorData.setangular(averageangular);
            navigatorData.setangular_velocity(averageangularspeed);
        }
    };
    /**
     * 磁场传感器事件处理函数
     */
    SensorListener sensorMagneticListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            //To change body of generated methods, choose Tools | Templates.
            //  navigatorData.setangular(sensorMagneticData.getangular());
        }
    };

    /**
     *
     * @param map
     */
    public Navigator(SmartMap map) {
        this.map = map;
        endTime = System.currentTimeMillis();
        navigatorData = new NavigatorData();
        //注册监听器        
        arduinoBridge.registerMessageListener(ArduinoBridge.HALL_MSG_TYPE,sensorHall);
        sensorHall.addSenserListener(sensorHallListener);

//        sensorGyro.addSenserListener(sensorGyroListener);
//        sensorAcc.addSenserListener(sensorAccListener);
//        sensorMagnetic.addSenserListener(sensorMagneticListener);
    }


    public NavigatorData getNavigatorData() {
        return navigatorData;
    }

    public SensorAccData getSensorAccRawData() {
        return sensorAcc.getSensorRawData();
    }

    @Override
    public SensorGyroData getSensorGyroRawData() {
        return sensorGyro.getRawSensorGyroData();
    }

    @Override
    public SensorMagneticData getSensorMagneticRawData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorHallData getSensorHallData() {
        return sensorHallData;
    }

    @Override
    public NavigatorData getNavigatorDate() {
        return navigatorData;
    }

    @Override
    public void calibrateSensors() {
        sensorAcc.calibrate();
        sensorGyro.calibrate();
        //TODO
    }

}
