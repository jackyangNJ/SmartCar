package smartcar.thrift;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.thrift.TException;
import smartcar.Interactor.Interactor;
import smartcar.Interactor.InteractorIf;
import smartcar.core.Point;
import smartcar.map.SmartMapInfo;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class SmartCarThriftHandler implements SmartCarThrift.Iface {

    public static Log logger = LogFactory.getLog(SmartCarThriftHandler.class);
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
        pos = new PointThrift(currentPosition.getX(), currentPosition.getY());
        return pos;
    }

    @Override
    public void setOperation(CarOperation op) throws TException {
        logger.info("setCarOperation = " + op);
        interactor.setCarOperation(op);
    }

    @Override
    public ByteBuffer getSmartMapInfo() throws TException {
        logger.info("getSmartMapInfo");
        try {
            SmartMapInfo info = interactor.getSmartMapInfo();
            return getByteBufferFromObject(info);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return null;
    }

    private static ByteBuffer getByteBufferFromObject(Serializable obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bout.toByteArray();
        bout.close();
        out.close();
        return ByteBuffer.wrap(bytes);
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
