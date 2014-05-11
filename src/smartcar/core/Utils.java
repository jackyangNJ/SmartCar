package smartcar.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.test.sensor.testArduinoBridge;

/**
 *
 * @author jack
 */
public class Utils {

    public static Log logger = LogFactory.getLog(Utils.class);

    /**
     * 获取两个point之间的距离
     *
     * @param srcPoint
     * @param dstPoint
     * @return
     */
    public static double getDistance(Point srcPoint, Point dstPoint) {
        double disX = srcPoint.getX() - dstPoint.getX();
        double disY = srcPoint.getY() - dstPoint.getY();
        return Math.sqrt(Math.hypot(disX, disY));
    }

    /**
     * 在笛卡尔坐标系下，输入源点和终点，返回从X轴逆时针旋转到源点到 终点向量的角度，范围0-360 degree
     *
     * @param srcPoint
     * @param dstPoint
     * @return
     */
    public static double getAngle(Point srcPoint, Point dstPoint) {
        double vectorX = dstPoint.getX() - srcPoint.getX();
        double vectorY = dstPoint.getY() - srcPoint.getY();
        double degree = Math.toDegrees(Math.atan2(vectorY, vectorX));
        if (degree < 0) {
            degree = degree + 360;
        }
        return degree;
    }

    /**
     * use System Thread.sleep to implements delay function
     *
     * @param time ms
     */
    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }

    public static String excuteSysCommand(String cmd) {
        //返回与当前 Java 应用程序相关的运行时对象
        Runtime run = Runtime.getRuntime();
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令  
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            String execResult = new String();
            while ((lineStr = inBr.readLine()) != null) //获得命令执行后在控制台的输出信息  
            {
                execResult += lineStr;
            }

            //检查命令是否执行失败。  
            //p.exitValue()==0表示正常结束，1：非正常结束  
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {
                    logger.error(p);
                }
            }
            inBr.close();
            in.close();
            return execResult;
        } catch (IOException | InterruptedException e) {
            logger.error(e);
        }
        return null;

    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        logger.info(excuteSysCommand("cat /sys/class/ulwave/ulwave0/trigger"));
    }
}
