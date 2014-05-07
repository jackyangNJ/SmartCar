package smartcar.test.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Navigator.Navigator;
import smartcar.map.SmartMap;
import smartcar.test.sensor.CameraTest;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author Administrator
 */
public class NavigatorTest {

    public static Log logger = LogFactory.getLog(CameraTest.class.getName());

    public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SmartMap map = new SmartMap();
        Navigator navigatorTest = new Navigator(map);
        while (true) {
            logger.info("a.x: " + navigatorTest.getNavigatorDate().geta_x());
            logger.info("a.y: " + navigatorTest.getNavigatorDate().geta_y());
            System.out.println("v.x: " + navigatorTest.getNavigatorDate().getv_x());
            System.out.println("v.y: " + navigatorTest.getNavigatorDate().getv_y());
            System.out.println("x: " + navigatorTest.getNavigatorDate().getx());
            System.out.println("y: " + navigatorTest.getNavigatorDate().gety());
            System.out.println("angular: " + navigatorTest.getNavigatorDate().getangular());
         //   System.out.println("distance: " + navigatorTest.getNavigatorDate().getdistance());
            Thread.sleep(1000);
        }
    }
}
