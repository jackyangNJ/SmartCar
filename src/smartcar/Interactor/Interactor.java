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
        if(op == FORWARD) {
            setCar(50,0);
        }
        else if(op == BACK) {
            setCar(-50,0);
        }
        else if(op == LEFT) {
            setCar(50,-90);
        }
        else if(op == RIGHT) {
            setCar(50,90);
        }
        else if(op == CLOCKWISE) {
            setCarClockwise();
        }
        else if(op == COUNTERCLOCKWISE) {
            setCarCounterClockwise();
        }
        else if(op == STOP) {
            setCar(0,0);
        }
        
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
