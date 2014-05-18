/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test.sensor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Sensor.CameraHW;
import smartcar.Sensor.QRCode;
import smartcar.Sensor.QRCodeData;

/**
 *
 * @author Kedar
 */
public class QRCodeTest implements SensorListener {

    public static Log logger = LogFactory.getLog(QRCodeTest.class.getName());
    QRCode test = new QRCode();

    public QRCodeTest() {
        test.addSenserListener(this);
    }

    public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        CameraHW.startCamera();

        while (true) {

        }
    }

    @Override
    public void SensorEventProcess(SensorEvent e) {
        QRCodeData data = (QRCodeData) e.getData();
        logger.info(data.get_position());
    }
}
