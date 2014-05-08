/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test.env;

import java.util.ArrayList;
import org.apache.log4j.PropertyConfigurator;
import smartcar.map.SmartMap;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class test {

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SmartMap map=new SmartMap();
        
    }
}
