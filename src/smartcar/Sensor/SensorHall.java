package smartcar.Sensor;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.core.SystemProperty;

/**
 *
 * @author jack
 */
public class SensorHall implements SensorHallIf, SensorListener {

    public static Log logger = LogFactory.getLog(ArduinoBridgeImpl.class.getName());
    private ArrayList<SensorListener> SensorListeners;

    //Car Wheel girth,unit m
    private static final float WheelGirth = Float.parseFloat(SystemProperty.getProperty("WheelGirth"));

    public SensorHall() {

    }

    /**
     * trigger SensorEvent
     *
     * @param e SensorEvent
     */
    private void fireSensorEventProcess(SensorEvent e) {
        ArrayList list;
        synchronized (this) {
            if (SensorListeners == null) {
                return;
            }
            list = (ArrayList) SensorListeners.clone();
        }
        for (int i = 0; i < list.size(); i++) {
            SensorListener listener = (SensorListener) list.get(i);
            listener.SensorEventProcess(e);
        }
    }

    /**
     * add SensorListener
     *
     * @param listener
     */
    @Override
    public synchronized void addSenserListener(SensorListener listener) {
        if (SensorListeners == null) {
            SensorListeners = new ArrayList<>(2);
        }
        if (!SensorListeners.contains(listener)) {
            SensorListeners.add(listener);
        }
    }

    /**
     * remove SensorListener
     *
     * @param listener
     */
    @Override
    public synchronized void removeSenserListener(SensorListener listener) {
        if ((SensorListeners != null) && SensorListeners.contains(listener)) {
            SensorListeners.remove(listener);
        }
    }

    @Override
    public void SensorEventProcess(SensorEvent e) {
        logger.info("Receive Hall Event");
        SensorHallData sensorHallData=new SensorHallData(WheelGirth);
        fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_HALL_TYPE, sensorHallData));
    }
    
    public static void main(String[]args){
        
    }

}
