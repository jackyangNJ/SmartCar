/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test.sensor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.motor.Motor;
import smartcar.Sensor.SensorAcc;
import smartcar.Sensor.SensorGyro;
import smartcar.core.*;

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
        gyroTest.calibrate(100);
        accTest.calibrate(100);
//        Motor.set_coufnterclockwise();
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
