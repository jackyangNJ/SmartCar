package smartcar.Sensor;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import smartcar.Ulwave;

/**
 *
 * @author cshuo
 */
public class SensorUltrasonic implements SensorUltrasonicIf{
    private ArrayList<SensorListener> SensorListeners;
    private SensorUltrasonicData UltrasonicData;   
    public static final String path = "/dev/";
    
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run(){            
        }
    };
    
    /**
     * 触发超声波传感器发出探测脉冲，
     * 一直等到是yes才能读数据，？？？？一直不是yes,陷于循环？？？(是否限时)
     */
    public void trigger(){
        char[] bufRead = new char[4];
        
        try{            //向trigger文件中写1
            FileWriter fw = new FileWriter(path+"trigger");
            fw.write("1");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Ulwave.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{            //直到从trigger文件读到yes，数据才准备好
            FileReader fr = new FileReader(path+"trigger");            
            fr.read(bufRead);            
            while(!new String(bufRead,0,3).equals("yes")){
               fr.read(bufRead);
            }
            fr.close();        
        }catch(IOException ex){
            Logger.getLogger(Ulwave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * 从distance文件中获取测量数据
     */
    public void getDistance(){
        char[] disRead = new char[20];
        long dis_cnt1 = 0,dis_cnt2 = 0,dis_cnt3 = 0;                            //time ulwave giving back
        
        trigger();    //触发之；
        
        try {            //读取三个distance文件的数值
            FileReader fr_dis1 = new FileReader(path+"distance1");
            FileReader fr_dis2 = new FileReader(path+"distance2");
            FileReader fr_dis3 = new FileReader(path+"distance3");
            fr_dis1.read(disRead);
            dis_cnt1 = Integer.parseInt(new String(disRead).trim());
            fr_dis2.read(disRead);
            dis_cnt2 = Integer.parseInt(new String(disRead).trim());
            fr_dis3.read(disRead);
            dis_cnt3 = Integer.parseInt(new String(disRead).trim());
            fr_dis1.close();
            fr_dis1.close();
            fr_dis1.close();        
        } catch (IOException ex) {
            Logger.getLogger(Ulwave.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //单位(m)
        UltrasonicData.setDistance1(dis_cnt1*170/1000000000);
        UltrasonicData.setDistance2(dis_cnt2*170/1000000000);
        UltrasonicData.setDistance3(dis_cnt3*170/1000000000);
    }
       
    
    /**
     * trigger the SensorEvent
     * @param e 
     */
    private void fireSensorEventProcess(SensorEvent e){
        ArrayList list;
        synchronized(this){
            if(SensorListeners == null){
                return;
            }
            list = (ArrayList) SensorListeners.clone();
        }
        for(int i=0;i<list.size();i++){
            SensorListener listener = (SensorListener)list.get(i);
            listener.SensorEventProcess(e);
        }
    }
    
    
    /**
     * add SensorListener
     * @param listener 
     */
    @Override
    public void addSenserListener(SensorListener listener) {
        if(SensorListeners==null){
            SensorListeners = new ArrayList<>(2);            
        }
        if(!SensorListeners.contains(listener)){
            SensorListeners.add(listener);
        }
    }

    
    /**
     * remove SensorListener
     * @param listener 
     */
    @Override
    public void removeSenserListener(SensorListener listener) {
        if((SensorListeners != null) && SensorListeners.contains(listener)){
            SensorListeners.remove(listener);
        }
    }
    
    
    /**
     * 获取Ultrosonic测得三个距离数据
     * @return 
     */
    @Override
    public SensorUltrasonicData getData() {
        SensorUltrasonicData sensorUltrasonicData = new SensorUltrasonicData();
        sensorUltrasonicData.setDistance1(UltrasonicData.getDistance1());
        sensorUltrasonicData.setDistance2(UltrasonicData.getDistance2());
        sensorUltrasonicData.setDistance3(UltrasonicData.getDistance3());
        //传送数据之后，将储存的数据置为最大
        UltrasonicData.setDistance1(3.0f);
        UltrasonicData.setDistance1(3.0f);
        UltrasonicData.setDistance1(3.0f);
        
        return sensorUltrasonicData;
    }
    
}
