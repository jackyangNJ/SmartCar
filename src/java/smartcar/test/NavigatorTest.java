/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.Navigator.Navigator;


/**
 *
 * @author Administrator
 */
public class NavigatorTest {
      public static Log logger = LogFactory.getLog(CameraTest.class.getName());

   public static void main(String[] args) throws InterruptedException {
                Navigator navigatorTest = new Navigator();
                while(true){
                    System.out.println("a.x: "+ navigatorTest.getNavigatorDate().geta_x());
                    System.out.println("a.y: "+ navigatorTest.getNavigatorDate().geta_y());
                    System.out.println("v.x: "+ navigatorTest.getNavigatorDate().getv_x());
                    System.out.println("v.y: "+ navigatorTest.getNavigatorDate().getv_y());
                    System.out.println("x: "+ navigatorTest.getNavigatorDate().getx());
                    System.out.println("y: "+ navigatorTest.getNavigatorDate().gety());
                    System.out.println("angular: "+ navigatorTest.getNavigatorDate().getangular());
                    System.out.println("distance: "+ navigatorTest.getNavigatorDate().getdistance());
                    Thread.sleep(1000);
                }
	}
}
