/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.Navigator;

/**
 *
 * @author Administrator
 */
public class NavigatorData {
    double v_x;
    double v_y;
    double a_x;
    double a_y;
    double x;
    double y;
    double angular;
    double distance;
    double angular_velocity;
    
    public NavigatorData(){
        
    }
    public NavigatorData(double v_x , double v_y ,double a_x,double a_y,double x,double y,double angular,double w) {
        this.v_x = v_x;
        this.v_y = v_y;
        this.a_x = a_x;
        this.a_y = a_y;
        this.x = x;
        this.y = y;
        this.angular = angular;
        this.angular_velocity = w;
    }
    
    public double getv_x(){
        return this.v_x;
    }
    public void setv_x(double v_x){
       this.v_x = v_x;
    }
    
    public double getv_y(){
        return this.v_y;
    }
    public void setv_y(double v_y){
       this.v_y = v_y;
    }
    
    public double geta_x(){
        return this.a_x;
    }
    public void seta_x(double a_x){
       this.a_x = a_x;
    }
    
    
    public double geta_y(){
        return this.a_y;
    }
    public void seta_y(double a_y){
       this.a_y = a_y;
    }
    public double getx(){
        return this.x;
    }
    public void setx(double x){
       this.x = x;
    }
    public double gety(){
        return this.y;
    }
    public void sety(double y){
       this.y = y;
    }
    public double getangular(){
        return this.angular;
    }
    public void setangular(double angular){
       this.angular = angular;
    }
    public double getdistance(){
        return this.distance;
    }
    public void setdistance(double distance){
       this.distance = distance;
    }
    public double getangular_velocity(){
        return this.angular_velocity;
    }
    public void setangular_velocity(double w){
       this.angular_velocity = w;
    }
}
