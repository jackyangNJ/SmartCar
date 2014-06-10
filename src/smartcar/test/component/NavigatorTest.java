package smartcar.test.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Navigator.Navigator;
import smartcar.core.SystemCoreData;
import smartcar.core.Utils;
import smartcar.map.SmartMap;
import smartcar.test.sensor.CameraTest;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author Administrator
 */
public class NavigatorTest {

    public static Log logger = LogFactory.getLog(CameraTest.class.getName());

    public static void main(String[] args){
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SmartMap map = new SmartMap();
        SystemCoreData.setSystemState(SystemCoreData.STATE_STILL);
        Navigator navigatorTest = new Navigator(map);
        navigatorTest.calibrateSensors(100);
        SystemCoreData.setSystemState(SystemCoreData.STATE_GOFORWARD);

        while (true) {
//            logger.info("a.x: " + navigatorTest.getNavigatorDate().geta_x());
//            logger.info("a.y: " + navigatorTest.getNavigatorDate().geta_y());
//            logger.info("v.x: " + navigatorTest.getNavigatorDate().getv_x());
//            logger.info("v.y: " + navigatorTest.getNavigatorDate().getv_y());
//            logger.info("x: " + navigatorTest.getNavigatorDate().getx());
//            logger.info("y: " + navigatorTest.getNavigatorDate().gety());
            logger.info("angular: " + navigatorTest.getCurrentAngle());
            Utils.delay(500);
        }
    }
}
