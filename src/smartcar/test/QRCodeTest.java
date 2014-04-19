/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Sensor.QRCode;

/**
 *
 * @author Kedar
 */
public class QRCodeTest {
	
	public static Log logger = LogFactory.getLog(QRCodeTest.class.getName());
	
	public static void main() throws InterruptedException {
		
		QRCode test = new QRCode();
		
		while (true) {
			logger.info("x is " + test.getQRCodeData().get_map_x() + "and y is " + test.getQRCodeData().get_map_y());
			Thread.sleep(1000);
		}
	}
}
