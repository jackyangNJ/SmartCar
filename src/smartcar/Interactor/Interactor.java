package smartcar.Interactor;

import smartcar.map.SmartMap;

/**
 *
 * @author jack
 */
class CarLocation{
double CarLocation_x;                        //小车当前位置
double CarLocation_y;
}

class BookLocation{
double BookLocation_x;                       //待查询的书籍的位置
double BookLocation_y;   
}

public class Interactor {
    private SmartMap map;
    private CarLocation cLocation;
    private BookLocation bLocation;
    public Interactor(SmartMap map){
        this.map = map;
    }
    //set the operation for the car
    
    //set the location of the car
    public void setCarLocation(double x,double y){
        cLocation.CarLocation_x=x;
        cLocation.CarLocation_y=y;
    }
    //set the location of the book
    public void setBookLocation(double x,double y){
        bLocation.BookLocation_x=x;
        bLocation.BookLocation_y=y;
    }
    public SmartMap getMap(){
        return map;
    }
    public CarLocation getCarLocation(){
        return cLocation;
    }
    public BookLocation getBookLocation(){
        return bLocation;
    }
}
