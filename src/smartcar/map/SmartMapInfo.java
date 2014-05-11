/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class SmartMapInfo implements Serializable{
    int numofy;
    int numofx;
    ArrayList<NodeToDisplay> GridMap = new ArrayList<NodeToDisplay>();
    /*
    void setWidth(double width) {
        this.width = width;
    }
    
    double getWidth() {
        return width;
    }
    
    void setLength(double length) {
        this.length = length;
    }
    
    double getLength() {
        return length;
    }
    
    void setGrid(double grid) {
        this.grid = grid;
    }
    
    double getGrid() {
        return grid;
    }
    */
    void setNumofy(int numofy) {
        this.numofy = numofy;
    }
    
    public int getNumofy() {//二维数组的列数
        return numofy;
    }
    
    void setNumofx(int numofx) {
        this.numofx = numofx;
    }
    
    public int getNumofx() {//二维数组的行数
        return numofx;
    }
    
    void setGridMap(ArrayList GridMap) {
        this.GridMap = GridMap;
    }
    
    public ArrayList getMap() {//表示有障碍物或二维码的点的动态数组
        return GridMap;
    }
    
}
