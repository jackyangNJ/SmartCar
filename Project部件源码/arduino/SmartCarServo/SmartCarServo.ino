#include <Servo.h>

Servo myservo; 
void setup() 
{ 
  myservo.attach(3);  // attaches the servo on pin 9 to the servo object 
  Serial.begin(115200);
} 
void loop() 
{ 
  while(Serial.available()){
    char rxData;
    rxData = Serial.read();
    if(rxData == 'R'){
      Serial.write(rxData);
      
      rxData = Serial.read();
      Serial.write(rxData);

          myservo.write(rxData);
    }
  } 
}
