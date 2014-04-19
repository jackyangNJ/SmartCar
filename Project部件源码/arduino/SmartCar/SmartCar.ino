const int HallMsgType = 0;
const int MagneticMsgType = 1;
int HallPin = 11;
int HallPinStateOld = 1;
int HallPinStateNew;

void setup() {
    Serial.begin(115200);
    pinMode(HallPin, INPUT);

}

void loop() {
    //test state of Hall sensor
    HallPinStateNew = digitalRead(HallPin);
    if ((HallPinStateNew == 0) &&(HallPinStateOld == 1)) {
        //send Hall sensor message via serial
        Serial.write(HallMsgType);
        Serial.write('\n');
    }
    HallPinStateOld = HallPinStateNew;
    
    //get state of magnetic sensor
}
