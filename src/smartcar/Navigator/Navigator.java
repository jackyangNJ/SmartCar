package smartcar.Navigator;

import java.util.ArrayList;
import smartcar.map.SmartMap;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.QRCode;
import smartcar.Sensor.QRCodeData;
import smartcar.Sensor.QRCodeIf;
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
import smartcar.Sensor.SensorUltrasonic;
import smartcar.Sensor.SensorUltrasonicIf;
import smartcar.core.SystemCoreData;

/**
 *
 * @author jack
 */
public class Navigator implements NavigatorIf{
    
    private static final float possibility_hall = 1/2;
    private static final float possibility_acc = 1/2;
    private static final float possibility_gory = 1/2;
    float frequency;
    SmartMap map;
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
    NavigatorData nevigatorData = new NavigatorData();
    ArrayList list = new ArrayList(10);
    

    
    
    /**
     * 霍尔传感器事件处理函数
     */
    //
    /*
    float dealangular(float angular){
          if(angular > 2 * Math.PI * 3 / 4){
                angular = (float) (2 * Math.PI - angular);
            }
            else if(angular > Math.PI ){
                 angular = (float) (2 * Math.PI * 3 / 4 - angular);
            }
            else if(angular > 1 / 2 * Math.PI){
                angular = (float)( Math.PI) - angular;
            }
          return angular;
    }*/
    
    
    SensorListener sensorHallListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            //To change body of generated methods, choose Tools | Templates.
            /**
             * 目前这段代码假定的是小车在1次测距离时转动的角度不到180度
             */
            
            /*float distance = sensorHallData.getDriveDistance() - nevigatorData.getdistance();
            float nowangular = sensorGyroData.getangular();
            float historyangular = nevigatorData.getangular();
            float angular = 0;//yong
            float angulardifferience = nowangular - historyangular;
            float linedistance = (float) (2 * Math.sin(Math.abs(angulardifferience) / 2)* distance /(Math.abs(angulardifferience)));//计算切线距离
            */
            
            // s not ensure
            sensorHallData = (SensorHallData)e.getData();
            float s = sensorHallData.getDriveDistance();
        
            float sum = 0;  
             // 遍历求和  
            for (int i = 0, size = list.size(); i < size; i++) {  
                 sum += (float)list.get(i);  
            }  
            float averageangular = sum / list.size();  
            float x = nevigatorData.getx() + (float)(s * Math.cos((double) averageangular));
            nevigatorData.setx(x);
            float y = nevigatorData.gety() + (float)(s * Math.sin((double) averageangular));
            nevigatorData.sety(y);
            // t not ensure
            if(SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL){
                float vx = possibility_hall * nevigatorData.getv_x() + (1 - possibility_hall) * (float)(s * Math.cos((double) averageangular))* frequency;
                nevigatorData.setv_x(vx);
                float vy = possibility_hall * nevigatorData.getv_y() + (1 - possibility_hall) * (float)(s * Math.sin((double) averageangular))* frequency;
                nevigatorData.setv_y(vy);
            }
            else{
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
            sensorAccRawData = (SensorAccData)e.getData();
           
            if(SystemCoreData.getSystemState() != SystemCoreData.STATE_STILL){
                float vx = possibility_acc * nevigatorData.getv_x() + (1 - possibility_acc) * sensorAccRawData.getv_x();
                nevigatorData.setv_x(vx);
                sensorAccData.setv_x(vx);
                float vy = possibility_acc * nevigatorData.getv_y() + (1 - possibility_acc) * sensorAccRawData.getv_y();
                nevigatorData.setv_y(vy);
                sensorAccData.setv_x(vx);
                float ax = sensorAccRawData.geta_x();
                nevigatorData.seta_x(ax);
                sensorAccData.setv_x(vx);
                float ay = sensorAccRawData.getv_y();
                nevigatorData.seta_y(ay);
                sensorAccData.setv_x(vx);
                sensorAccData.setx(nevigatorData.getx());
                sensorAccData.sety(nevigatorData.gety());
                
                
            }
            else{
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
        }
    };
    /**
     * 陀螺仪传感器事件处理函数
     */
    SensorListener sensorGyroListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
             //To change body of generated methods, choose Tools | Templates.
            sensorGyroRawData = (SensorGyroData)e.getData();
            nevigatorData.setangular((float) sensorGyroRawData.getHori_angle());
            if(list.size() >= 10){
                list.remove(0);
            }
           
            float curangulat = (float) (possibility_gory * nevigatorData.getangular() +  (1 - possibility_gory) * sensorGyroRawData.getHori_angle());
            nevigatorData.setangular(curangulat);
            list.add(curangulat);
            
            nevigatorData.setangular_velocity(sensorGyroRawData. getHori_angleSpeed());
            sensorGyroData.setHori_angle(curangulat);
            sensorGyroData.setHori_angleSpeed(sensorGyroRawData. getHori_angleSpeed());
            
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
        
        //注册监听器
        sensorHall.addSenserListener(sensorHallListener);
        sensorGyro.addSenserListener(sensorGyroListener);
        sensorAcc.addSenserListener(sensorAccListener);
        sensorMagnetic.addSenserListener(sensorMagneticListener);
   //     qrCode.addSenserListener(QRCodeListener);
    }
    
    @Override
    public NavigatorData getNavigatorDate() {
       return nevigatorData;
    }

    @Override
    public SensorAccData getSensorAccRawDate() {
       return sensorAccRawData;
    }

    @Override
    public SensorAccData getSensorAccDate() {
       return  sensorAccData;
       
    }

    @Override
    public SensorGyroData getSensorGyroRawData() {
       return sensorGyroRawData;
    }

    @Override
    public SensorGyroData getSensorGyroData() {
       return sensorGyroData;
    }

    @Override
    public SensorMagneticData getSensorMagneticRawData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorMagneticData getSensorMagneticData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorHallData getSensorHallData() {
          return sensorHallData;
    }
}
