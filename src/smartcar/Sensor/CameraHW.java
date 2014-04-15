package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import java.awt.image.BufferedImage;

/**
 * The CameraHW class is to manage the samera hardware in the system, and
 * provice related functions for other classes to call, such as camera
 * controlling and providing image
 *
 * @author jack
 */
public class CameraHW implements CameraHWIf {

    private static opencv_highgui.CvCapture cvCapture;

    public static void startCamera() {
        cvCapture = opencv_highgui.cvCreateCameraCapture(0);
        if (cvCapture == null) {
            System.err.println("cannot get camera");
            System.exit(-1);
        }
    }

    public static void stopCamera() {
        opencv_highgui.cvReleaseCapture(cvCapture);
    }

    public static opencv_core.IplImage getIplImage() {
        return opencv_highgui.cvQueryFrame(cvCapture);
    }
    public static BufferedImage getBufferedImage() {
        return opencv_highgui.cvQueryFrame(cvCapture).getBufferedImage();
    }
}
