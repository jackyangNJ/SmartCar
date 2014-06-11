package smartcar.test.sensor;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
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

    public static void main(String[] args) throws FrameGrabber.Exception, IOException {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        while (true) {
            opencv_core.IplImage frame = CameraHW.getIplImage();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            logger.info(frame.getBufferedImage().getColorModel());
            ImageIO.write(frame.getBufferedImage(), "jpeg", out);
            ByteBuffer buffer = ByteBuffer.wrap(out.toByteArray());
            logger.info("take a picrute");
        }

//        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);  
//        grabber.start();
//        opencv_core.IplImage frame = grabber.grab();
//        opencv_highgui.cvSaveImage("test.jpg", frame);
//        grabber.setImageHeight(640);
//        logger.info(grabber.getImageHeight());
//        logger.info(grabber.getPixelFormat());
//        logger.info(grabber.getImageWidth());
//        logger.info(grabber.getLengthInFrames());
//        logger.info(grabber.getFrameRate());
//        logger.info(grabber.getSampleRate());
//        
//        grabber.stop();
    }
}
