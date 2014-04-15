package smartcar.Interactor;

import smartcar.core.Point;
import smartcar.map.SmartMap;


public class Interactor {
    private SmartMap map;
    private Point cLocation;
    private Point bLocation;
    public Interactor(SmartMap map){
        this.map = map;
    }
    //set the operation for the car
    
    //set the location of the car
    public void setCarLocation(double x,double y){
    //TODO
        
    }
    public SmartMap getMap(){
        return map;
    }
    
}
