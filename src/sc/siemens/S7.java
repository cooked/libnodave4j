package sc.siemens;

import com.serotonin.messaging.DefaultMessagingExceptionHandler;
import com.serotonin.messaging.MessagingExceptionHandler;

/**
 * Base level for Siemens communication
 * 
 * @author scottafavi
 */
public class S7 {
    private MessagingExceptionHandler exceptionHandler = new DefaultMessagingExceptionHandler();

    public void setExceptionHandler(MessagingExceptionHandler exceptionHandler) {
        if (exceptionHandler == null)
            this.exceptionHandler = new DefaultMessagingExceptionHandler();
        else
            this.exceptionHandler = exceptionHandler;
    }

    public MessagingExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }
}
