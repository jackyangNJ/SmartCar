package smartcar.test;

import smartcar.Sensor.SensorAcc;
import spiLib.SPIFunc;


public class accTest {	
	public static void main(String[] args) throws InterruptedException {
                SensorAcc  accTest = new SensorAcc();
                while(true){
                    System.out.println("a.x: "+accTest.getSensorRawData().geta_x());
                    System.out.println("a..y: "+accTest.getSensorRawData().geta_y());
                    Thread.sleep(1000);
                }
	}
}
