package smartcar.Interactor;

import smartcar.SmartMap;

/**
 *
 * @author jack
 */
public class Interactor {
    SmartMap map;
    int CarLocation[];                        //小车当前位置
    int BookLocation[];                       //待查询的书籍的位置
    
  /*  public Interactor(SmartMap map){
        this.map = map;
    }*/
    public Interactor(){
        CarLocation=new int[2];
        BookLocation=new int[2];
    }
    //set the map
    void setMap(SmartMap map){
        this.map=map;
    }
    //set the location of the car
    void setCarLocation(int x,int y){
        CarLocation[0]=x;
        CarLocation[1]=y;
    }
    //set the location of the book
    void setBookLocation(int x,int y){
        BookLocation[0]=x;
        BookLocation[1]=y;
    }
    SmartMap getMap(){
        return map;
    }
    int[] getCarLocation(){
        return CarLocation;
    }
    int[] getBookLocation(){
        return BookLocation;
    }
}
