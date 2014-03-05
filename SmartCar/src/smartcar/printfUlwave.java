package smartcar;
//import java.lang.*;

public class printfUlwave {
	public static void main(String[] args) throws InterruptedException{
		Ulwave ulwave = new Ulwave(227,228,229,230);
		while(true){
		ulwave.trigger();
		Thread.sleep(1000);
		double distance1 = ulwave.getDistance1();
		System.out.println((int)distance1);
		double distance2 = ulwave.getDistance2();
		System.out.println((int)distance2);
		double distance3 = ulwave.getDistance3();
		System.out.println((int)distance3);
		}
	}
}
