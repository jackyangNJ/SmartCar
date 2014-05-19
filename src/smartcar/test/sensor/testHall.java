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
    public AtomicInteger count =new AtomicInteger(0);

    public testHall() {
        logger.info("1");
        arduinoBridge.registerMessageListener(ArduinoBridge.HALL_MSG_TYPE, testHall);
        logger.info("2");
        testHall.addSenserListener(new SensorListener() {
            @Override
            public void SensorEventProcess(SensorEvent e) {
                logger.info("a cycle!!");
                count.addAndGet(1);
                logger.info("count="+count);
            }
        });
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));

        testHall test = new testHall();
        Utils.delay(2000);
//        Motor.smart_car_set(10, 0);
        while (test.count.get() != 100) {

        };
        Motor.smart_car_set(-80, 0);
        Utils.delay(30);
        Motor.smart_car_set(0, 0);
        
        logger.info("count = " + test.count);
        System.exit(0);
    }

}
