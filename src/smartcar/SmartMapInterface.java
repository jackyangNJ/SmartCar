/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar;

/**
 *
 * @author Administrator
 */
public interface SmartMapInterface {
    public SmartMapBarrier getBarrierInformation();//地图整体障碍物信息
    public SmartMapQRCode getQRCodeInformation();//地图整体二维码位置信息
    public SmartMapBarrier getBarrierInformation(float x,float y);//地图中某点周围障碍物信息
    public SmartMapQRCode getQRCodeInformation(float x,float y);//地图中某点周围二维码位置信息
    public SmartMapData getPath(float start_x,float start_y,float end_x,float end_y);
}
