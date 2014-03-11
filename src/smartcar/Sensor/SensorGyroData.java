/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.Sensor;

/**
 *
 * @author jack
 */
public class SensorGyroData {
    float angular ;
    float angular_velocity;
    
    public SensorGyroData(float angular , float angular_velocity) {
        
        this.angular = angular;
        this.angular_velocity = angular_velocity;
    }
    
    public  float getangular(){
        return this.angular;
    }
    
    public  float getangular_velocity(){
        return this.angular_velocity;
    }
    
    public void setangular(float angular){
        this.angular = angular;
    }
    
    public  void setangular_velocity(float angular_velocity){
        this.angular_velocity = angular_velocity;
    }
    
}
