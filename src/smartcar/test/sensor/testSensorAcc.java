package smartcar.test.sensor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Sensor.SensorAcc;


public class testSensorAcc {	
    public static Log logger = LogFactory.getLog(testHall.class.getName());
	public static void main(String[] args) throws InterruptedException {
                PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
                SensorAcc  accTest = new SensorAcc();
                while(true){
                   
                }
	}
}
