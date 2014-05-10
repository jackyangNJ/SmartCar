package smartcar.Interactor;

import smartcar.Controller.Controller;
import smartcar.Controller.ControllerImpl;
import smartcar.core.Point;
import smartcar.map.SmartMap;
import smartcar.map.SmartMapInfo;

public class Interactor implements InteractorIf {

    private SmartMap map;
    private final Controller controller;

    public Interactor() {
        map = new SmartMap();
        controller = new ControllerImpl(map);

    }

    @Override
    public void setOperation(int op) {
        if(op == InteractorIf.FORWARD) {
            setCar(50,0);
        }
        else if(op == InteractorIf.BACK) {
            setCar(-50,0);
        }
        else if(op == InteractorIf.LEFT) {
            setCar(50,-90);
        }
        else if(op == InteractorIf.RIGHT) {
            setCar(50,90);
        }
        else if(op == InteractorIf.CLOCKWISE) {
            setCarClockwise();
        }
        else if(op == InteractorIf.COUNTERCLOCKWISE) {
            setCarCounterClockwise();
        }
        else if(op == InteractorIf.STOP) {
            setCar(0,0);
        }
        
    }

    @Override
    public void setCarAutoDriveDestination(Point location) {
        controller.setCarAutoDriveDestination(location);
    }

    @Override
    public SmartMapInfo getSmartMapInfo() {
        return map.getMapInfo();
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
