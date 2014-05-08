package smartcar.Interactor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import smartcar.map.SmartMapInfo;
import smartcar.thrift.CarOperation;
import smartcar.thrift.PointThrift;
import smartcar.thrift.SmartCarThrift;

/**
 *
 * @author jack
 */
public class SmartCarService {

    TTransport transport;
    TProtocol protocol;
    SmartCarThrift.Client client;

    public SmartCarService() {
        transport = new TSocket("localhost", 10000);
        protocol = new TBinaryProtocol(transport);
        client = new SmartCarThrift.Client(protocol);

        try {
            transport.open();
        } catch (TTransportException ex) {
            Logger.getLogger(SmartCarService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            client.ping();
        } catch (TException ex) {
            Logger.getLogger(SmartCarService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    
    public void setOperation(CarOperation op) throws TException {
        client.setOperation(op);
    }

    
    public void setCarAutoDriveDestination(PointThrift location) throws TException {
        client.setCarAutoDriveDestination(location);
    }

    
    public PointThrift getCarCurrentLocation() throws TException {
        return client.getCarCurrentLocation();
    }

    
    public SmartMapInfo getSmartMap()  throws TException{
        ByteBuffer dataBuffer =client.getSmartMap();
        try {
            return (SmartMapInfo)getObject(dataBuffer);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SmartCarService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Object getObject(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        ObjectInputStream oi;
        Object obj;
        try (InputStream input = new ByteArrayInputStream(byteBuffer.array())) {
            oi = new ObjectInputStream(input);
            obj = oi.readObject();
        }
        oi.close();
        byteBuffer.clear();
        return obj;
    }

}
