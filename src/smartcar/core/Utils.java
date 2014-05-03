package smartcar.core;

/**
 *
 * @author jack
 */
public class Utils {
    /**
     * 获取两个point之间的距离
     * @param srcPoint
     * @param dstPoint
     * @return 
     */
    public static double getDistance(Point srcPoint,Point dstPoint){
        double disX=srcPoint.getX() - dstPoint.getX();
        double disY=srcPoint.getY()-dstPoint.getY();
        return Math.sqrt(Math.hypot(disX,disY));
    }
    
    /**
     * 在笛卡尔坐标系下，输入源点和终点，返回从X轴逆时针旋转到源点到
     * 终点向量的角度，范围0-360 degree
     * @param srcPoint
     * @param dstPoint
     * @return 
     */
    public static double getAngle(Point srcPoint,Point dstPoint){
        double vectorX= dstPoint.getX()-srcPoint.getX();
        double vectorY = dstPoint.getY()-srcPoint.getY();
        double degree = Math.toDegrees(Math.atan2(vectorY,vectorX));
        if(degree<0) degree = degree+360;        
        return  degree;
    }
    public static void main(String[] args) {
        
    }
}
