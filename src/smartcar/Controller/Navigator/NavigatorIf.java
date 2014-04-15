package smartcar.Controller.Navigator;

/**
 * Interface for class Navigator
 * @author jackl
 */
public interface NavigatorIf {
    public float getv_x();
    public void setv_x(float v_x);
    public float getv_y();
    public void setv_y(float v_y);
    public float geta_x();
    public void seta_x(float a_x);
    public float geta_y();
    public void seta_y(float a_y);
    public float getx();
    public void setx(float x);
    public float gety();
    public void sety(float y);
    public float getangular();
    public void setangular(float angular);
    public float getdistance();
    public void setdistance(float distance);
    public float getangular_velocity();
    public void setangular_velocity(float w);
}
