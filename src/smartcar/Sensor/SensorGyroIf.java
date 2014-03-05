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
     * 获取水平角速度，单位度
     * @return 水平角速度
     */
    float getHorizontalDps();
    
    /**
     * 获取水平角度，与正北方向的夹角(正北方向顺时针到当前的方向所成的夹角)，单位度，范围0~360
     * @return 
     */
    float getHorizontalAngular();
    
}
