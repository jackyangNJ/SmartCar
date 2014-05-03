package smartcar.Navigator;

import java.util.ArrayList;
import smartcar.map.SmartMap;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
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
import smartcar.map.SmartMapInterface;

/**
 *
 * @author jack
 */
public class Navigator implements NavigatorIf {

    private static final double possibility_hall = (double) 1 / 2;
    private static final double possibility_acc = (double) 1 / 2;
    private static final double possibility_gory = (double) 1 / 2;
    double frequency;
    SmartMapInterface map;
    //传感器对象
    SensorHallIf sensorHall = new SensorHall();
    SensorAccIf sensorAcc = new SensorAcc();
    SensorGyroIf sensorGyro = new SensorGyro();
    SensorMagneticIf sensorMagnetic = new SensorMagnetic();

    //传感器数据
    SensorHallData sensorHallData;
    SensorAccData sensorAccRawData;
    SensorAccData sensorAccData;
    SensorGyroData sensorGyroData;
    SensorGyroData sensorGyroRawData;
    SensorMagneticData sensorMagneticData;
    NavigatorData nevigatorData;
    ArrayList list = new ArrayList(10);

    /**
     * 霍尔传感器事件处理函数
     */
    SensorListener sensorHallListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            //To change body of generated methods, choose Tools | Templates.
            /**
             * 目前这段代码假定的是小车在1次测距离时转动的角度不到180度
             */
            // s not ensure
            sensorHallData = (SensorHallData) e.getData();
            double s = sensorHallData.getDriveDistance();

            double sum = 0;
            // 遍历求和  
            for (int i = 0, size = list.size(); i < size; i++) {
                sum += (double) list.get(i);
            }
            double averageangular = sum / list.size();
            double x = nevigatorData.getx() + (double) (s * Math.cos((double) averageangular));
            nevigatorData.setx(x);
            double y = nevigatorData.gety() + (double) (s * Math.sin((double) averageangular));
            nevigatorData.sety(y);
            // t not ensure
            if (SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL) {
                double vx = possibility_hall * nevigatorData.getv_x() + (1 - possibility_hall) * (double) (s * Math.cos((double) averageangular)) * frequency;
                nevigatorData.setv_x(vx);
                double vy = possibility_hall * nevigatorData.getv_y() + (1 - possibility_hall) * (double) (s * Math.sin((double) averageangular)) * frequency;
                nevigatorData.setv_y(vy);
            } else {
                nevigatorData.setv_x(0);
                nevigatorData.setv_y(0);
            }

            nevigatorData.setdistance(sensorHallData.getDriveDistance());

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
            sensorAccRawData = (SensorAccData) e.getData();

            if (SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL) {
                double vx = possibility_acc * nevigatorData.getv_x() + (1 - possibility_acc) * sensorAccRawData.getv_x();
                nevigatorData.setv_x(vx);
                sensorAccData.setv_x(vx);
                double vy = possibility_acc * nevigatorData.getv_y() + (1 - possibility_acc) * sensorAccRawData.getv_y();
                nevigatorData.setv_y(vy);
                sensorAccData.setv_x(vx);
                double ax = sensorAccRawData.geta_x();
                nevigatorData.seta_x(ax);
                sensorAccData.setv_x(vx);
                double ay = sensorAccRawData.getv_y();
                nevigatorData.seta_y(ay);
                sensorAccData.setv_x(vx);
                sensorAccData.setx(nevigatorData.getx());
                sensorAccData.sety(nevigatorData.gety());

            } else {
                nevigatorData.setv_x(0);
                nevigatorData.setv_y(0);
                nevigatorData.seta_x(0);
                nevigatorData.seta_y(0);
                sensorAccData.setv_x(0);
                sensorAccData.setv_y(0);
                sensorAccData.seta_x(0);
                sensorAccData.seta_y(0);
                sensorAccData.setx(nevigatorData.getx());
                sensorAccData.sety(nevigatorData.gety());

            }
            e.setData(sensorAccData);
        }
    };

    /**
     * 陀螺仪传感器事件处理函数
     */
    SensorListener sensorGyroListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            sensorGyroRawData = (SensorGyroData) e.getData();
            nevigatorData.setangular((float) sensorGyroRawData.getHori_angle());
            if (list.size() >= 10) {
                list.remove(0);
            }

            float curangulat = (float) (possibility_gory * nevigatorData.getangular() + (1 - possibility_gory) * sensorGyroRawData.getHori_angle());
            nevigatorData.setangular(curangulat);
            list.add(curangulat);

            nevigatorData.setangular_velocity(sensorGyroRawData.getHori_angleSpeed());
            sensorGyroData.setHori_angle(curangulat);
            sensorGyroData.setHori_angleSpeed(sensorGyroRawData.getHori_angleSpeed());
        }
    };
    /**
     * 磁场传感器事件处理函数
     */
    SensorListener sensorMagneticListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            //To change body of generated methods, choose Tools | Templates.
            //  nevigatorData.setangular(sensorMagneticData.getangular());
        }
    };

    /**
     *
     * @param map
     */
    public Navigator(SmartMap map) {
        this.map = map;
        nevigatorData = new NavigatorData();
        //注册监听器
        sensorHall.addSenserListener(sensorHallListener);
        sensorGyro.addSenserListener(sensorGyroListener);
        sensorAcc.addSenserListener(sensorAccListener);
        sensorMagnetic.addSenserListener(sensorMagneticListener);
    }


    public NavigatorData getNavigatorData() {
        return nevigatorData;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void calibrateSensors() {
        sensorAcc.calibrate();
        sensorGyro.calibrate();
        //TODO
    }

}
