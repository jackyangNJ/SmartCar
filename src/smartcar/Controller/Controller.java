package smartcar.Controller;

import smartcar.SmartMap;
import smartcar.Controller.Navigator.Navigator;
import smartcar.Event.NavigatorEvent;
import smartcar.Event.NavigatorListener;

/**
 *
 * @author jack
 */
public class Controller implements NavigatorListener
{
    SmartMap map;
    Navigator navigator;
    
    
    public Controller(SmartMap map){
        this.map = map;
        navigator=new Navigator(map);
    }

    @Override
    public void NavigatorEventProcess(NavigatorEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
}
