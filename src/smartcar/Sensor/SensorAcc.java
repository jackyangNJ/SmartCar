package smartcar.Sensor;

import smartcar.Event.SensorListener;
import smartcar.Event.SensorListener;
import com.googlecode.javacpp.annotation.Const;
import com.googlecode.javacv.cpp.cvkernels;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_legacy.CvRandState;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_videostab;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_video.*;
import java.util.Timer;
import java.util.TimerTask;
import com.googlecode.javacv.*;

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
    CvKalman kalman_x ;
    CvKalman kalman_y ;
    CvMat z1_k ;
    CvMat z2_k;
    
    CvMat x_axle;
    CvMat y_axle;
    SensorAccData data;
    public SensorAcc() {
            kalman_x = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(2, 1, 0) ;//创建kalman for x
            kalman_y = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(2, 1, 0) ;//创建kalman for y
           
           
		/*CvMat x1_k = new CvMat();//for x axle
		x1_k.create(2, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		x1_k.zero();*/
		z1_k = new CvMat(); //for x axle
		z1_k.create(1, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		z1_k.zero();//预测值，即为返回值
		
                /*CvMat x2_k = new CvMat();//for y axle
		x2_k.create(2, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		x2_k.zero();*/
		z2_k = new CvMat(); //for y axle
		z2_k.create(1, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		z2_k.zero();//预测值，即为返回值
                
                
		final float F[] = {1,1,0,1};
		
                System.arraycopy(kalman_x.transition_matrix().data_fl(), 0, F, 0,4);
		System.arraycopy(kalman_y.transition_matrix().data_fl(), 0, F, 0,4);
                //赋值
	    
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_x.measurement_matrix(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_x.process_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_x.measurement_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_x.error_cov_post(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
	    //初始化   
            
            com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_y.measurement_matrix(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_y.process_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_y.measurement_noise_cov(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1e-5));
	    com.googlecode.javacv.cpp.opencv_core.cvSetIdentity(kalman_y.error_cov_post(),com.googlecode.javacv.cpp.opencv_core.cvRealScalar(1));
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
          
 
                
                
	    	x_axle = opencv_video.cvKalmanPredict( kalman_x, null );
                /*获取数据*/
                z1_k.put(0,0,this.getSensorData().getv_x() + this.getSensorData().geta_x() * 1);//1为时间
                z1_k.put(1,0,this.getSensorData().geta_x());
	    	opencv_video.cvKalmanCorrect(kalman_x, z1_k);
	    	
                y_axle = opencv_video.cvKalmanPredict( kalman_y, null );
                /*获取数据*/
                z2_k.put(0,0,this.getSensorData().getv_y() + this.getSensorData().geta_y() * 1);//1为时间
                z2_k.put(1,0,this.getSensorData().geta_y());
	    	opencv_video.cvKalmanCorrect(kalman_y, z2_k);
                
                data.seta_x((float)x_axle.get(1, 0));
                data.seta_y((float)y_axle.get(1, 0));
                data.setv_x((float)x_axle.get(0, 0));
                data.setv_y((float)y_axle.get(0, 0));
                
	    	return data;

    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorAccData getSensorRawData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
