package smartcar.test.sensor;
import org.apache.log4j.PropertyConfigurator;
import smartcar.core.Utils;
import smartcar.motor.Motor;
/**
 *
 * @author cs
 */
public class testMotor {
    public static void main(String[] args){
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        Motor.smart_car_set(5000, 80);
        Utils.delay(5000);
        Motor.set_clockwise();
        Utils.delay(5000);
        Motor.smart_car_set(0, 0);
    }
}
