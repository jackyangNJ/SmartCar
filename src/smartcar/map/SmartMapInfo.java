/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class SmartMapInfo implements Serializable{
    int numofy;
    int numofx;
    Node[][] GridMap;
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
    
    public int getNumofy() {//二维数组的行数
        return numofy;
    }
    
    void setNumofx(int numofx) {
        this.numofx = numofx;
    }
    
    public int getNumofx() {//二维数组的列数
        return numofx;
    }
    
    void setGridMap(Node[][] GridMap) {
        this.GridMap = GridMap;
    }
    
    public Node[][] getMap() {//表示地图的二维数组
        return GridMap;
    }
    
}
