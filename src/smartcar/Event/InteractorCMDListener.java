package smartcar.Event;

import java.util.EventListener;


public interface InteractorCMDListener extends EventListener{
    /**
     * 
     * @param e 的
     */
    void CommandPerformed(InteractorCMDEvent e);
}
