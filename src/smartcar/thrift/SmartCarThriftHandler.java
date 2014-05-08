package smartcar.thrift;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import smartcar.Interactor.Interactor;
import smartcar.Interactor.InteractorIf;
import smartcar.core.Point;

/**
 *
 * @author jack
 */
public class SmartCarThriftHandler implements SmartCarThrift.Iface {

    public static Log logger = LogFactory.getLog(SmartCarThriftHandler.class);
    InteractorIf interactor=new Interactor();

    public SmartCarThriftHandler() {

    }

    @Override
    public void ping() throws TException {
    }

    @Override
    public void setCarAutoDriveDestination(PointThrift location) throws TException {
        logger.info(location);
        interactor.setCarAutoDriveDestination(new Point(location.x, location.y));
    }

    @Override
    public PointThrift getCarCurrentLocation() throws TException {
        Point currentPosition = interactor.getCarCurrentLocation();
        PointThrift pos;
        pos = new  PointThrift(currentPosition.getX(), currentPosition.getY());
        return pos;
        
    }

    @Override
    public void setOperation(CarOperation op) throws TException {
        interactor.setOperation(op.getValue());
    }

    @Override
    public ByteBuffer getSmartMap() throws TException {

        try {
            return getByteBufferFromObject(interactor.getSmartMapInfo());
        } catch (IOException ex) {
            Logger.getLogger(SmartCarThriftHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ByteBuffer getByteBufferFromObject(Serializable obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bout.toByteArray();
        bout.close();
        out.close();
        return ByteBuffer.wrap(bytes);
    }

}
