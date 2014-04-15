package smartcar.test;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import smartcar.Sensor.CameraHW;

/**
 * test if CameraHW works
 *
 * @author jack
 */
public class CameraTest {

    public static void main(String[] args) {
        CameraHW.startCamera();
        opencv_core.IplImage frame = CameraHW.getIplImage();
        opencv_highgui.cvSaveImage("z:/teset.jpg", frame);
        CameraHW.stopCamera();
    }
}
