/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar;

/**
 *
 * @author Administrator
 */
public class SmartMapData {//封装每一条小路径
    float start_x;
    float start_y;
    float end_x;
    float end_y;
    SmartMapData child;
    
    public void setStartPoint(float x,float y){
        this.start_x = x;
        this.start_y = y;
    }
    public float[] getStartPoint(){
        float[] start_point = new float[2];
        start_point[0] = start_x;
        start_point[1] = start_y;
        return start_point;
    }
    public void setEndPoint(float x,float y){
        this.end_x = x;
        this.end_y = y;
    }
    public float[] getEndPoint(float x,float y){
        float[] end_point = new float[2];
        end_point[0] = end_x;
        end_point[1] = end_y;
        return end_point;
    }
    public void setChild(SmartMapData s){
        this.start_x = s.start_x;
        this.start_y = s.start_y;
        this.end_x = s.end_x;
        this.end_y = s.end_y;
    }
    public SmartMapData getChild(){
        SmartMapData s = new SmartMapData();
        s.start_x = this.start_x;
        s.start_y = this.start_y;
        s.end_x = this.end_x;
        s.end_y = this.end_y;
        return s;
    }
    
}
