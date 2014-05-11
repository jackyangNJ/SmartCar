package smartcar.Interactor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Controller.Controller;
import smartcar.Controller.ControllerImpl;
import smartcar.core.Point;
import smartcar.map.SmartMap;
import smartcar.map.SmartMapInfo;

public class Interactor implements InteractorIf {

    public static Log logger = LogFactory.getLog(Interactor.class);
    private SmartMap map;
    private Controller controller;

    public Interactor() {
        map = new SmartMap();
        controller = new ControllerImpl(map);

    }

    @Override
    public void setOperation(int op) {
        controller.setOperation(op);
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
