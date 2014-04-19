package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;

/**
 *
 * @author jack
 */
public class testArduinoBridge {

    public static Log logger = LogFactory.getLog(test.class.getName());
    ArduinoBridge arduinoBridge = new ArduinoBridgeImpl();

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
        testArduinoBridge tArduinoBridge = new testArduinoBridge();
    }
}
