package smartcar.Interactor;

import smartcar.core.Point;
import smartcar.map.SmartMap;
import smartcar.map.SmartMapInfo;
import smartcar.thrift.CarOperation;


public interface InteractorIf {
    
    public void setCarOperation(CarOperation carOperation);
    
    /**
     * 进入自动驾驶模式，由小车自动行驶到指定的位置
     * @param location 
     */
    public void setCarAutoDriveDestination(Point location);

    
    /**
     * 获取SmartMap Instance
     * @return 
     */
    public SmartMapInfo getSmartMapInfo();
    
    /**
     * 获取小车当前的位置，由Controller提供返回值
     * @return 
     */
    public Point getCarCurrentLocation();

    /**
     * 获取小车实时的角度
     * @return
     */
    public double getCarAngle();
    
    /**
     * 手动控制小车，参数为控制小车的速度和角度，速度为正值时，小车往前走，速度为负值，小车朝相反方向 
     * @param speed
     * @param angle 
     */
    public void setCar(int speed,int angle);

    
}
