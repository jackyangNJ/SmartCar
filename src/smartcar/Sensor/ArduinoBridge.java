package smartcar.Sensor;
import smartcar.Event.SensorListener;

/**
 * 这个接口提供了arduino到java之间的数据转换
 *
 * @author jack
 */

public interface ArduinoBridge {
    public static final int HALL_MSG_TYPE = 0;
    public static final int MAGNETIC_MSG_TYPE = 1;
    public static final int ULTRAWAVE_MSG_TYPE = 2;
    
    /**
     * 移除SensorEvent的监听者
     *
     * @param type
     * @param listener
     */
    void unregisterMessageListener(int type,SensorListener listener);

    /**
     * 向ArduinoBridge注册侦听的接口类型
     * @param type 指明监听的类型（0..127,指向串口报文的第一个字节
     * @param listener
     * @return true，register success
     *          false,register error,for the same type has been registered
     */
    boolean registerMessageListener(int type,SensorListener listener);
}

