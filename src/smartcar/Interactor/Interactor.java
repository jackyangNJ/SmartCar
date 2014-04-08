package smartcar.Interactor;

import smartcar.SmartMap;

/**
 *
 * @author jack
 */
public class Interactor {
    SmartMap map;
    double CarLocation[];                        //小车当前位置
    double BookLocation[];                       //待查询的书籍的位置
    
  /*  public Interactor(SmartMap map){
        this.map = map;
    }*/
    public Interactor(){
        CarLocation=new double[2];
        BookLocation=new double[2];
    }
    //set the map
    void setMap(SmartMap map){
        this.map=map;
    }
    //set the location of the car
    void setCarLocation(double x,double y){
        CarLocation[0]=x;
        CarLocation[1]=y;
    }
    //set the location of the book
    void setBookLocation(double x,double y){
        BookLocation[0]=x;
        BookLocation[1]=y;
    }
    SmartMap getMap(){
        return map;
    }
    double[] getCarLocation(){
        return CarLocation;
    }
    double[] getBookLocation(){
        return BookLocation;
    }
}
