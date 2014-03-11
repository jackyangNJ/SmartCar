package smartcar.Sensor;

import smartcar.Event.SensorListener;
import com.googlecode.javacpp.annotation.Const;
import com.googlecode.javacv.cpp.cvkernels;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_legacy.CvRandState;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_videostab;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_video.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.googlecode.javacv.*;
/**
 *
 * @author jack
 */
public class SensorGyro implements SensorGyroIf{
    
    
        
    //private ArrayList<SensorListener> SensorListeners;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            getSensorData();
        }
    };
    CvMat z_k ;
    CvMat y_k ;
    CvKalman kalman;
    SensorGyroData data;
    public SensorGyro() {
    
            	kalman = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(2, 1, 0) ;//创建kalman
             
		z_k = new CvMat();
		z_k.create(1, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		z_k.zero();//预测值，即为返回值
		
		final float F[] = {1,1,0,1};
		
                System.arraycopy(kalman.transition_matrix().data_fl(), 0, F, 0,4);
		//赋值
	    
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman.measurement_matrix(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
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
        public SensorGyroData getSensorData(){
      
                          
	    	y_k = opencv_video.cvKalmanPredict( kalman, null );
                z_k.put(0,0,this.getSensorGyroData().getangular() + this.getSensorGyroData().getangular_velocity() * 1);    
	    	z_k.put(1,0,this.getSensorGyroData().getangular_velocity());
                opencv_video.cvKalmanCorrect(kalman, z_k);
          
                data.setangular((float)y_k.get(0,0));//
                data.setangular_velocity((float)z_k.get(1, 0));
                
	    	return data;

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        @Override
        public SensorGyroData getSensorGyroData() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

}
