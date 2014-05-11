package smartcar.Sensor;

public class SensorUltrasonicData {
/**
 * Distance1-3标识三个超声波传感器测量的距离
 */
    double Distance1;
    double Distance2;
    double Distance3;

    public SensorUltrasonicData() {

    }

    public double getDistance1() {
        return Distance1;
    }

    public void setDistance1(double Distance1) {
        this.Distance1 = Distance1;
    }

    public double getDistance2() {
        return Distance2;
    }

    public void setDistance2(double Distance2) {
        this.Distance2 = Distance2;
    }

    public double getDistance3() {
        return Distance3;
    }

    public void setDistance3(double Distance3) {
        this.Distance3 = Distance3;
    }

    
}
