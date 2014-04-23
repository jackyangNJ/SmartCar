/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Interactor.Interactor;

/**
 *
 * @author cs
 */
public class testInterator {
    public static Log logger = LogFactory.getLog(testInterator.class.getName());
    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        logger.info("111");
        Interactor testInter = new Interactor();
        testInter.setCar(5000, 80);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        testInter.setCarClockwise();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        testInter.setCar(0, 0);
    }
}
