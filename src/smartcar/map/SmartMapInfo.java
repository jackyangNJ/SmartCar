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
    double getWidth() {
        return SmartMap.width;
    }
    
    double getLength() {
        return SmartMap.length;
    }
    
    double getGrid() {
        return SmartMap.grid;
    }
    
    int getNumofy() {//二维数组的行数
        return SmartMap.numofy;
    }
    
    int getNumofx() {//二维数组的列数
        return SmartMap.numofx;
    }
    
    Node[][] getMap() {//表示地图的二维数组
        return SmartMap.GridMap;
    }
}
