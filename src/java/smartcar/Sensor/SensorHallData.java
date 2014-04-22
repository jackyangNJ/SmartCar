package smartcar.Sensor;

public class SensorHallData {
    /**
     * 标识行走距离
     */
    float  DriveDistance;
    
    public SensorHallData(float distance){
        this.DriveDistance = distance;
    }
    
    
    public void setDriveDistance(float distance){
        this.DriveDistance = distance;
    }
    public float getDriveDistance(){
        return this.DriveDistance;
    }
}
