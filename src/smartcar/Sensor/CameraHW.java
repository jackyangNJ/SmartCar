package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import java.awt.image.BufferedImage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.test.testLogger;

/**
 * The CameraHW class is to manage the samera hardware in the system, and
 * provice related functions for other classes to call, such as camera
 * controlling and providing image
 *
 * @author jack
 */
public class CameraHW implements CameraHWIf {
    public static Log logger=LogFactory.getLog(testLogger.class.getName());
    private static opencv_highgui.CvCapture cvCapture;

    public static void startCamera() {
        cvCapture = opencv_highgui.cvCreateCameraCapture(0);
        if (cvCapture == null) {
            logger.error("cannot get Camera device");
            System.exit(-1);
        }
        logger.info("Camera started");
    }

    public static void stopCamera() {
        opencv_highgui.cvReleaseCapture(cvCapture);
        logger.info("Camera stopped");
    }

    public static opencv_core.IplImage getIplImage() {
        return opencv_highgui.cvQueryFrame(cvCapture);
    }
    public static BufferedImage getBufferedImage() {
        return opencv_highgui.cvQueryFrame(cvCapture).getBufferedImage();
    }
}
