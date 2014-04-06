package smartcar.Event;

import java.util.EventObject;
import javax.swing.JButton;

/**
 * 传感器数据事件类，用于传递传感器数据，其中type字段指明了data字段的类型
 * @author jack
 * 
 */
public class SensorEvent extends EventObject {
    /**
     * 常量
     */
    public static final int SENSOR_ACC_TYPE = 0;
    public static final int SENSOR_GYRO_TYPE = 1;
    public static final int SENSOR_ULTRASONIC_TYPE = 2;
    public static final int SENSOR_HALL_TYPE = 3;
    public static final int SENSOR_MAGNETIC_TYPE = 4;
    public static final int SENSOR_QRCODE_TYPE = 5;
    public static final int SENSOR_ARDUINO_TYPE = 6;
    
    // 内部变量
    /** 传递数据 */
    Object data;
    /** sdfsdf */
    int type;

    public SensorEvent(Object source) {
        super(source);
    }

    public SensorEvent(Object source, int type, Object data) {
        super(source);
        this.type = type;
        this.data=data;
    }

}
