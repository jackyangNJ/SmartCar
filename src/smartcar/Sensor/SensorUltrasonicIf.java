package smartcar.Sensor;

import java.util.List;


public interface SensorUltrasonicIf {
    /**
     * 
     * @return 返回距离列表，距离以米(m)为单位，基本类型为float,数据包括左中右三个传感器的数据
     */
    List getDistances();
}
