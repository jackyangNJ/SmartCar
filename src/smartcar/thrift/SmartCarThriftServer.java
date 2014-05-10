package smartcar.thrift;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class SmartCarThriftServer {
    public static Log logger = LogFactory.getLog(SmartCarThriftServer.class);
    SmartCarThriftHandler handler=new SmartCarThriftHandler();
    SmartCarThrift.Processor processor=new SmartCarThrift.Processor(handler);
    TServerTransport serverTransport;
    TServer server;
    
    public SmartCarThriftServer(){
        try {
            serverTransport= new TServerSocket(10000);
        } catch (TTransportException ex) {
            logger.info(ex);
        }
        
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        tArgs.processor(processor);
        server =new TSimpleServer(tArgs);
    }  
    public void start(){
        server.serve();
    }
    public void stop(){
        server.stop();
    }
    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SmartCarThriftServer server=new SmartCarThriftServer();
        server.start();
    }
}
