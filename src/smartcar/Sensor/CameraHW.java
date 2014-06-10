package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.test.env.testLogger;

/**
 * The CameraHW class is to manage the samera hardware in the system, and
 * provice related functions for other classes to call, such as camera
 * controlling and providing image
 *
 * @author jack
 */
public class CameraHW {

    public static Log logger = LogFactory.getLog(testLogger.class.getName());
    private static opencv_highgui.CvCapture cvCapture = null;
    private static opencv_core.IplImage image = null;
    private static final Timer timer;

    static {
        startCamera();
        timer = new Timer("CameraHW");
        if (cvCapture != null) {
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    image = opencv_highgui.cvQueryFrame(cvCapture);
                }
            }, 0, 100);
        }
    }

    public static void startCamera() {
        if (cvCapture != null) {
            return;
        }
        cvCapture = opencv_highgui.cvCreateCameraCapture(0);
        if (cvCapture == null) {
            logger.error("cannot get Camera device");
            System.exit(-1);
        }
        image = opencv_highgui.cvQueryFrame(cvCapture);

        logger.info("Camera started");
    }

    public static void stopCamera() {
        opencv_highgui.cvReleaseCapture(cvCapture);
        logger.info("Camera stopped");
    }

    public static opencv_core.IplImage getIplImage() {
        return image;
    }

    public static BufferedImage getBufferedImage() {
        return image.getBufferedImage();
    }
}
