const int HallMsgType = 0;
const int MagneticMsgType = 1;
int HallPin = 12;
int HallPinStateOld;
int HallPinStateNew;

void setup() {
    Serial.begin(115200);
    pinMode(HallPin, INPUT);

}

void loop() {
    //test state of Hall sensor
    HallPinStateNew = digitalRead(HallPin);
    if ((HallPinStateNew == 1) &&(HallPinStateOld == 0)) {
        //send Hall sensor message via serial
        Serial.write(HallMsgType);
        Serial.write('\n');
    }
    HallPinStateOld = HallPinStateNew;
    
    //get state of magnetic sensor
}
