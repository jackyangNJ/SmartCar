package smartcar.Sensor;

import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public interface SensorHallIf {
    /**
     * 添加SensorEvent的监听者
     * @param listener
     */
     void addSenserListener(SensorListener listener);
    /**
     * 移除SensorEvent的监听者
     * @param listener 
     */
    void removeSenserListener(SensorListener listener);

}
