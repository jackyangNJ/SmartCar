/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test.sensor;

import org.apache.log4j.PropertyConfigurator;
import smartcar.Sensor.SensorUltrasonic;

/**
 *
 * @author cs
 */
public class ultroTest {   
    public static void main(String []args) throws InterruptedException{
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SensorUltrasonic  ultrtest = new SensorUltrasonic();
        while(true){     
            ultrtest.getDistance();
            System.out.println("the distance is: 1->"+ultrtest.getData().getDistance1()
                    +"\n2->"+ultrtest.getData().getDistance2()+"\n3->"+ultrtest.getData().getDistance3());
            Thread.sleep(2000);
        }
    }
}
