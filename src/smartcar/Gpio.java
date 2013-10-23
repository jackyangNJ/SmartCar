package smartcar;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jack
 */
public class Gpio implements GpioInterface {

    private int port;
    private String path;
    private String direction="in";

    public Gpio(int port) {
        this.init(port);
    }

    @Override
    public void init(int port) {
        this.port = port;
        path = "/sys/class/gpio/gpio" + Integer.toString(port) + "/";   /**/
        export(port);
    }

    private void export(int port) {
        try (FileWriter fw = new FileWriter("/sys/class/gpio/export")) {
            fw.write(Integer.toString(port));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.out.print(port + "has export");
        }
    }

    private void unexport(int port) {
        try (FileWriter fw = new FileWriter("/sys/class/gpio/unexport")) {
            fw.write((new Integer(port)).toString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
        	 System.out.print(port + "has unexport");
        }
    }

    @Override
    public void setDirection(String direction) {
        if (this.direction.equals(direction)) 
            return;
        this.direction = direction;
        try (FileWriter fw = new FileWriter(path  + "direction")) {
            fw.write(direction);
            fw.close();
        } catch (IOException ex) {
            //Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }

    @Override
    public void setValue(int value) {
        try (FileWriter fw = new FileWriter(path + "value")) {
            fw.write(Integer.toString(value));
            fw.close();
        } catch (IOException ex) {
            //Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getValue() {
        char[] cbuf = new char[10];
        try (FileReader fr = new FileReader(path + "value")) {
            fr.read(cbuf);
            fr.close();
        } catch (IOException ex) {
            //Logger.getLogger(Gpio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cbuf[0] == '1') {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public void close() {
        unexport(port);
    }
}
