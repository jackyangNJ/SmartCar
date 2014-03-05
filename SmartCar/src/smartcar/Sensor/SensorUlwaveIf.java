package smartcar.Sensor;

import java.util.List;


public interface SensorUlwaveIf {
    /**
     * 
     * @return 返回距离列表，距离以米(m)为单位，基本类型为float
     */
    List getDistances();
}
