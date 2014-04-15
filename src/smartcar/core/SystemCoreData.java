package smartcar.core;

/**
 *
 * @author jack
 */
public class SystemCoreData {

    //constant for systemState
    public static int STATE_GOAHEAD = 0x1;
    public static int STATE_GOBACK = 0x2;
    public static int STATE_GOLEFT = 0x4;
    public static int STATE_GORIGHT = 0x8;
    public static int STATE_STILL = 0x10;
    
    private static int systemState;
    //position
    static float x;
    static float y;
    //velocity
    private static float v_x;
    private static float v_y;
    //accelerater
    private static float a_x;
    private static float a_y;
    //angular
    static float angular;
    static float angular_velocity;

    public static int getSystemState() {
        return systemState;
    }

    public static void setSystemState(int systemState) {
        SystemCoreData.systemState = systemState;
    }

    public static float getX() {
        return x;
    }

    public static void setX(float x) {
        SystemCoreData.x = x;
    }

    public static float getY() {
        return y;
    }

    public static void setY(float y) {
        SystemCoreData.y = y;
    }

    public static float getV_x() {
        return v_x;
    }

    public static void setV_x(float v_x) {
        SystemCoreData.v_x = v_x;
    }

    public static float getV_y() {
        return v_y;
    }

    public static void setV_y(float v_y) {
        SystemCoreData.v_y = v_y;
    }

    public static float getA_x() {
        return a_x;
    }

    public static void setA_x(float a_x) {
        SystemCoreData.a_x = a_x;
    }

    public static float getA_y() {
        return a_y;
    }

    public static void setA_y(float a_y) {
        SystemCoreData.a_y = a_y;
    }

    public static float getAngular() {
        return angular;
    }

    public static void setAngular(float angular) {
        SystemCoreData.angular = angular;
    }

    public static float getAngular_velocity() {
        return angular_velocity;
    }

    public static void setAngular_velocity(float angular_velocity) {
        SystemCoreData.angular_velocity = angular_velocity;
    }
}
