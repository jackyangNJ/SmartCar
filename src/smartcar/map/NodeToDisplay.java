/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;

import java.io.Serializable;

/**
 *封装返回给网页显示的点的信息（包括该点是否有障碍物和是否有二维码以及相应的信息）
 * @author Administrator
 */
public class NodeToDisplay implements Serializable{
    private static final long serialVersionUID = 14634665734237362L;   
    int x;
    int y;
    private boolean barriermask = false;
    SmartMapBarrier.Barrier b = new SmartMapBarrier.Barrier();
    private boolean qrcodemask = false;
    SmartMapQRCodeInfo q = new SmartMapQRCodeInfo();
    

    public NodeToDisplay(int x,int y) {
        this.x = x;
        this.y = y;
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

    /**
     *
     * @return
     */
    public SmartMapBarrier.Barrier getBarrierInfo() {
        return b;
    }

    /**
     *
     * @return
     */
    public SmartMapQRCodeInfo getQRCodeInfo() {
        return q;
    }
}
