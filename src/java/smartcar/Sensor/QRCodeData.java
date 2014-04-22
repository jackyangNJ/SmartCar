/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartcar.Sensor;

/**
 *
 * @author Kedar
 */
public class QRCodeData {
    private String position;
    
    public QRCodeData() {
        position = null;
    }
    
    /**
     * 获取地图坐标string
	 * @return 
     */
    public String get_position() {
        return position;
    }
    
    /**
     * 设置position的数据
	 * @param s
     */
    public void set_position(String s) {
        position = s;
    }
}
