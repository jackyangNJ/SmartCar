/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

import smartcar.core.Point;

/**
 *
 * @author Administrator
 */
public class SmartMapData {//封装每一条小路径
    Point start;
    Point end;
    SmartMapData child;
    
    public void setStartPoint(Point p){
        this.start = new Point(p.x,p.y);
    }
    public Point getStartPoint(){
        return start;
    }
    public void setEndPoint(Point p){
        this.end = new Point(p.x,p.y);
    }
    public Point getEndPoint(float x,float y){
        return end;
    }
    public void setChild(SmartMapData s){
        child.start = s.start;
        child.end = s.end;
    }
    public SmartMapData getChild(){
        SmartMapData s = new SmartMapData();
        s.start = child.start;
        s.end = child.end;
        return s;
    }
    
}
