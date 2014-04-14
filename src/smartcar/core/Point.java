package smartcar.core;

/**
 * Point类是用来描述工程中需要的二维信息，单位是米
 * @author Administrator
 */
public class Point {
    public float x;//横坐标
    public float y;//纵坐标
    public Point(float x,float y) {
        this.x = x;
        this.y = y;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
