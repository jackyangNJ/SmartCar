/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

import java.util.ArrayList;
import smartcar.core.Point;

/**
 *
 * @author Administrator
 */
public class SmartMapBarrier {
    int num;//障碍物个数
    ArrayList<Barrier> barriers = new ArrayList<Barrier>();
    void setBarrier(Point p,float length,float width,String s) {
        Barrier b = new Barrier();
        b.p = new Point(p.x,p.y);
        b.length = length;
        b.width = width;
        b.description = s;
        barriers.add(b);
        num ++;
    }
    void printBarriers() {
        System.out.println("The number of barriers is " + num);
        for(int i = 0;i < num;i++) {
            System.out.println(i + ": " + barriers.get(i).p.x + "," + barriers.get(i).p.y + "," + barriers.get(i).length
                    + "," + barriers.get(i).width + "," + barriers.get(i).description);
        }
    }
    void printBarriers(float x,float y) {
        for(int i = 0;i < num;i++) {
            float distance = (barriers.get(i).p.y - y) * (barriers.get(i).p.y - y) 
                    + (barriers.get(i).p.x - x) * (barriers.get(i).p.x - x);
            if(distance < 4)
            System.out.println(barriers.get(i).p.x + "," + barriers.get(i).p.y + "," + barriers.get(i).length
                    + "," + barriers.get(i).width + "," + barriers.get(i).description);
        }
    }
    static class Barrier {
        Point p;
        float length;
        float width;
        String description = new String();
    }
}
