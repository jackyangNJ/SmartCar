package smartcar.Sensor;

import smartcar.Event.SensorListener;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_video.*;
import java.util.Timer;
import java.util.TimerTask;
import static com.googlecode.javacv.cpp.opencv_core.cvSet2D;

/**
 *
 * @author jack
 */

public class SensorAcc implements SensorAccIf{
 Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            getSensorData();
        }
    };
    private CvKalman kalman ;
    
    private CvMat z_k ;
   
    public static final double time = 0.01;
    
    private CvMat xy_axle;

    SensorAccData data;

    public SensorAcc() {
            kalman = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(6, 2, 0) ;//创建kalman for x
      

		z_k = new CvMat(); //for x axle
		z_k.create(2, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		z_k.zero();//预测值，即为返回值
		
                
		final float F[][] = {{1,0,(float)time,0,0,0},{0,1,0,(float)time,0,0,},{0,0,1,0,(float)time,0},{0,0,0,1,0,(float)time},{0,0,0,0,1,0},{0,0,0,0,0,1}};
		
                System.arraycopy(kalman.transition_matrix().data_fl(), 0, F, 0,4);
		
                //赋值
	    
	    cvSet2D(kalman.measurement_matrix(),0,4,CvScalar.ZERO);
            cvSet2D(kalman.measurement_matrix(),1,5,CvScalar.ONE);
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.process_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.measurement_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.error_cov_post(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
	    //初始化   
    }
    
    @Override
    public void addSenserListener(SensorListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSenserListener(SensorListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorAccData getSensorData() {
                
	    	return data;

    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorAccData getSensorRawData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     public SensorAccData kalmanData(SensorAccData sensoraccdata){
         SensorAccData accdata = new SensorAccData(0,0,0,0);
         xy_axle = opencv_video.cvKalmanPredict( kalman, null );
         z_k.put(0,1,sensoraccdata.geta_x());//1为时间
         z_k.put(1,0,sensoraccdata.geta_y());
	 opencv_video.cvKalmanCorrect(kalman, z_k);
         accdata.seta_x((float)z_k.get(0, 0));
         accdata.seta_y((float)z_k.get(1, 0));
         accdata.setv_x((float)xy_axle.get(2, 0));
         accdata.setv_y((float)xy_axle.get(3,0));
         return accdata;
     }
    
}
