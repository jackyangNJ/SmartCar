package smartcar.test;

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

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        CameraHW.startCamera();
        opencv_core.IplImage frame = CameraHW.getIplImage();
        opencv_highgui.cvSaveImage("test.jpg", frame);
        CameraHW.stopCamera();
    }
}
