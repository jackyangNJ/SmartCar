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
    ArrayList<QRCode> qrcodes = new ArrayList<>();
    void setQRCode(Point location,String data) {
        QRCode b = new QRCode();
        b.location = new Point(location.x,location.y);
        b.data = data;
        qrcodes.add(b);
        num ++;
    }
    void printQRCodes() {
        System.out.println("The number of qrcodes is " + num);
        for(int i = 0;i < num;i++) {
            System.out.println(i + ": " + qrcodes.get(i).location.x + "," + qrcodes.get(i).location.y + qrcodes.get(i).data);
        }
    }
    void printQRCodes(float x,float y) {
        for(int i = 0;i < num;i++) {
            float distance = (qrcodes.get(i).location.y - y) * (qrcodes.get(i).location.y - y) 
                    + (qrcodes.get(i).location.x - x) * (qrcodes.get(i).location.x - x);
            if(distance < 4)
            System.out.println(qrcodes.get(i).location.x + "," + qrcodes.get(i).location.y + "," + qrcodes.get(i).data);
        }
    }
    void printQRCodes(String s) {
        for(int i = 0;i < num;i++) {
            if(qrcodes.get(i).data.equals(s))
                System.out.println(i + ": " + qrcodes.get(i).location.x + "," + qrcodes.get(i).location.y + qrcodes.get(i).data);
        }
    }
    static class QRCode {
        Point location;
        String data;
    }
}
