package smartcar.test.sensor;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author jack
 */
public class testWebcam {
public static Log logger = LogFactory.getLog(CameraTest.class.getName());
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        Webcam.setAutoOpenMode(true);
        Webcam webcam=Webcam.getDefault();
        Dimension dimension[]=webcam.getViewSizes();
        for (Dimension dimension1 : dimension) {
            logger.info(dimension1.height);
            logger.info(dimension1.width);
        }
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        ImageIO.write(image, "PNG", new File("test.png"));

    }

}
