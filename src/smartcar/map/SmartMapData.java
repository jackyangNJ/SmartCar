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

    public void setStartPoint(Point p) {
        this.start = p;
    }

    public Point getStartPoint() {
        return start;
    }

    public void setEndPoint(Point p) {
        this.end = p;
    }

    public Point getEndPoint() {
        return end;
    }

    public void setChild(SmartMapData s) {
        child = s;
    }

    public SmartMapData getChild() {
        return child;
    }

    public double getAngle() {
        double y_t = end.y - start.y;
        double x_t = end.x - start.x;
        if (x_t == 0) {
            return 90;
        } else {
            return Math.atan(y_t / x_t) * (180 / Math.PI);
        }
    }

    @Override
    public String toString() {
        String resultString = "";
        SmartMapData tmp = this;
        while(tmp !=null){
            resultString +="("+ tmp.getStartPoint() + "->" + tmp.getEndPoint()+")";
            tmp = tmp.getChild();
        }
        return resultString;
    }
}
