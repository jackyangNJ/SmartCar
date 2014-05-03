package smartcar.core;

/**
 * Point类是用来描述工程中需要的二维信息，单位是米
 * @author Administrator
 */
public class Point {
    public double x;//横坐标
    public double y;//纵坐标
    public Point(double x,double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
