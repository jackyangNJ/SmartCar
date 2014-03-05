package smartcar.Sensor;

import java.awt.Point;
import smartcar.Event.SensorListener;

/**
 *
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
     * 获取x轴加速度，单位m/s2
     * @return 
     */
    float getXAcc();
    
    /**
     * 获取y轴加速度，单位m/s2
     * @return 
     */
    float getYAcc();
    
    /**
     * 获取x轴速度，单位m/s
     * @return 
     */
    float getXVelocity();
    
    /**
     * 获取y轴加速度，单位m/s
     * @return 
     */
    float getYVelocity();
    
    /**
     * 获取小车当前的坐标,包括x轴和y轴，封装在Point类中
     * @return 
     */
    Point getPosition();
    
    
}
