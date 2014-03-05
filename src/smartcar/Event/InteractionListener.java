package smartcar.Event;

import java.util.EventListener;


public interface InteractionListener extends EventListener{
    /**
     * 
     * @param e 
     */
    void CommandPerformed(InteractionEvent e);
}
