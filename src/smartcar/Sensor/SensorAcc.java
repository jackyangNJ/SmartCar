package smartcar.Sensor;

import smartcar.Event.SensorListener;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_video.*;
import java.util.Timer;
import java.util.TimerTask;
import static com.googlecode.javacv.cpp.opencv_core.cvSet2D;
import java.util.ArrayList;
import smartcar.Event.SensorEvent;
import spiLib.SPIFunc;
import smartcar.Sensor.SensorAccData;
/**
 *
 * @author jack
 */

public class SensorAcc implements SensorAccIf{
 Timer timer = new Timer("Acc");
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            defaultEnable();
            readAccData();
            fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_ACC_TYPE, getSensorRawData()));
        }
    };
    private CvKalman kalman ;
    
    private CvMat z_k ;
   
    public static final double time = 0.01;
    
    //10ms
    public static final long frequency = 1000;
    
    private CvMat xy_axle;

    private SensorAccData data;
    
    private SPIFunc spi;
    
    private  final String routePath = "/dev/spidev32766.0";
    private ArrayList<SensorListener> SensorListeners;
    
   
    
    public SensorAcc() {
        spi = new SPIFunc(routePath);
        data = new SensorAccData(0,0,0,0,0,0);
        timer.scheduleAtFixedRate(task, 0, frequency);
        kalman = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(6, 2, 0) ;
      
//		z_k = new CvMat(); 
		z_k = CvMat.create(2, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
//		z_k.zero();

                
//	       final float F[][] = {{1,0,(float)time,0,0,0},{0,1,0,(float)time,0,0,},{0,0,1,0,(float)time,0},{0,0,0,1,0,(float)time},{0,0,0,0,1,0},{0,0,0,0,0,1}};
             
               for(int i = 0 ; i < 6 ; i++)
               {
                   for(int j = 0 ;j < 6; j++)
                   {
                       if(i != j)
                       kalman.transition_matrix().put(i, j, 0);
                       else
                       kalman.transition_matrix().put(i, j, 1);
                   }
                   
               }
               kalman.transition_matrix().put(0, 2, time);
               kalman.transition_matrix().put(1, 3, time);
               kalman.transition_matrix().put(2, 4, time);
               kalman.transition_matrix().put(3, 5, time);
               
//               kalman.transition_matrix().put(0, 0, 1);kalman.transition_matrix().put(1, 0, 0);kalman.transition_matrix().put(2, 0, time);
//               kalman.transition_matrix().put(3, 0, 0);kalman.transition_matrix().put(4, 0, 0);kalman.transition_matrix().put(5, 0, 0);
//               kalman.transition_matrix().put(1, 0, 0);kalman.transition_matrix().put(1, 1, 1);kalman.transition_matrix().put(1, 2, 0);
//               kalman.transition_matrix().put(1, 3, time);kalman.transition_matrix().put(1, 4,0);kalman.transition_matrix().put(1, 5, 0);
//               kalman.transition_matrix().put(0, 0, 1);kalman.transition_matrix().put(0, 0, 1);kalman.transition_matrix().put(0, 0, 1);
//               kalman.transition_matrix().put(0, 0, 1);kalman.transition_matrix().put(0, 0, 1);kalman.transition_matrix().put(0, 0, 1);
               

                //¸³Öµ

	    kalman.measurement_matrix().put(0,4,1);
            kalman.measurement_matrix().put(1,5,1);
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.process_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.measurement_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.error_cov_post(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
	    //³õÊ¼»¯   
    
    //³õÊ¼»¯¼ÓËÙ¶È´«¸ÐÆ÷
    
    }
    
     public void defaultEnable(){
        write((byte) 0x1f, (byte)0x52);
        write((byte) 0x26, (byte)0x20);
        write((byte) 0x2c, (byte)0x13);
	byte temp = (byte)this.read((byte) 0x2d);
	temp = (byte) (temp|0x02);
	write((byte) 0x2d, temp);
    }
    
    @Override
    public void addSenserListener(SensorListener listener) {
        
        if(SensorListeners == null){
            SensorListeners = new ArrayList<>(2);
        }
        if(!SensorListeners.contains(listener)){
            SensorListeners.add(listener);
        }
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSenserListener(SensorListener listener) {
     if((SensorListeners != null) && SensorListeners.contains(listener)){
            SensorListeners.remove(listener);
        }   
    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorAccData getSensorData() {
                
	    	 return this.kalmanData(data);

//     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void readAccData(){       
        readxalxe();
        readyalxe();
    }

    	public void readxalxe() {
		byte xalxeLow;
		byte xalxeHigh;
		xalxeLow = (byte)this.read((byte) 0x0e);
		xalxeHigh = (byte)this.read((byte)0x0f);
		int value = xalxeHigh << 8 | xalxeLow ;               
		data.seta_x(value);
	}

	public void readyalxe(){
		byte yalxeLow;
		byte yalxeHigh;
		yalxeLow = (byte)this.read((byte) 0x10);
		yalxeHigh = (byte)this.read((byte)0x11);
		int value = yalxeHigh << 8 | yalxeLow ;                
		data.seta_y(value);
	}


	public int read(byte addr){
		byte[] input = new byte[3];
		input[0]=0x0b;
		input[1] = addr;
		input[2] = 0x00;
		int value = spi.RWBytes(input, 3);
		return input[2];
	}

	public void write(byte addr, byte value){
		byte[] output = new byte[3];
		output[0]=0x0a;
		output[1] = addr;
		output[2] = value;
		int a = spi.RWBytes(output, 3);
	}

    private void fireSensorEventProcess(SensorEvent e){
        ArrayList list;
        synchronized(this){
            if(SensorListeners == null){
                return;
            }
            list = (ArrayList)SensorListeners.clone();
        }
        for(int i=0;i<list.size();i++){
            SensorListener listener = (SensorListener)list.get(i);
            listener.SensorEventProcess(e);
        }
    }
    
    @Override
    public SensorAccData getSensorRawData() {
            return this.data;
    }
    
     public SensorAccData kalmanData(SensorAccData sensoraccdata){
         SensorAccData accdata = new SensorAccData(0,0,0,0,0,0);
         xy_axle = opencv_video.cvKalmanPredict( kalman, null );
         z_k.put(0,0,sensoraccdata.geta_x());
         z_k.put(1,0,sensoraccdata.geta_y());
	 opencv_video.cvKalmanCorrect(kalman, z_k);
         accdata.seta_x((float)xy_axle.get(4, 0));
         accdata.seta_y((float)xy_axle.get(5, 0));
         accdata.setv_x((float)xy_axle.get(2,0));
         accdata.setv_y((float)xy_axle.get(3,0));
          accdata.setx((float)xy_axle.get(0,0));
         accdata.sety((float)xy_axle.get(1,0));
         return accdata;
     }
    
}
