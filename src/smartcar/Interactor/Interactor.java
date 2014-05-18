package smartcar.Interactor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Controller.Controller;
import smartcar.Controller.ControllerImpl;
import smartcar.core.Point;
import smartcar.map.SmartMap;
import smartcar.map.SmartMapInfo;
import smartcar.thrift.CarOperation;

public class Interactor implements InteractorIf {

    public static Log logger = LogFactory.getLog(Interactor.class);
    private SmartMap map;
    private Controller controller;

    public Interactor() {
        map = new SmartMap();
//        controller = new ControllerImpl(map);

    }

    @Override
    public void setCarOperation(CarOperation carOperation) {
        controller.setCarOperation(carOperation);
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
}
