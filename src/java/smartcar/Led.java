package smartcar;

public class Led implements LedInterface{
    Gpio ledGpio[]=new Gpio[8];
    public Led(){
        for (int i = 0; i < 8; i++) {
            ledGpio[i].init(i);
        }
    }

    @Override
    public void blink(int index, int usec) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void light(int index) {
        
    }

    @Override
    public void dark(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[]args){
        Led led=new Led();
        
    }
}
