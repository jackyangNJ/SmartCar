/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.map;
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
    public SmartMapBarrier.Barrier getBarrierInformation(Point p);//地图中某点周围障碍物信息
    public SmartMapQRCodeInfo getQRCodeInformation(Point p);    public SmartMapQRCodeInfo getQRCodeInformation(String s);
    public SmartMapData getPath(Point start,Point end);
    public SmartMapInfo getMapInfo();//返回地图整体信息，用于jsp显示
}
