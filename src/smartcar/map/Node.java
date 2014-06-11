/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.map;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
//Node对象用于封装节点信息，包括名字和子节点
public class Node{
    private String name;
    private transient Map<Node, Double> child = new HashMap<>();
    private boolean barriermask = false;
    SmartMapBarrier.Barrier b = new SmartMapBarrier.Barrier();
    private boolean qrcodemask = false;
    SmartMapQRCodeInfo q = new SmartMapQRCodeInfo();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Node, Double> getChild() {
        return child;
    }

    public void setChild(Map<Node, Double> child) {
        this.child = child;
    }

    public void setblack(SmartMapBarrier.Barrier b) {//设置为障碍物点
        barriermask = true;
        this.b = b;
    }

    public boolean getblack() {
        return barriermask;
    }

    public void setQRCode(SmartMapQRCodeInfo q) {//设置二维码
        qrcodemask = true;
        this.q = q;
    }

    public boolean getQRCode() {
        return qrcodemask;
    }

    public SmartMapBarrier.Barrier getBarrierInfo() {
        return b;
    }

    public SmartMapQRCodeInfo getQRCodeInfo() {
        return q;
    }

}
