package smartcar.Sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public class ArduinoBridgeImpl implements ArduinoBridge, SerialPortEventListener {

    public static Log logger = LogFactory.getLog(ArduinoBridgeImpl.class.getName());
    private ArrayList<SensorListener> SensorListeners = new ArrayList<>();
    private Map<Integer, ArrayList> listenerTypeMap = new HashMap<>();
    private jssc.SerialPort serialPort;
    private Thread thread;

    public ArduinoBridgeImpl(String serialName, int serialRate) {
        init(serialName, serialRate);
    }

    private void init(String serialName, int serialRate) {
        serialPort = new jssc.SerialPort(serialName);
        try {
            //Open port
            if (!serialPort.openPort()) {
                logger.info("cannot open serial port");
            }
            logger.info("open serial port");

            //purge
            serialPort.purgePort(jssc.SerialPort.PURGE_TXCLEAR);
            serialPort.purgePort(jssc.SerialPort.PURGE_RXCLEAR);
            int n = serialPort.getInputBufferBytesCount();
            byte[] tmp = serialPort.readBytes(n);
            
            //Set params
            serialPort.setParams(serialRate, 8, 1, 0);

            //Set mask
            serialPort.setEventsMask(jssc.SerialPort.MASK_RXCHAR);

            //create thread to monitor serial port
            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            byte[] buffer = serialPort.readBytes(2);
                            int msgType = buffer[0];
                            SensorEvent event = new SensorEvent(this, SensorEvent.SENSOR_HALL_TYPE, buffer);
                            fireSensorEventProcess(msgType, event);
                        } catch (SerialPortException ex) {
                            logger.error(ex);
                        }
                    }
                }
            }, "ArduinoBridge");
            
            thread.start();

        } catch (SerialPortException ex) {
            logger.error(ex);
        }
        logger.info("ArduinoBrdge init ok!");
    }

    /**
     * trigger SensorEvent
     *
     * @param e SensorEvent
     */
    private void fireSensorEventProcess(int type, SensorEvent e) {
        ArrayList<SensorListener> list = listenerTypeMap.get(type);
        synchronized (this) {
            if (list == null) {
                return;
            }
            list = (ArrayList) list.clone();
        }
        for (int i = 0; i < list.size(); i++) {
            SensorListener listener = (SensorListener) list.get(i);
            listener.SensorEventProcess(e);
        }
    }

    @Override
    public synchronized void unregisterMessageListener(int type, SensorListener listener) {
        if (listenerTypeMap.containsKey(type)) {
            ArrayList<SensorListener> list = listenerTypeMap.get(type);
            if (list.contains(listener)) {
                list.remove(listener);
            }
        }
    }

    @Override
    public synchronized boolean registerMessageListener(int type, SensorListener listener) {
        ArrayList<SensorListener> list;
        if (listenerTypeMap.containsKey(type)) {
            list = listenerTypeMap.get(type);
        } else {
            list = new ArrayList<>(2);
            listenerTypeMap.put(type, list);
        }
        if (!SensorListeners.contains(listener)) {
            list.add(listener);
        }
        return true;
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        logger.info("Serial Event from arduino!");
        //If data is available
        if (spe.isRXCHAR()) {
            try {
                logger.info("buffer length = " + serialPort.getInputBufferBytesCount());
                byte buffer[] = serialPort.readBytes(2);
                int msgType = buffer[0];
                SensorEvent event = new SensorEvent(this, SensorEvent.SENSOR_HALL_TYPE, buffer);
                fireSensorEventProcess(msgType, event);
            } catch (SerialPortException ex) {
                logger.error(ex);
            }
        }
    }

}
