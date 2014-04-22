package smartcar.Event;

import java.util.EventListener;


public interface NavigatorListener extends EventListener {
    void NavigatorEventProcess(NavigatorEvent e);
}
