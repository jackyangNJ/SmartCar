/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class SmartMapBarrier {
    int num;//障碍物个数
    ArrayList<Barrier> barriers = new ArrayList<Barrier>();
    void setBarrier(float x,float y,float length,float width,String s) {
        Barrier b = new Barrier();
        b.x = x;
        b.y = y;
        b.length = length;
        b.width = width;
        b.description = s;
        barriers.add(b);
        num ++;
    }
    void printBarriers() {
        System.out.println("The number of barriers is " + num);
        for(int i = 0;i < num;i++) {
            System.out.println(i + ": " + barriers.get(i).x + "," + barriers.get(i).y + "," + barriers.get(i).length
                    + "," + barriers.get(i).width + "," + barriers.get(i).description);
        }
    }
    void printBarriers(float x,float y) {
        for(int i = 0;i < num;i++) {
            float distance = (barriers.get(i).y - y) * (barriers.get(i).y - y) 
                    + (barriers.get(i).x - x) * (barriers.get(i).x - x);
            if(distance < 4)
            System.out.println(barriers.get(i).x + "," + barriers.get(i).y + "," + barriers.get(i).length
                    + "," + barriers.get(i).width + "," + barriers.get(i).description);
        }
    }
    class Barrier {
        float x;//中心点横坐标
        float y;//中心点纵坐标
        float length;
        float width;
        String description = new String();
    }
}
