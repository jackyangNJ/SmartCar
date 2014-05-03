package smartcar.Sensor;

import smartcar.Event.SensorListener;

public interface SensorUltrasonicIf {

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
     * 返回距离，距离以米(m)为单位，基本类型为double,数据包括左中右三个传感器的数据
     * @return 
     */
    SensorUltrasonicData getData();
}
