/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test.env;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_32FC1;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author jack
 */
public class testJavaCV {

    public static Log logger = LogFactory.getLog(testJavaCV.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(testJavaCV.class.getResourceAsStream("/config/log4j.properties"));
        opencv_core.CvMat mat = opencv_core.CvMat.create(1, 1, CV_32FC1);
        mat.put(0, 0, 171);
        logger.info(mat.get(0,0));

    }

}
