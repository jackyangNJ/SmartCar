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
   public void setNumofy(int numofy) {
        this.numofy = numofy;
    }
    
    public int getNumofy() {//二维数组的行数
        return numofy;
    }
    
    public void setNumofx(int numofx) {
        this.numofx = numofx;
    }
    
    public int getNumofx() {//二维数组的列数
        return numofx;
    }
    
    public void setGridMap(Node[][] GridMap) {
        this.GridMap = GridMap;
    }
    
    public Node[][] getMap() {//表示地图的二维数组
        return GridMap;
    }
}
