package smartcar.test.sensor;

import smartcar.Sensor.SensorAcc;


public class testSensorAcc {	
	public static void main(String[] args) throws InterruptedException {
                SensorAcc  accTest = new SensorAcc();
                while(true){
                    System.out.println("a.x: "+accTest.getSensorRawData().geta_x());
                    System.out.println("a.y: "+accTest.getSensorRawData().geta_y());
                     System.out.println("after a.x: "+accTest.getSensorData().geta_x());
                    System.out.println("after a.y: "+accTest.getSensorData().geta_y());
                    Thread.sleep(1000);
                }
	}
}
