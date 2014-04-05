/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class SmartMapQRCode {
    int num;//二维码个数
    ArrayList<QRCode> qrcodes = new ArrayList<QRCode>();
    void setQRCode(float x,float y,float length,float width,String s) {
        QRCode b = new QRCode();
        b.x = x;
        b.y = y;
        b.description = s;
        qrcodes.add(b);
        num ++;
    }
    void printQRCodes() {
        System.out.println("The number of qrcodes is " + num);
        for(int i = 0;i < num;i++) {
            System.out.println(i + ": " + qrcodes.get(i).x + "," + qrcodes.get(i).y + qrcodes.get(i).description);
        }
    }
    void printQRCodes(float x,float y) {
        for(int i = 0;i < num;i++) {
            float distance = (qrcodes.get(i).y - y) * (qrcodes.get(i).y - y) 
                    + (qrcodes.get(i).x - x) * (qrcodes.get(i).x - x);
            if(distance < 4)
            System.out.println(qrcodes.get(i).x + "," + qrcodes.get(i).y + "," + qrcodes.get(i).description);
        }
    }
    class QRCode {
        float x;//中心点横坐标
        float y;//中心点纵坐标
        String description = new String();
    }
}
