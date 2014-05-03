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
public class SensorAccData {
    double v_x;
    double v_y;
    double a_x;
    double a_y;
    double x;
    double y;

    public SensorAccData(){
        this(0,0,0,0,0,0);
    }
    public SensorAccData(double v_x , double v_y ,double a_x,double a_y,double x,double y) {
        this.v_x = v_x;
        this.v_y = v_y;
        this.a_x = a_x;
        this.a_y = a_y;
        this.x = x;
        this.y = y;
    }
    
    public double getv_x(){
        return this.v_x;
    }
    public void setv_x(double v_x){
       this.v_x = v_x;
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
    
}
