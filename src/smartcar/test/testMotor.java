/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Motor;
/**
 *
 * @author cs
 */
public class testMotor {
    public static void main(String[] args){
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        Motor.smart_car_set(5000, 80);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(testMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Motor.set_clockwise();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(testMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Motor.smart_car_set(0, 0);
    }
}
