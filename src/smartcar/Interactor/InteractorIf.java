package smartcar.Interactor;

import smartcar.map.SmartMap;


public interface InteractorIf {
    public void setOperation(int op);
    //set the location of the car
    public void setCarLocation(double x,double y);
    //set the location of the book
    public void setBookLocation(double x,double y);
    public SmartMap getMap();
    public CarLocation getCarLocation();
    public BookLocation getBookLocation();
    public Movement getMovement();
}
