package smartcar.test.sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;
import smartcar.core.SystemProperty;
import smartcar.core.Utils;

/**
 *
 * @author jack
 */
public class testArduinoBridge {

    public static Log logger = LogFactory.getLog(testArduinoBridge.class);
    String serialName = SystemProperty.getProperty("ArduinoBridge.serialComName");
    int serialRate = Integer.parseInt(SystemProperty.getProperty("ArduinoBridge.serialComRate"));
    ArduinoBridge arduinoBridge = new ArduinoBridgeImpl(serialName, serialRate);

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
    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        testArduinoBridge tArduinoBridge = new testArduinoBridge();

        for (int i = 0; i < 200; i++) {
            byte[] data = new byte[2];
            data[0] = 'R';

            data[1] = (byte)i;
            System.err.println(Byte.toString((byte)i));
            ArduinoBridgeImpl.sendMessagge(data);
            Utils.delay(50);
        }

    }
}
