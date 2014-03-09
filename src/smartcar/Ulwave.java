
package smartcar;

/**
 *ultrosonic module
 * @author cshuo
 */
public class Ulwave implements UlwaveInterface {

    private Gpio trigger_port;
    private Gpio echo1_port;
    private Gpio echo2_port;
    private Gpio echo3_port;

    private double distance1;
    private double distance2;
    private double distance3;

    public Ulwave(int port1, int port2, int port3, int port4) {
        trigger_port = new Gpio(port1);
        trigger_port.setDirection("out");
        echo1_port = new Gpio(port2);
        echo2_port = new Gpio(port3);
        echo3_port = new Gpio(port4);
    }

    @Override
    public void trigger() {
        /*10us high level to ultrowave*/
        long starttime = System.nanoTime();
        long endtime = 0;
        while (endtime < starttime + 10000) {
            trigger_port.setValue(0);
            endtime = System.nanoTime();
        }
        trigger_port.setValue(1);

        /*calculate the distance*/
        calDistance(echo1_port, echo2_port, echo3_port);

    }

    @Override
    public double getDistance1() {
        return distance1;
    }

    @Override
    public double getDistance2() {
        return distance2;
    }

    @Override
    public double getDistance3() {
        return distance3;
    }

    public void calDistance(Gpio echo1, Gpio echo2, Gpio echo3) {
        long start_time1 = 0;
        long start_time2 = 0;
        long start_time3 = 0;
        long end_time1 = 0;
        long end_time2 = 0;
        long end_time3 = 0;
        while (true) {
            int value1 = echo1.getValue();
            int value2 = echo2.getValue();
            int value3 = echo3.getValue();
            if (value1 == 1 && start_time1 == 0) {
                start_time1 = System.nanoTime();
            }
            if (value2 == 1 && start_time2 == 0) {
                start_time2 = System.nanoTime();
            }
            if (value3 == 1 && start_time3 == 0) {
                start_time3 = System.nanoTime();
            }
            if (value1 == 0 && start_time1 != 0) {
                end_time1 = System.nanoTime();
            }
            if (value2 == 0 && start_time2 != 0) {
                end_time2 = System.nanoTime();
            }
            if (value3 == 0 && start_time3 != 0) {
                end_time3 = System.nanoTime();
            }
            if (end_time1 != 0 && end_time2 != 0 && end_time3 != 0) {
                break;
            }
        }

        this.distance1 = 170 * (end_time1 - start_time1) / 10000000;
        this.distance2 = 170 * (end_time2 - start_time2) / 10000000;
        this.distance3 = 170 * (end_time3 - start_time3) / 10000000;

    }
}
