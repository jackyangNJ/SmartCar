/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar;

/**
 *
 * @author jack
 */
public interface GpioInterface {
    /**
     *
     * @param port
     */
    public void init(int port);
    public void setDirection(String direction);
    public void setValue(int value);
    public int getValue();
    public void close();
    
}
