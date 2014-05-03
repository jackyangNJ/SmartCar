package smartcar.Navigator;

import smartcar.Sensor.SensorAccData;
import smartcar.Sensor.SensorGyroData;
import smartcar.Sensor.SensorHall;
import smartcar.Sensor.SensorHallData;
import smartcar.Sensor.SensorMagnetic;
import smartcar.Sensor.SensorMagneticData;

/**
 * Interface for class Navigator
 * @author jackl
 */
public interface NavigatorIf {
   public NavigatorData getNavigatorDate();
   /**
    * 获取加速度传感器的原始数据
    * @return 
    */
   public SensorAccData getSensorAccRawData();
   /**
    * 获取加速度传感器处理后的数据
    * @return 
    */
 //  public SensorAccData getSensorAccDate();
   
   public SensorGyroData getSensorGyroRawData();
  // public SensorGyroData getSensorGyroData();
   
   public SensorMagneticData getSensorMagneticRawData();
  // public SensorMagneticData getSensorMagneticData();
   
   /**
    * 获取霍尔传感器的数据，指明小车轮子是否转了一圈
    * @return 
    */
   public SensorHallData getSensorHallData();
   
   /**
    * 静止矫正传感器数据
    */
   public void calibrateSensors();
}
