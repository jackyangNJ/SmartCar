package smartcar.thrift;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.thrift.TException;
import smartcar.Interactor.Interactor;
import smartcar.Interactor.InteractorIf;
import smartcar.core.Point;
import smartcar.core.SystemProperty;
import smartcar.core.Utils;
import smartcar.map.SmartMapInfo;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class SmartCarThriftHandler implements SmartCarThrift.Iface {

    public static Log logger = LogFactory.getLog(SmartCarThriftHandler.class);
    private final double gridSize = Double.parseDouble(SystemProperty.getProperty("Map.Grid.Size"));
    public InteractorIf interactor;

    public SmartCarThriftHandler() {
        interactor = new Interactor();
    }

    @Override
    public void ping() throws TException {
        logger.info("ping");
    }

    @Override
    public void setCarAutoDriveDestination(PointThrift location) throws TException {
        logger.info("setCarAutoDriveDestination callby");
        logger.info(location);
        interactor.setCarAutoDriveDestination(new Point(location.x, location.y));
    }

    @Override
    public PointThrift getCarCurrentLocation() throws TException {
        logger.info("getCarCurrentLocation callby");
        Point currentPosition = interactor.getCarCurrentLocation();
        PointThrift pos;
        pos = new PointThrift((int) currentPosition.getX() / gridSize, (int) currentPosition.getY() / gridSize);
        return pos;
    }

    @Override
    public void setOperation(CarOperation op) throws TException {
        logger.info("setCarOperation = " + op);
        interactor.setCarOperation(op);
    }

    @Override
    public ByteBuffer getSmartMapInfo() throws TException {
        try {
            logger.info("getSmartMapInfo");
            
            SmartMapInfo info = interactor.getSmartMapInfo();
            ByteBuffer buffer = Utils.getByteBufferFromObject(info);
            return buffer;
        } catch (IOException ex) {
            logger.error(ex);
        }
        return null;

    }

    
    @Override
    public double getCarAngle() throws TException {
        return interactor.getCarAngle();
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SmartCarThriftHandler handler = new SmartCarThriftHandler();

        try {
            handler.getSmartMapInfo();
        } catch (TException ex) {
            logger.error(ex);

        }

    }

}
