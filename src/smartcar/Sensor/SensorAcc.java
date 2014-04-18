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
 Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            defaultEnable();
            getSensorData();
            fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_GYRO_TYPE, getSensorData()));
        }
    };
    private CvKalman kalman ;
    
    private CvMat z_k ;
   
    public static final double time = 0.01;
    
    private CvMat xy_axle;

    private SensorAccData data;
    
    private SPIFunc spi;
    
    private  final String routePath = "/dev/spidev32766.0";
    private ArrayList<SensorListener> SensorListeners;
    
   
    
    public SensorAcc() {
        
        
        spi = new SPIFunc(routePath);
        data = new SensorAccData(0,0,0,0,0,0);
        kalman = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(6, 2, 0) ;
      
		z_k = new CvMat(); 
		z_k.create(2, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		z_k.zero();
		
                
		final float F[][] = {{1,0,(float)time,0,0,0},{0,1,0,(float)time,0,0,},{0,0,1,0,(float)time,0},{0,0,0,1,0,(float)time},{0,0,0,0,1,0},{0,0,0,0,0,1}};
		
                System.arraycopy(kalman.transition_matrix().data_fl(), 0, F, 0,36);
		
                //¸³Öµ
	    
	    cvSet2D(kalman.measurement_matrix(),0,4,CvScalar.ONE);
            cvSet2D(kalman.measurement_matrix(),1,5,CvScalar.ONE);
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.process_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.measurement_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.error_cov_post(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
	    //³õÊ¼»¯   
    
    //³õÊ¼»¯¼ÓËÙ¶È´«¸ÐÆ÷
    
    }
    
     public void defaultEnable(){
        wirte((byte) 0x1f, 0x52);
	byte temp = (byte)this.read((byte) 0x2d);
	temp = (byte) (temp|0x02);
	wirte((byte) 0x2d, temp);
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

    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
	
	public void wirte(byte addr, int value){
		
		byte[] output = new byte[3];
		output[0]=0x0a;
		output[1] = addr;
		output[2] = 0x00;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      
    }
     public SensorAccData kalmanData(SensorAccData sensoraccdata){
         SensorAccData accdata = new SensorAccData(0,0,0,0,0,0);
         xy_axle = opencv_video.cvKalmanPredict( kalman, null );
         z_k.put(0,1,sensoraccdata.geta_x());
         z_k.put(1,0,sensoraccdata.geta_y());
	 opencv_video.cvKalmanCorrect(kalman, z_k);
         accdata.seta_x((float)z_k.get(0, 0));
         accdata.seta_y((float)z_k.get(1, 0));
         accdata.setv_x((float)xy_axle.get(2,0));
         accdata.setv_y((float)xy_axle.get(3,0));
          accdata.setx((float)xy_axle.get(0,0));
         accdata.sety((float)xy_axle.get(1,0));
         return accdata;
     }
    
}
