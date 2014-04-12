/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

/**
 *
 * @author Administrator
 */
public class SmartMapInfo {
    //double width;
    //double length;
    //double grid;
    int numofy;
    int numofx;
    Node[][] GridMap = new Node[numofx][numofy];
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
    
    int getNumofy() {//二维数组的行数
        return numofy;
    }
    
    void setNumofx(int numofx) {
        this.numofx = numofx;
    }
    
    int getNumofx() {//二维数组的列数
        return numofx;
    }
    
    void setGridMap(Node[][] GridMap) {
        this.GridMap = GridMap;
    }
    
    Node[][] getMap() {//表示地图的二维数组
        return GridMap;
    }
}
