package smartcar.Sensor;

import smartcar.Event.SensorListener;
import smartcar.core.Point;

/**
 * 刚哥fuck here
 * @author jack
 */
public interface SensorAccIf {
    
    /**
     * 添加SensorEvent的监听者，加速度传感器以自己的数据获取时间周期触发SensorEvent事件
     * @param listener
     */
    
     void addSenserListener(SensorListener listener);
    /**
     * 移除SensorEvent的监听者
     * @param listener 
     */
    void removeSenserListener(SensorListener listener);
    
    /**
     * 获取SensorAcc模块维护的数据
     * @return 
     */
    SensorAccData getSensorData();
    /**
     * 获取加速度传感器的原始数据
     * @return 
     */
    SensorAccData getSensorRawData(); 
    
    /**
     * 用于静止时传感器校准
     */
    void calibrate();
    
    public void setBiasPosition(Point biasPosition);
}
