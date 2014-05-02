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
        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);
        gyroTest.calibrate();
//        gyroTest.getRawSensorGyroData().getHori_angle();
//        gyroTest.setState(0);
        while(true){
           //System.out.println("processed data is: "+gyroTest.getSensorGyroData().getHori_angleSpeed());
            //Thread.sleep(1000);
        }
    }
}
