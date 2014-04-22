package smartcar.test;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Sensor.CameraHW;

/**
 * test if CameraHW works
 *
 * @author jack
 */
public class CameraTest {

    public static Log logger = LogFactory.getLog(CameraTest.class.getName());

    public static void main(String[] args) throws FrameGrabber.Exception {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
//        opencv_core.IplImage frame = CameraHW.getIplImage();
//        opencv_highgui.cvSaveImage("test.jpg", frame);
//        logger.info("take a picrute");
//        CameraHW.stopCamera();
        
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);  
        grabber.start();
        opencv_core.IplImage frame = grabber.grab();
        opencv_highgui.cvSaveImage("test.jpg", frame);
        grabber.setImageHeight(640);
        logger.info(grabber.getImageHeight());
        logger.info(grabber.getPixelFormat());
        logger.info(grabber.getImageWidth());
        logger.info(grabber.getLengthInFrames());
        logger.info(grabber.getFrameRate());
        logger.info(grabber.getSampleRate());
        
        grabber.stop();
        
        
    }
}
