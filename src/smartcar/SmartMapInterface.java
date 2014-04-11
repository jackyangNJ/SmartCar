/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar;
import smartcar.map.SmartMapData;
import smartcar.map.SmartMapInfo;
import smartcar.map.SmartMapQRCode;
import smartcar.map.SmartMapBarrier;
import smartcar.core.Point;
/**
 *
 * @author Administrator
 */
public interface SmartMapInterface {
    public SmartMapBarrier getBarrierInformation();//地图整体障碍物信息
    public SmartMapQRCode getQRCodeInformation();//地图整体二维码位置信息
    public SmartMapBarrier getBarrierInformation(Point p);//地图中某点周围障碍物信息
    public SmartMapQRCode getQRCodeInformation(Point p);//地图中某点周围二维码位置信息
    public SmartMapQRCode getQRCodeInformation(long i);
    public SmartMapData getPath(Point start,Point end);
    public SmartMapInfo getMap();//返回地图整体信息，用于jsp显示
}
