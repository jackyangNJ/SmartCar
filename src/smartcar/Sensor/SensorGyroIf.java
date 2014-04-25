package smartcar.Sensor;

import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public interface SensorGyroIf {

    /**
     *
     * @param listener 添加SensorEvent的监听者
     */
    void addSenserListener(SensorListener listener);

    /**
     *
     * @param listener 移除SensorEvent的监听者
     */
    void removeSenserListener(SensorListener listener);

    /**
     * 获取处理后的水平角速度，单位度
     *
     * @return 水平角速度
     */
    SensorGyroData getSensorGyroData();

    /**
     * 获取传感器原始数据
     *
     * @return
     */
    SensorGyroData getRawSensorGyroData();

    /**
     * 用于静止时传感器校准
     */
    void calibrate();
}
