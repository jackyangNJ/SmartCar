package smartcar.motor;

import java.util.logging.Level;
import java.util.logging.Logger;
import smartcar.Sensor.ArduinoBridgeImpl;

/**
 *
 * @author jack
 */
public class Yuntai {

    static int angle;

    public static int getAngle() {
        return angle;
    }

    public static void setAngle(int angle) {
        if (angle < 0 || angle > 180) {
            return;
        }
        Yuntai.angle = angle;
        
        byte[] data = new byte[3];
        data[0] = 'R';
        data[1] = (byte) angle;
        data[2] = '\n';
        ArduinoBridgeImpl.sendMessagge(data);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArduinoBridgeImpl arduinoBridgeImpl=new ArduinoBridgeImpl("/dev/ttyUSB0", 115200);
        for (int i = 180; i > 90; i-=15) {
            Yuntai.setAngle(i);
            try {
                Thread.sleep(600);
            } catch (InterruptedException ex) {
                Logger.getLogger(Yuntai.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
