package smartcar.Sensor;

import smartcar.Event.SensorListener;

public interface SensorMagneticIf {

    /**
     * 添加SensorEvent的监听者
     *
     * @param listener
     */
    void addSenserListener(SensorListener listener);

    /**
     * 移除SensorEvent的监听者
     *
     * @param listener
     */
    void removeSenserListener(SensorListener listener);
    
    /**
     * 获取磁场维护的数据，其中包括角度信息
     * @return
     */
    SensorMagneticData getData();

    /**
     * 获取从磁场传感器中获取的数据
     * @return
     */
    SensorMagneticData getRawData();
}
