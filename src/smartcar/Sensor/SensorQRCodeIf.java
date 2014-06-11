package smartcar.Sensor;

import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public interface SensorQRCodeIf {

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
     * 获取准确位置
     * @return SensorQRCodeData
     */
    SensorQRCodeData getQRCodeData();
    
    
}
