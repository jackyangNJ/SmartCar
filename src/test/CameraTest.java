package test;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import smartcar.Sensor.CameraHW;

/**
 * test if CameraHW works
 *
 * @author jack
 */
public class CameraTest {

    public static void main(String[] args)  {
        CameraHW.startCamera();
        opencv_highgui.cvNamedWindow("test");

        while (true) {
            opencv_core.IplImage frame = CameraHW.getImage();
            opencv_highgui.cvShowImage("test", frame);
            if (opencv_highgui.cvWaitKey(30) == 27) {
                break;
            }
        }
        
        opencv_highgui.cvDestroyWindow("test");
        CameraHW.stopCamera();
    }
}
