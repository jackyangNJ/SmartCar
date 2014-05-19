/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SmartMapInfo implements Serializable {

    private static final long serialVersionUID = 1463466574567362L;
    int numofy;
    int numofx;
    List<NodeToDisplay> GridMap = new ArrayList<>();

    public SmartMapInfo() {

    }

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

    void setGridMap(List GridMap) {
        this.GridMap = GridMap;
    }

    public List getMap() {//表示有障碍物或二维码的点的动态数组
        return GridMap;
    }

    @Override
    public String toString(){
        return "numofx="+numofx+" numofy="+numofy;
    }
 
}
