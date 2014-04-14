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
    //set the location of the book
    public void setBookLocation(double x,double y){
        bLocation.BookLocation_x=x;
        bLocation.BookLocation_y=y;
    }
    public SmartMap getMap(){
        return map;
    }
    
}
