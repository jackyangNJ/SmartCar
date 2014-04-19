/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar.test;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_32F;
import static com.googlecode.javacv.cpp.opencv_core.CV_32FC1;

/**
 *
 * @author jack
 */
public class testJavaCV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        opencv_core.CvMat mat = opencv_core.CvMat.create(1, 1, CV_32FC1);
        mat.put(0, 0, 1);
        

    }

}
