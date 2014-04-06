package smartcar.Sensor;
import smartcar.Event.SensorListener;

/**
 * 这个接口提供了arduino到java之间的数据转换
 *
 * @author jack
 */

public interface ArduinoBridge {
    /**
     * 移除SensorEvent的监听者
     *
     * @param listener
     */
    void unregisterMessageListener(int type,SensorListener listener);

    /**
     * 向ArduinoBridge注册侦听的接口类型
     * @param type 指明监听的类型（0..127,指向串口报文的第一个字节
     * @return true，register success
     *          false,register error,for the same type has been registered
     */
    boolean registerMessageListener(int type,SensorListener listener);
}

