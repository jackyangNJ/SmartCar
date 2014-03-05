package smartcar.Event;

import java.util.EventListener;


public interface InteractionListener extends EventListener{
    /**
     * 
     * @param e 不过分
     */
    void CommandPerformed(InteractionEvent e);
}
