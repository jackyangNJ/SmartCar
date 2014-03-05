package smartcar.Event;

import java.util.EventListener;


public interface InteractionListener extends EventListener{
    /**
     * 
     * @param e 的
     */
    void CommandPerformed(InteractionEvent e);
}
