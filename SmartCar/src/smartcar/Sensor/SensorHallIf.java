package smartcar.Sensor;

import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public interface SensorHallIf {
    /**
     * 
     * @param l 添加SensorEvent的监听者
     */
    void addSenserListener(SensorListener l);
    /**
     * 
     * @param l 移除SensorEvent的监听者
     */
    void removeSenserListener(SensorListener l);
}
