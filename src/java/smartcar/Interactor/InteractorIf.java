package smartcar.Interactor;

import smartcar.core.Point;
import smartcar.map.SmartMap;


public interface InteractorIf {
    //??????????????????????????????????????干什么的
    public void setOperation(int op);
    
    /**
     * 进入自动驾驶模式，由小车自动行驶到指定的位置
     * @param x
     * @param y 
     */
    public void setCarAutoDriveDestination(Point location);

    
    /**
     * 获取SmartMap Instance
     * @return 
     */
    public SmartMap getSmartMap();
    
    /**
     * 获取小车当前的位置，由Controller提供返回值
     * @return 
     */
    public Point getCarCurrentLocation();

    //？？？？？？？？？？？？？？？？？？？？？？？这是干什么的
//    public Movement getMovement();
    
    /**
     * 手动控制小车，参数为控制小车的速度和角度，速度为正值时，小车往前走，速度为负值，小车朝相反方向 
     * @param speed
     * @param angle 
     */
    public void setCar(int speed,int angle);
    /**
     * 顺时针旋转，non block function
     */
    public void setCarClockwise();
    /**
     * 逆时针旋转,non block function
     */
    public void setCarCounterClockwise();
}
