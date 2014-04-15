package smartcar.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author jack
 */
public class SystemLogger {

    private static final Logger LOG;

    static {
        LOG = Logger.getLogger(SystemLogger.class.getName());
        
        FileHandler fh = null;

        try {
            fh = new FileHandler("z:/className.log");
        } catch (IOException | SecurityException ex) {
            System.err.println("Cannot open system log file");
            System.exit(-1);
        }
        
        fh.setFormatter(new SimpleFormatter());
        LOG.addHandler(fh);
    }

    public static void info(String msg) {
        LOG.info(msg);
    }

    public static void severe(String msg) {
        LOG.severe(msg);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SystemLogger.info("hello");
    }

} 
