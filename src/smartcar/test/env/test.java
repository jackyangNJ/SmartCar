/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test.env;

import java.util.ArrayList;
import org.apache.log4j.PropertyConfigurator;
import smartcar.core.Point;
import smartcar.core.Utils;
import smartcar.map.SmartMap;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class test {

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        Point dst=new Point(0.875, 0.625);
        Point srcPoint= new Point(0.83, 0.55);
        System.err.println(Utils.getAngle(srcPoint, dst));
                
        
    }
}
