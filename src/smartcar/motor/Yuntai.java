package smartcar.motor;

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
        ArduinoBridgeImpl arduinoBridgeImpl=new ArduinoBridgeImpl("COM3", 115200);
        Yuntai.setAngle(180);
    }

}
