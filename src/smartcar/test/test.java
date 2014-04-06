package smartcar.test;

import java.util.HashMap;
import java.util.Map;

/**
 * just for test
 * @author jack
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<Integer,Integer> map=new HashMap<>();
        map.put(3, 4);
        System.err.println(map.get(3));
        byte[] llBs=new byte[10];
//        llBs="sdf".getBytes();
        Object object= llBs;
        byte[]a=(byte[])object;
        System.err.println(a.length);
        
    }
    
}
