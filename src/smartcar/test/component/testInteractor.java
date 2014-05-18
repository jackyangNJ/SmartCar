package smartcar.test.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Interactor.Interactor;
import smartcar.core.Point;
import smartcar.core.Utils;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author cs
 */
public class testInteractor {

    public static Log logger = LogFactory.getLog(testInteractor.class.getName());

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        Interactor testInter = new Interactor();
//        testInter.setCarAutoDriveDestination(new Point(1, 1));
        testInter.getSmartMapInfo();
        while (true) {
        }
    }
}
