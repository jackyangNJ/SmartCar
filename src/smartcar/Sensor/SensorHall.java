package smartcar.Sensor;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;

/**
 *
 * @author jack
 */
public class SensorHall implements SensorHallIf {

    private ArrayList<SensorListener> SensorListeners;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            getState();
        }
    };

    public SensorHall() {
        //start timer
    }

    private void getState() {

    }

    /**
     * trigger SensorEvent
     *
     * @param e SensorEvent
     */
    private void processSensorEvent(SensorEvent e) {
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

}
