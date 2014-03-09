package smartcar.Controller;

import smartcar.SmartMap;
import smartcar.Controller.Navigator.Navigator;
import smartcar.Event.NavigatorListener;

/**
 *
 * @author jack
 */
public class Controller implements NavigatorListener{
    SmartMap map;
    Navigator navigator;
    
    
    public Controller(SmartMap map){
        this.map = map;
        navigator=new Navigator(map);
    }
            
}
