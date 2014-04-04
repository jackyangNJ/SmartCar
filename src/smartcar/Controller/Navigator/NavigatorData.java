/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.Controller.Navigator;

/**
 *
 * @author Administrator
 */
public class NavigatorData {
    float v_x;
    float v_y;
    float a_x;
    float a_y;
    float x;
    float y;
    float angular;
    float distance;
    float angular_velocity;
    public NavigatorData(){
        
    }
    public NavigatorData(float v_x , float v_y ,float a_x,float a_y,float x,float y,float angular,float w) {
        this.v_x = v_x;
        this.v_y = v_y;
        this.a_x = a_x;
        this.a_y = a_y;
        this.x = x;
        this.y = y;
        this.angular = angular;
        this.angular_velocity = w;
    }
    
    public float getv_x(){
        return this.v_x;
    }
    public void setv_x(float v_x){
       this.v_x = v_x;
    }
    
    public float getv_y(){
        return this.v_y;
    }
    public void setv_y(float v_y){
       this.v_y = v_y;
    }
    
    public float geta_x(){
        return this.a_x;
    }
    public void seta_x(float a_x){
       this.a_x = a_x;
    }
    
    
    public float geta_y(){
        return this.a_y;
    }
    public void seta_y(float a_y){
       this.a_y = a_y;
    }
    public float getx(){
        return this.x;
    }
    public void setx(float x){
       this.x = x;
    }
    public float gety(){
        return this.y;
    }
    public void sety(float y){
       this.y = y;
    }
    public float getangular(){
        return this.angular;
    }
    public void setangular(float angular){
       this.angular = angular;
    }
    public float getdistance(){
        return this.distance;
    }
    public void setdistance(float distance){
       this.distance = distance;
    }
    public float getangular_velocity(){
        return this.angular_velocity;
    }
    public void setangular_velocity(float w){
       this.angular_velocity = w;
    }
}
