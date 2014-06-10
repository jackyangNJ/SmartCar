package smartcar.test.sensor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.ArduinoBridge;
import smartcar.Sensor.ArduinoBridgeImpl;
import smartcar.Sensor.SensorUltrasonic;
import smartcar.Sensor.SensorUltrasonicData;
import smartcar.core.SystemProperty;
import smartcar.test.env.testLogger;

/**
 *
 * @author cs
 */
public class ultroTest implements SensorListener {

    public static Log logger = LogFactory.getLog(testLogger.class);

    public static void main(String[] args) throws InterruptedException {

        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        ultroTest test = new ultroTest();
        SensorUltrasonic sensorUltrasonic = new SensorUltrasonic();
        String serialName = SystemProperty.getProperty("ArduinoBridge.serialComName");
        int serialRate = Integer.parseInt(SystemProperty.getProperty("ArduinoBridge.serialComRate"));
        ArduinoBridge arduinoBridge = new ArduinoBridgeImpl(serialName, serialRate);
        arduinoBridge.registerMessageListener(SensorEvent.SENSOR_ULTRASONIC_TYPE, sensorUltrasonic);
        sensorUltrasonic.addSenserListener(test);
        while (true) {

        }
    }

    @Override
    public void SensorEventProcess(SensorEvent e) {
        SensorUltrasonicData data = (SensorUltrasonicData) e.getData();
        logger.info("dis=" + data.getDistance1());
    }
}
