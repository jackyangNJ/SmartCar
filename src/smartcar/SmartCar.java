/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar;

import smartcar.Interactor.Interactor;
import smartcar.Controller.Controller;

/**
 *
 * @author jack
 */
public class SmartCar {
    
    Controller controller;
    SmartMap map;
    Interactor interactor;
    
    public SmartCar(){
        map=new SmartMap();
        controller=new Controller(map);
        interactor = new Interactor(map);
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
