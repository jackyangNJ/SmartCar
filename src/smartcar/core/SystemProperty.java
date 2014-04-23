package smartcar.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartcar.test.env.testLogger;

/**
 * 获取System.properties配置文件中的数据
 *
 * @author jack
 */
public class SystemProperty {
    public static Log logger = LogFactory.getLog(SystemProperty.class.getName());
    private static final Properties systemProperties;

    static {
        InputStream propertyInputStream = SystemProperty.class.getResourceAsStream("/config/System.properties");
        if (propertyInputStream == null) {
            logger.error("System.property inputstream is null");
        }
        
        systemProperties = new Properties();
        try {
            systemProperties.load(propertyInputStream);
        } catch (IOException ex) {
            logger.error(ex);
        }
        logger.info("SystemProperty initialization OK!");
    }

    public static String getProperty(String key) {
        return systemProperties.getProperty(key).trim();   
    }
}
