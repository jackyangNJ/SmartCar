package smartcar.thrift;

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
import smartcar.core.Point;

/**
 *
 * @author jack
 */
public class SmartCarThriftClient {

    public static Object getObject(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException  {
        InputStream input = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream oi = new ObjectInputStream(input);
        Object obj = oi.readObject();
        input.close();
        oi.close();
        byteBuffer.clear();
        return obj;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TException, IOException, ClassNotFoundException {
        TTransport transport = new TSocket("localhost", 10000);

        TProtocol protocol = new TBinaryProtocol(transport);

        SmartCarThrift.Client client = new SmartCarThrift.Client(protocol);
        try {
            transport.open();
        } catch (TTransportException ex) {
            Logger.getLogger(SmartCarThriftClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.ping();
        ByteBuffer data= client.getSmartMap();
        
        Point p=(Point)getObject(data);
        System.err.println(p);
        transport.close();

    }

}
