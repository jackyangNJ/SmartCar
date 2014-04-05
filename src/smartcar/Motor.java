package smartcar;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cshuo
 */
public class Motor {

    public void set_clockwise() {
        String cspeed = "7000"+'\0';
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction1")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm1_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction2")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm2_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction3")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm3_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction4")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm4_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void set_counterclockwise() {
        String cspeed = "7000"+'\0';

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction1")) {
            fw.write(Integer.toString(1));
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm1_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction2")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm2_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction3")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm3_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction4")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm4_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void set_go() {
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction1")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction2")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction3")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction4")) {
            fw.write(Integer.toString(1));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void set_back() {
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction1")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction2")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction3")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/direction4")) {
            fw.write(Integer.toString(0));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void set_left(int speed) {   //no unsigned in java
        String cspeed = Integer.toString(speed)+'\0';
        
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm2_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm3_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void set_right(int speed) {
        String cspeed = Integer.toString(speed)+'\0';
        
        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm1_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter fw = new FileWriter("/sys/class/motors/motor0/pwm4_speed")) {
            fw.write(cspeed);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void set_car(int FB, int LR, int fast, int slow) {
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

    public void smart_car_set(int speed, int angle) {
        int dir_FB, dir_LR;             //NO static!!!!!!!!!
        int pwm_fast, pwm_slow;
        if(speed !=0){
            if (speed < 0) {
                dir_FB = 1;
                speed =(-1)* speed;
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
        }
        else{
            dir_FB=0;
            dir_LR=0;
            pwm_fast=0;
            pwm_slow=0;
        }
        set_car(dir_FB, dir_LR, pwm_fast, pwm_slow);
    }
}
