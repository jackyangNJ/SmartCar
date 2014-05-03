package smartcar.Sensor;

public class SensorHallData {
    /**
     * 标识行走距离
     */
    double  DriveDistance;
    
    public SensorHallData(double distance){
        this.DriveDistance = distance;
    }
    
    
    public void setDriveDistance(double distance){
        this.DriveDistance = distance;
    }
    public double getDriveDistance(){
        return this.DriveDistance;
    }
}
