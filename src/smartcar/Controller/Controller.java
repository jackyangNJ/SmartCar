package smartcar.Controller;

import smartcar.map.SmartMap;
import smartcar.Navigator.Navigator;
import smartcar.Event.NavigatorEvent;
import smartcar.Event.NavigatorListener;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public class Controller implements NavigatorListener
{
    private enum DriveModeType{AUTO,MANUAL};
    private DriveModeType driveMode;
    
    SmartMap map;
    Navigator navigator;
   
       
    /**
     * 超声波事件处理函数
     */
    SensorListener sensorUltrasonicListener = new SensorListener() {

        @Override
        public void SensorEventProcess(SensorEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    public Controller(SmartMap map){
        this.map = map;
        navigator=new Navigator(map);
    }

    @Override
    public void NavigatorEventProcess(NavigatorEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
}
