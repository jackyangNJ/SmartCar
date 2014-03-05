/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcar;

import java.util.Scanner;

/**
 *
 * @author cshuo
 */
public class Test {

    public static void main(String[] args) {
        //System.out.print("0 to stop\n1 to clockwise\n2 to countclockwise\n3 to left\n4 to right\n5 to 7000 forward");
        smart_car_move car = new smart_car_move();
        
        System.out.println("input speed and angle");
        Scanner input = new Scanner(System.in);
        int speed = input.nextInt();
        int angle = input.nextInt();
        car.smart_car_set(speed, angle);
        
        car.set_back();
        
        System.out.println("0 to stop");
        int x = input.nextInt();        
        if(x==0){
            car.smart_car_set(0, 0);
        }
    }
}
