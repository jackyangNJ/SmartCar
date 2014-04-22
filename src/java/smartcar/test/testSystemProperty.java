/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class testSystemProperty {

    public static Log logger = LogFactory.getLog(testLogger.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        logger.info(SystemProperty.getProperty("WheelGirth"));
        logger.info(SystemProperty.getProperty("Controller.RunFrequency"));
    }

}
