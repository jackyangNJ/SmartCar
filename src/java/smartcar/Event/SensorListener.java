package smartcar.Event;

import java.util.EventListener;

/**
 *
 * @author jack
 该Listener用户处理Sensor数据事件
 */
public interface SensorListener extends EventListener{
    void SensorEventProcess(SensorEvent e);
}
