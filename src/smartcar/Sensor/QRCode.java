package smartcar.Sensor;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorListener;
import smartcar.Event.SensorEvent;
import smartcar.core.Utils;
import smartcar.test.sensor.QRCodeTest;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author Kedar
 */
public class QRCode implements QRCodeIf {

    public static Log logger = LogFactory.getLog(QRCode.class.getName());
    private ArrayList<SensorListener> SensorListeners;
    private String content;
    private QRCodeData qrcd;

    // every 100ms doing a decoding conduct which means 10Hz
    private final int readFrequency = 100;

    Timer timer = new Timer("QRcode");
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            content = decode();
            if (content != null) {
                fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_QRCODE_TYPE, getQRCodeData()));
            }
        }
    };

    String decode() {
        Result result = null;

//        BufferedImage image = CameraHW.getBufferedImage();
        BufferedImage image = null ;
        try {
            image = ImageIO.read(new File("e:/test.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(QRCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (image == null) {
                System.out.println("the decode image may be not exit.");
                return null;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<Object, Object> hints = new Hashtable<>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (NotFoundException e) {
            logger.error(e);
        }
        return null;
    }

    public QRCode() {

        content = null;
        qrcd = new QRCodeData();
//        timer.scheduleAtFixedRate(task, 0, readFrequency);
    }

    /**
     * 触发监听事件
     *
     * @param e
     */
    private void fireSensorEventProcess(SensorEvent e) {
        ArrayList list;
        synchronized (this) {
            if (SensorListeners == null) {
                return;
            }
            list = (ArrayList) SensorListeners.clone();
        }
        for (int i = 0; i < list.size(); i++) {
            SensorListener listener = (SensorListener) list.get(i);
            listener.SensorEventProcess(e);
        }
    }

    /**
     * add SensorListener
     *
     * @param listener
     */
    @Override
    public void addSenserListener(SensorListener listener) {
        if (SensorListeners == null) {
            SensorListeners = new ArrayList<>(2);
        }
        if (!SensorListeners.contains(listener)) {
            SensorListeners.add(listener);
        }
    }

    /**
     * remove SensorListener
     *
     * @param listener
     */
    @Override
    public void removeSenserListener(SensorListener listener) {
        if ((SensorListeners != null) && SensorListeners.contains(listener)) {
            SensorListeners.remove(listener);
        }
    }

    @Override
    public QRCodeData getQRCodeData() {
        // The string pattern is x:#12#,y:#34#.
        qrcd.set_position(content);
        return qrcd;
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        QRCode testCode = new QRCode();
        while (true) {
            testCode.logger.info(testCode.decode());

        }
    }
}
