package smartcar;
public interface LedInterface {
    /*
     * flash a led
     */
    public void blink(int index,int usec);
    /*
     * lighten a led
     */
    public void light(int index);
    /*
     * darken a led
     */
    public void dark(int index);
}
