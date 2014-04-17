package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;


/**
 * 测试log4j的配置
 * @author jack
 */
public class testLogger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        Log logger=LogFactory.getLog("test");
        logger.trace("begin");
        logger.info("hello");
        logger.debug("debug");
        logger.error("dfd");
        
    }
    
}
