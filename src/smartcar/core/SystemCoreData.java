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
    //current location
    private static Point location;
    //current velocity
    private static Point velocity;
    //current accelerater
    private static Point accelerater;
    //current angle
    private static float angle;
    //current angle_velocity
    private static float angle_velocity;

    public static Point getLocation() {
        return location;
    }

    public static void setLocation(Point location) {
        SystemCoreData.location = location;
    }

    public static Point getVelocity() {
        return velocity;
    }

    public static void setVelocity(Point velocity) {
        SystemCoreData.velocity = velocity;
    }

    public static Point getAccelerater() {
        return accelerater;
    }

    public static void setAccelerater(Point accelerater) {
        SystemCoreData.accelerater = accelerater;
    }

    public static float getAngle() {
        return angle;
    }

    public static void setAngle(float angle) {
        SystemCoreData.angle = angle;
    }

    public static float getAngle_velocity() {
        return angle_velocity;
    }

    public static void setAngule_velocity(float angle_velocity) {
        SystemCoreData.angle_velocity = angle_velocity;
    }

    public static int getSystemState() {
        return systemState;
    }

    /**
     * 设为同步方法，获取系统状态
     * @param systemState 
     */
    public static synchronized void setSystemState(int systemState) {
        SystemCoreData.systemState = systemState;
    }

}
