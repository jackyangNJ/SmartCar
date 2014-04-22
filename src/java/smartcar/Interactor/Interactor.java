package smartcar.Interactor;

import smartcar.Controller.Controller;
import smartcar.Controller.ControllerImpl;
import smartcar.core.Point;
import smartcar.map.SmartMap;

public class Interactor implements InteractorIf {

    private SmartMap map;
    private final Controller controller;

    public Interactor() {
        map = new SmartMap();
        controller = new ControllerImpl(map);

    }

    @Override
    public void setOperation(int op) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCarAutoDriveDestination(Point location) {
        controller.setCarAutoDriveDestination(location);
    }

    @Override
    public SmartMap getSmartMap() {
        return map;
    }

    @Override
    public Point getCarCurrentLocation() {
        return controller.getCarCurrentLocation();
    }

    @Override
    public void setCar(int speed, int angle) {
        controller.setCar(speed, angle);
    }

    @Override
    public void setCarClockwise() {
        controller.setCarClockwise();
    }

    @Override
    public void setCarCounterClockwise() {
        controller.setCarCounterClockwise();
    }

}
