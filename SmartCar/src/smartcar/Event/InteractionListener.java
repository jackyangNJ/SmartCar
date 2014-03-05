package smartcar.Event;

import java.util.EventListener;


public interface InteractionListener extends EventListener{
    /**
     * 
     * @param e fgvvb
     */
	
    void CommandPerformed(InteractionEvent e);
}
