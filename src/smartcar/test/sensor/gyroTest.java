/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test.sensor;

import static java.lang.Thread.sleep;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Motor;
import smartcar.Sensor.SensorAcc;
import smartcar.Sensor.SensorGyro;
import smartcar.core.*;
import spiLib.SPIFunc;

/**
 *
 * @author cs
 */
public class gyroTest {
    public static Log logger = LogFactory.getLog(testHall.class.getName());
    public static void main(String[] args) throws InterruptedException{
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
//        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);             //set still        
        SensorGyro gyroTest = new SensorGyro();     
        SensorAcc  accTest = new SensorAcc();
        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);
        gyroTest.calibrate();
        accTest.calibrate();
//        Motor.set_counterclockwise();
        Motor.smart_car_set(50, 0);        
        
        while(true){            
            if(accTest.getSensorData().gety()<=-0.5){
                break;
            }                                    
        }
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Motor.smart_car_set(0, 0);
        
    }
}
