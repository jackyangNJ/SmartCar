/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test;

import smartcar.Sensor.SensorGyro;

/**
 *
 * @author cs
 */
public class gyroTest {
    public static void main(String[] args) throws InterruptedException{
        SensorGyro gyroTest = new SensorGyro();
        while(true){
            System.out.println("raw data is: "+gyroTest.getRawSensorGyroData().getHori_angleSpeed());
            System.out.println("processed data is: "+gyroTest.getSensorGyroData().getHori_angleSpeed());
            Thread.sleep(2000);
        }
    }
}
