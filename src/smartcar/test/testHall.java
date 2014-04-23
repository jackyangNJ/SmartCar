/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;
import smartcar.Sensor.SensorHall;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class testHall {

    /**
     * @param args the command line arguments
     */
    public static Log logger = LogFactory.getLog(testHall.class.getName());
    SensorHall testHall = new SensorHall();
    String serialName = SystemProperty.getProperty("ArduinoBridge.serialComName");
    int serialRate =Integer.parseInt(SystemProperty.getProperty("ArduinoBridge.serialComRate"));
    ArduinoBridge arduinoBridge = new ArduinoBridgeImpl(serialName,serialRate);
    public testHall(){
        logger.info("1");
        arduinoBridge.registerMessageListener(ArduinoBridge.HALL_MSG_TYPE,testHall);
        logger.info("2");
        testHall.addSenserListener(new SensorListener() {
            @Override
            public void SensorEventProcess(SensorEvent e) {
                logger.info("a cycle!!");
            }
        });
    }
    public static void main(String[] args) {                    
         PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
         testHall test = new testHall();
    }
    
}
