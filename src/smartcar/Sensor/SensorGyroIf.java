package smartcar.Sensor;

import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public interface SensorGyroIf {
    /**
     * 
     * @param listener
     *  添加SensorEvent的监听者
     */
     void addSenserListener(SensorListener listener);
     
    /**
     * 
     * @param listener 移除SensorEvent的监听者
     */
     void removeSenserListener(SensorListener listener);
    
 
    /**
     * 获取SensorGyro模块维护的数据
     * @return 
     */
    SensorGyroData getSensorData();
    /**
     * 获取角速度传感器的原始数据
     * @return 
     */
  
    SensorGyroData getSensorGyroData();
    
}
