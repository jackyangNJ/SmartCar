/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.Sensor;

import javax.swing.JButton;

/**
 *
 * @author cs
 */
public class SensorGyroData {

    private float hori_angleSpeed;
    private float hori_angle;

    public SensorGyroData() {
        this(0, 0);
    }

    public SensorGyroData(float horiAngleSpeed, float horiAngle) {
        this.hori_angleSpeed = horiAngleSpeed;
        this.hori_angle = horiAngle;
    }

    /**
     * @return the hori_angleSpeed
     */
    public float getHori_angleSpeed() {
        return hori_angleSpeed;
    }

    /**
     * @param hori_angleSpeed the hori_angleSpeed to set
     */
    public void setHori_angleSpeed(float hori_angleSpeed) {
        this.hori_angleSpeed = hori_angleSpeed;
    }

//    double getangular() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    /**
     * @return the hori_angle
     */
    public float getHori_angle() {
        return hori_angle;
    }

    /**
     * @param hori_angle the hori_angle to set
     */
    public void setHori_angle(float hori_angle) {
        this.hori_angle = hori_angle;
    }
}
