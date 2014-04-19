package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class testArduinoBridge {

    public static Log logger = LogFactory.getLog(test.class.getName());
    String serialName = SystemProperty.getProperty("ArduinoBridge.serialComName");
    int serialRate =Integer.parseInt(SystemProperty.getProperty("ArduinoBridge.serialComRate"));
    ArduinoBridge arduinoBridge = new ArduinoBridgeImpl(serialName,serialRate);

    public testArduinoBridge() {
        arduinoBridge.registerMessageListener(ArduinoBridge.HALL_MSG_TYPE, new SensorListener() {

            @Override
            public void SensorEventProcess(SensorEvent e) {
                logger.info("Receive msg from serial : type hall");
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        testArduinoBridge tArduinoBridge = new testArduinoBridge();
    }
}
