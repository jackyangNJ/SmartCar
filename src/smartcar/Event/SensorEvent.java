package smartcar.Event;

import java.util.EventObject;

/**
 *
 * @author jack
 * 传感器数据事件类，用于传递传感器数据，其中type字段指明了data字段的类型
 */
public class SensorEvent extends EventObject {

    Object data;
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
