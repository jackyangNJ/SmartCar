#include <Servo.h>

Servo servo; 
const int HallMsgType = 'H';
const int UltrawaveMsgType = 'U';
const int TrigPin = 5;
const int EchoPin = 3;
const int HallPin = 10;
const int ServoPin = 11;
int HallPinStateOld = 1;
int HallPinStateNew;
byte buffer[10];
int bufferIndex = 0;
unsigned long UltrawaveTime;
unsigned long currentTime;
void setup() {
    Serial.begin(115200);
	servo.attach(ServoPin);  // attaches the servo on pin 9 to the servo object 
    pinMode(HallPin, INPUT);
	pinMode(TrigPin, OUTPUT);
	pinMode(EchoPin, INPUT);
	UltrawaveTime = millis();
}

void loop() {
	while(Serial.available()){
		char data = Serial.read();
		buffer[bufferIndex] = data;
		if(data == '\n'){
			bufferIndex = 0;
			if(buffer[0] == 'R')
				servoProcess();
		}else{
			bufferIndex++;
		}
	}
	Hall();
	currentTime= millis();
	if(currentTime - UltrawaveTime > 1000){
		UltrawaveTime = currentTime;
		UltrawaveProcess();
	}
}
bool Hall(){
	HallPinStateNew = digitalRead(HallPin);
    if ((HallPinStateNew == 0) &&(HallPinStateOld == 1)) {
        //send Hall sensor message via serial
        Serial.write(HallMsgType);
        Serial.write('\n');
    }
    HallPinStateOld = HallPinStateNew;
}
//return cm
float UltrawaveProcess()
{
	float cm;
	digitalWrite(TrigPin, LOW); //低高低电平发一个短时间脉冲去TrigPin
	delayMicroseconds(2);
	digitalWrite(TrigPin, HIGH);
	delayMicroseconds(10);
	digitalWrite(TrigPin, LOW);
	cm = pulseIn(EchoPin, HIGH) / 58.0; //将回波时间换算成cm
	cm = (int(cm * 100.0)) / 100.0; //保留两位小数	
	Serial.write(UltrawaveMsgType);
	Serial.print(cm);
	Serial.write('\n');
}
void servoProcess()
{
    byte data = buffer[1];
    // if(data < 0)  rxData += 256;
    servo.write(data);
	delay(20);
}


