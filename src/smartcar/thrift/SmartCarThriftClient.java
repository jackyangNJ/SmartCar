package smartcar.thrift;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import smartcar.core.Utils;
import smartcar.map.SmartMapInfo;

/**
 *
 * @author jack
 */
public class SmartCarThriftClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TException, IOException, ClassNotFoundException {
        TTransport transport = new TSocket("localhost", 10001);

        TProtocol protocol = new TBinaryProtocol(transport);

        SmartCarThrift.Client client = new SmartCarThrift.Client(protocol);
        try {
            transport.open();
        } catch (TTransportException ex) {
            Logger.getLogger(SmartCarThriftClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        client.ping();
        System.err.println(client.getCarAngle());


    }

}
