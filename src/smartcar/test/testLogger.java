package smartcar.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;


/**
 * 测试log4j的配置
 * @author jack
 */
public class testLogger {
    Log logger=LogFactory.getLog(testLogger.class.getName());
    
    public void a(){
        logger.info("A");
        b();
    }
    public void b(){
        logger.info("B");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure("src/config/log4j.properties");
        testLogger teLogger=new testLogger();
        teLogger.a();
        
    }
    
}
