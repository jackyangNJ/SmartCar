package smartcar.Event;

import java.util.EventListener;


public interface InteractionListener extends EventListener{
    /**
     * 
     * @param e fg
     */
    void CommandPerformed(InteractionEvent e);
}
