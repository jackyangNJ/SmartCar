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
    private float map_x;
    private float map_y;
    
    public QRCodeData() {
        map_x = 0;
        map_y = 0;
    }
    
    /**
     * 获取地图坐标x和y
     */
    public float get_map_x() {
        return map_x;
    }
    
    public float get_map_y() {
        return map_y;
    }
    
    /**
     * 设置x和y的数据
     * @param x and y
     */
    public void set_map_x(float x) {
        map_x = x;
    }
    
    public void set_map_y(float y) {
        map_y = y;
    }
}
