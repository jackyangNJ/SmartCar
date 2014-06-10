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
    private static final int initCaliNum = Integer.parseInt(SystemProperty.getProperty("GYRO.RunCalibrateNum"));
    public static void main(String[] args) throws InterruptedException{
        int i = 0;
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
//        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);             //set still        
        SensorGyro gyroTest = new SensorGyro();                            
        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);
        while(i<5){                    
            gyroTest.calibrate(initCaliNum);        
            gyroTest.printMeandData();                                    
            sleep(6000);
            i++;
        }        
    }
}
