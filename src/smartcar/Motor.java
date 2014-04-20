package smartcar;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.core.SystemProperty;

/**
 *
 * @author cshuo
 */
public class Motor {

    public static Log logger = LogFactory.getLog(Motor.class.getName());
    //Constants
    private static final String MOTOR_FILE_DIRECTION1 = SystemProperty.getProperty("Motor.File.Direction1");
    private static final String MOTOR_FILE_DIRECTION2 = SystemProperty.getProperty("Motor.File.Direction2");
    private static final String MOTOR_FILE_DIRECTION3 = SystemProperty.getProperty("Motor.File.Direction3");
    private static final String MOTOR_FILE_DIRECTION4 = SystemProperty.getProperty("Motor.File.Direction4");
    private static final String MOTOR_FILE_SPEED1 = SystemProperty.getProperty("Motor.File.Speed1");
    private static final String MOTOR_FILE_SPEED2 = SystemProperty.getProperty("Motor.File.Speed2");
    private static final String MOTOR_FILE_SPEED3 = SystemProperty.getProperty("Motor.File.Speed3");
    private static final String MOTOR_FILE_SPEED4 = SystemProperty.getProperty("Motor.File.Speed4");

    public static void fileWrite(String filePath, String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }

    }

    public static void set_clockwise() {
        String cspeed = "7000" + '\0';

        fileWrite(MOTOR_FILE_DIRECTION1, Integer.toString(0));
        fileWrite(MOTOR_FILE_SPEED1, cspeed);

        fileWrite(MOTOR_FILE_DIRECTION2, Integer.toString(1));
        fileWrite(MOTOR_FILE_SPEED2, cspeed);

        fileWrite(MOTOR_FILE_DIRECTION3, Integer.toString(1));
        fileWrite(MOTOR_FILE_SPEED3, cspeed);

        fileWrite(MOTOR_FILE_DIRECTION4, Integer.toString(0));
        fileWrite(MOTOR_FILE_SPEED4, cspeed);
    }

    public static void set_counterclockwise() {
        String cspeed = "7000" + '\0';
        fileWrite(MOTOR_FILE_DIRECTION1, Integer.toString(1));
        fileWrite(MOTOR_FILE_SPEED1, cspeed);

        fileWrite(MOTOR_FILE_DIRECTION2, Integer.toString(0));
        fileWrite(MOTOR_FILE_SPEED2, cspeed);

        fileWrite(MOTOR_FILE_DIRECTION3, Integer.toString(0));
        fileWrite(MOTOR_FILE_SPEED3, cspeed);

        fileWrite(MOTOR_FILE_DIRECTION4, Integer.toString(1));
        fileWrite(MOTOR_FILE_SPEED4, cspeed);
    }

    public static void set_go() {
        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION1)) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION2)) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION3)) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION4)) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void set_back() {
        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION1)) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION2)) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION3)) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter fw = new FileWriter(MOTOR_FILE_DIRECTION4)) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void set_left(int speed) {   //no unsigned in java
        String cspeed = Integer.toString(speed) + '\0';

        try (FileWriter fw = new FileWriter(MOTOR_FILE_SPEED2)) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter(MOTOR_FILE_SPEED3)) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void set_right(int speed) {
        String cspeed = Integer.toString(speed) + '\0';

        try (FileWriter fw = new FileWriter(MOTOR_FILE_SPEED1)) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter(MOTOR_FILE_SPEED4)) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void set_car(int FB, int LR, int fast, int slow) {
        if (FB == 1) {
            set_back();
        } else {
            set_go();
        }

        if (LR == 1) {
            set_left(slow);
            set_right(fast);
        } else {
            set_left(fast);
            set_right(slow);
        }
    }

    public static void smart_car_set(int speed, int angle) {
        int dir_FB, dir_LR;
        int pwm_fast, pwm_slow;
        if (speed != 0) {
            if (speed < 0) {
                dir_FB = 1;
                speed = (-1) * speed;
            } else {
                dir_FB = 0;
            }

            if (angle < 0) {
                dir_LR = 1;
                angle = angle * (-1);
            } else {
                dir_LR = 0;
            }
            pwm_fast = 6000 + speed * 80;
            pwm_slow = 6000 + speed * (80 - angle);
        } else {
            dir_FB = 0;
            dir_LR = 0;
            pwm_fast = 0;
            pwm_slow = 0;
        }
        set_car(dir_FB, dir_LR, pwm_fast, pwm_slow);
    }
}
