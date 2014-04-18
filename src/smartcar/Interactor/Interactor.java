package smartcar.Interactor;

import smartcar.Controller.Controller;
import smartcar.core.Point;
import smartcar.map.SmartMap;

public class Interactor implements InteractorIf {

    private SmartMap map;
    private Controller controller;

    public Interactor() {
        map = new SmartMap();
        controller = new Controller(map);

    }

    public SmartMap getSmartMap() {
        return map;
    }

    @Override
    public void setOperation(int op) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCarAutoDriveDestination(Point location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmartMap getSmartMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point getCarCurrentLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCar(int speed, int angle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCarClockwise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCarCounterClockwise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
