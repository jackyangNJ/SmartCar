package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_highgui;

/**
 * The CameraHW class is to manage the samera hardware in the system, and
 * provice related functions for other classes to call, such as camera
 * controlling and providing image
 *
 * @author jack
 */
public class CameraHW implements CameraHWIf {

    private static opencv_highgui.CvCapture cvCapture;

    static {
        opencv_highgui.CvCapture cvCapture = opencv_highgui.cvCreateCameraCapture(0);
    }
}
