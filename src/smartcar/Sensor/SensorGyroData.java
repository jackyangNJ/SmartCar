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

    private double hori_angleSpeed;
    private double hori_angle;

    public SensorGyroData() {
        this(0, 0);
    }

    public SensorGyroData(double horiAngleSpeed, double horiAngle) {
        this.hori_angleSpeed = horiAngleSpeed;
        this.hori_angle = horiAngle;
    }

    /**
     * @return the hori_angleSpeed
     */
    public double getHori_angleSpeed() {
        return hori_angleSpeed;
    }

    /**
     * @param hori_angleSpeed the hori_angleSpeed to set
     */
    public void setHori_angleSpeed(double hori_angleSpeed) {
        this.hori_angleSpeed = hori_angleSpeed;
    }

    /**
     * @return the hori_angle
     */
    public double getHori_angle() {
        return hori_angle;
    }

    /**
     * @param hori_angle the hori_angle to set
     */
    public void setHori_angle(double hori_angle) {
        this.hori_angle = hori_angle;
    }
}
