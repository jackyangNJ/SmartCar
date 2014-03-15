/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.Sensor;

/**
 *
 * @author cs
 */
public class SensorGyroData {
    private float hori_angleSpeed;        
    
    public  SensorGyroData(){
        
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
}