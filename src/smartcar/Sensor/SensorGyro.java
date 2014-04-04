package smartcar.Sensor;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import static com.googlecode.javacv.cpp.opencv_core.cvRealScalar;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_core.cvSetIdentity;
import com.googlecode.javacv.cpp.opencv_video;
import com.googlecode.javacv.cpp.opencv_video.CvKalman;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import smartcar.Event.SensorEvent;
import smartcar.Event.SensorListener;
import spiLib.SPIFunc;

/**
 *
 * @author cs
 */
public class SensorGyro implements SensorGyroIf{
    private ArrayList<SensorListener> SensorListeners;
    private SPIFunc spifunc;
    private SensorGyroData gyroData;
    private  final float UNIT = 8.75f;
    private  final byte OUT_Z_L_addr = 0x2C;
    private  final byte OUT_Z_H_addr = 0x2D;
    private  final byte L3G4200D_CTRL_REG1 = 0x20; 
    private  final String routePath = "/dev/spidev32765.0";
    /*every 10ms read a number,that's to say 100Hz*/
    private  final  int readFrequency = 10;
    private  CvMat z_k ;
    private  CvMat y_k ;//预测值
    private  CvKalman kalman;
    //private  SensorGyroData data;
    public static  final  double frequency = 0.01;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            /*读取使能*/
            defaultEnable();  
            read_HoriAngleSpeed();  
            fireSensorEventProcess(new SensorEvent(this, SensorEvent.SENSOR_GYRO_TYPE, getSensorGyroData()));
        }
    };
            
    public SensorGyro(){
        gyroData = new SensorGyroData();
        gyroData.setHori_angle((float)0);
        spifunc = new SPIFunc(routePath);        
        timer.scheduleAtFixedRate(task,0,readFrequency);
        //初始化kalman
 	kalman = com.googlecode.javacv.cpp.opencv_video.cvCreateKalman(2, 1, 0) ;//创建kalman
             
		z_k = new CvMat();
		z_k.create(1, 1, com.googlecode.javacv.cpp.opencv_core.CV_32FC1);
		z_k.zero();//测量值
               // z_k.
		final float F[][] = {{1,(float)frequency},{0,1}};//时间会变化

                System.arraycopy(kalman.transition_matrix().data_fl(), 0, F, 0,4);
		//赋值
                cvSet2D(kalman.measurement_matrix(),0,0,CvScalar.ZERO);
                cvSet2D(kalman.measurement_matrix(),1,0,CvScalar.ONE);
                kalman.measurement_matrix().put(0, 0, 0);
                kalman.measurement_matrix().put(1, 0, 1);
                cvSetIdentity(kalman.process_noise_cov(),cvRealScalar(1e-5));
                cvSetIdentity(kalman.measurement_noise_cov(),cvRealScalar(1e-5));
                cvSetIdentity(kalman.error_cov_post(),cvRealScalar(1));
                //初始化
    }
    
    /**
     * 读取水平角速度，单位 度/s
     */
    public void read_HoriAngleSpeed(){
        byte Z_L = gyr_read(OUT_Z_L_addr);
        byte Z_H = gyr_read(OUT_Z_H_addr);
        int z = Z_H<<8 | Z_L;
        gyroData.setHori_angleSpeed((float)z*UNIT/1000);
    }
    
    /**
     * 向控制写默认值
     */
    public void defaultEnable(){
        byte[] data = new byte[2];
        data[0] = L3G4200D_CTRL_REG1;
        data[1] = 0x0F;    
        Gyr_write(data);
    }
    
    /**
     * 读取指定地址寄存器的值
     * @param addr
     * @return 
     */
    public byte gyr_read(byte addr){
        byte[] read_data = new byte[2];
        read_data[0] = (byte)((addr|(3<<6))-(1<<6));       //bit0置为1,bit1置为0
        spifunc.RWBytes(read_data,2);
        return read_data[1];
    }
    
    /**
     * 向指定地址寄存器写值
     * @param data 
     */
    public void Gyr_write(byte[] data){
        byte[] write_data = new byte[2];
        write_data[0] = (byte) ((data[0]|(3<<6))-(3<<6));//   bit0，1置为0
        write_data[1] = data[1];
        spifunc.RWBytes(write_data,2);
    }
    
    /**
     * 根据寄存器的值判断设备是否正确
     * @return 
     */
    public boolean id_true(){
        byte ID_Addr = 0x0f;       
        return gyr_read(ID_Addr)==(byte)0xd3;
    }
    
    /**
     *触发监听事件
     * @param e 
     */
    private void fireSensorEventProcess(SensorEvent e){
        ArrayList list;
        synchronized(this){
            if(SensorListeners == null){
                return;
            }
            list = (ArrayList)SensorListeners.clone();
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
        if(SensorListeners == null){
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
     * 获取处理后的数据
     * @return 
     */
    @Override
    public SensorGyroData getSensorGyroData() { 
        //this.gyroData = this.kalmanData(gyroData);
        return this.kalmanData(gyroData);
    }
    
    public SensorGyroData kalmanData(SensorGyroData GyroData){
                SensorGyroData speed = new SensorGyroData();
            	y_k = opencv_video.cvKalmanPredict( kalman, null );//获取值
                z_k.put(GyroData.getHori_angleSpeed());    
                opencv_video.cvKalmanCorrect(kalman, z_k);
                return speed;
    }

    @Override
    public SensorGyroData getRawSensorGyroData() {
        return this.gyroData;
    }
    
}