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
public class SmartMapQRCode {
    int num;//二维码个数
    ArrayList<QRCode> qrcodes = new ArrayList<QRCode>();
    void setQRCode(Point p,String l) {
        QRCode b = new QRCode();
        b.p = new Point(p.x,p.y);
        b.l = l;
        qrcodes.add(b);
        num ++;
    }
    void printQRCodes() {
        System.out.println("The number of qrcodes is " + num);
        for(int i = 0;i < num;i++) {
            System.out.println(i + ": " + qrcodes.get(i).p.x + "," + qrcodes.get(i).p.y + qrcodes.get(i).l);
        }
    }
    void printQRCodes(float x,float y) {
        for(int i = 0;i < num;i++) {
            float distance = (qrcodes.get(i).p.y - y) * (qrcodes.get(i).p.y - y) 
                    + (qrcodes.get(i).p.x - x) * (qrcodes.get(i).p.x - x);
            if(distance < 4)
            System.out.println(qrcodes.get(i).p.x + "," + qrcodes.get(i).p.y + "," + qrcodes.get(i).l);
        }
    }
    void printQRCodes(String s) {
        for(int i = 0;i < num;i++) {
            if(qrcodes.get(i).l.equals(s))
                System.out.println(i + ": " + qrcodes.get(i).p.x + "," + qrcodes.get(i).p.y + qrcodes.get(i).l);
        }
    }
    static class QRCode {
        Point p;
        String l;
    }
}
