package smartcar.Sensor;


public interface SensorUltrasonicIf {
    /**
     * 
     * @return 返回距离，距离以米(m)为单位，基本类型为float,数据包括左中右三个传感器的数据
     */
    SensorUltrasonicData getDistances();
}
