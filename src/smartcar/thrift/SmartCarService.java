package smartcar.thrift;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import smartcar.core.Point;
import smartcar.core.Utils;
import smartcar.map.SmartMapInfo;

/**
 *
 * @author jack
 */
public class SmartCarService {

    TTransport transport;
    TProtocol protocol;
    SmartCarThrift.Client client;

    public SmartCarService() {
        transport = new TSocket("localhost", 10001);
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

    public Point getCarCurrentLocation() throws TException {
        PointThrift tmp = client.getCarCurrentLocation();
        return new Point(tmp.x, tmp.y);
    }

    public double getCarCurrentAngle() throws TException {
        return client.getCarAngle();
    }

    public SmartMapInfo getSmartMapInfo() throws TException {
        ByteBuffer dataBuffer = client.getSmartMapInfo();
        try {
            return (SmartMapInfo) Utils.getObjectFromByteBuffer(dataBuffer);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SmartCarService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setYuntaiAngle(int angle) throws TException {
        client.setYuntaiAngle(angle);
    }

    public BufferedImage getCameraImage() throws TException, IOException {
        ByteBuffer byteBuffer=client.getCameraImage();
        ByteArrayInputStream in = new ByteArrayInputStream(byteBuffer.array());
        BufferedImage image = ImageIO.read(in);  
        return image;
    }

    public static void main(String[] args) throws TException, IOException {
        SmartCarService service = new SmartCarService();
        Utils.delay(2000);
        service.setYuntaiAngle(0);
    }
}
