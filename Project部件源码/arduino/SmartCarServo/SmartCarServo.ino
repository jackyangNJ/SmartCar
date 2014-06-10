#include <Servo.h>

Servo servo; 
void setup() 
{ 
  servo.attach(3);  // attaches the servo on pin 9 to the servo object 
  Serial.begin(115200);

} 
void loop() 
{ 
  while(Serial.available() >= 2){
    int rxData;
    rxData = Serial.read();
    if(rxData == 'R'){
      Serial.print('R');
      rxData = Serial.read();
      if(rxData< 0)  rxData += 256;
      servo.write(rxData);
     
    }
  }
delay(10);
}
