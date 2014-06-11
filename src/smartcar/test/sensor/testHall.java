/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test.sensor;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;
import smartcar.Sensor.SensorHall;
import smartcar.core.SystemProperty;
import smartcar.core.Utils;
import smartcar.motor.Motor;

/**
 *
 * @author jack
 */
public class testHall {

    /**
     */
    static Log logger = LogFactory.getLog(testHall.class.getName());
    SensorHall testHall = new SensorHall();
    String serialName = SystemProperty.getProperty("ArduinoBridge.serialComName");
    int serialRate = Integer.parseInt(SystemProperty.getProperty("ArduinoBridge.serialComRate"));
    ArduinoBridge arduinoBridge = new ArduinoBridgeImpl(serialName, serialRate);
    public AtomicInteger count = new AtomicInteger(0);

    public testHall() {
        arduinoBridge.registerMessageListener(SensorEvent.SENSOR_HALL_TYPE, testHall);
        testHall.addSenserListener(new SensorListener() {
            @Override
            public void SensorEventProcess(SensorEvent e) {
                logger.info("a cycle!!");
                count.addAndGet(1);
                logger.info("count=" + count);
                if (count.get() == 200) {
                    Motor.smart_car_set(0, 0);
                    System.exit(0);
                }

            }
        });
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));

        testHall test = new testHall();
        while (true) {
        }
    }
}
