package smartcar.Sensor;

import gnu.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */

public class ArduinoBridgeImpl implements ArduinoBridge {

    private ArrayList<SensorListener> SensorListeners;
    private Map<Integer, ArrayList> listenerTypeMap = new HashMap<>();
    private String defaultComNameString="COM4";
    private SerialComm serialComm= new SerialComm(defaultComNameString);
    
    public ArduinoBridgeImpl() {
        
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

    class SerialComm implements SerialPortEventListener {
        CommPortIdentifier portId; //串口通信管理类
        Enumeration portList;   //已经连接上的端口的枚举
        private InputStream inputStream; //从串口来的输入流
        private OutputStream outputStream;//向串口输出的流
        private SerialPort serialPort;     //串口的引用
        private int serialRate = 9600;
        private String comName = "COM4";

        public SerialComm() {
            init();
        }
        
        public SerialComm(String comName) {
            this.comName = comName;
            init();
        }
        void init() {
            portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {//判断如果端口类型是串口
                    if (!portId.getName().equals(comName)) { 
                        return;
                    }
                }
            }   
            try {
                serialPort = (SerialPort) portId.open("OutCpuPort", 2000);//打开串口名字为myapp,延迟为2毫秒
            } catch (PortInUseException e) {
            }
            try {
                inputStream = serialPort.getInputStream();
                outputStream = serialPort.getOutputStream();
            } catch (IOException e) {
            }
            
            //给当前串口添加一个监听器
            try {
                serialPort.addEventListener(this);      
            } catch (TooManyListenersException e) {
            }
            
            //当有数据时通知
            serialPort.notifyOnDataAvailable(true); 
            
            //设置串口读写参数
            try {
                serialPort.setSerialPortParams(serialRate, SerialPort.DATABITS_8, 
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            } catch (UnsupportedCommOperationException e) {
            }
        }

        //SerialPortEventListener 的方法,监听的时候会不断执行
        @Override
        public void serialEvent(SerialPortEvent event) {
            switch (event.getEventType()) {
                case SerialPortEvent.BI:
                case SerialPortEvent.OE:
                case SerialPortEvent.FE:
                case SerialPortEvent.PE:
                case SerialPortEvent.CD:
                case SerialPortEvent.CTS:
                case SerialPortEvent.DSR:
                case SerialPortEvent.RI:
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                    break;
                case SerialPortEvent.DATA_AVAILABLE://当有可用数据时读取数据,并且给串口返回数据
                    byte[] readBuffer = new byte[200];
                    int numBytes = 0;
                    try {
                        if (inputStream.available() > 0) {
                            numBytes = inputStream.read(readBuffer);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ArduinoBridgeImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    System.out.println(new String(readBuffer, 0, numBytes));
                    
                    //according the first the byte to dispatch the message
                    int type= readBuffer[0];
                    fireSensorEventProcess(type, new SensorEvent(this, SensorEvent.SENSOR_ARDUINO_TYPE, readBuffer));
                    break;
            }
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
}
