package smartcar.Sensor;

import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public interface SensorHallIf {
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
}
