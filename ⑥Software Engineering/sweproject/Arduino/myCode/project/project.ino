/*
   Temperature Alarm
*/
float sinVal;            
int toneVal;
unsigned long tepTimer ;    

/*
  Temperature variables
*/
int analPin=0;
int val=0;
long x=0, vcc=4840;
float th=0, celcius=0;
float t=0;
float tc=0;


/*
  FSR variables
*/
int fsrAnalogPin=1;
int fsrReading;


/*
  LED variables
*/
int ledHeatLOW=3;
int ledHeatMID=2;
int ledHeatHIGH=8;
int ledDefault=4;
int ledCoolLOW=5;
int ledCoolMID=6;
int ledCoolHIGH=7;


/**
 *  Motor Variables
 */

int motorPin=10;

void setup(){ 
    pinMode(ledDefault, OUTPUT);
    pinMode(ledHeatLOW, OUTPUT);
    pinMode(ledHeatMID, OUTPUT);
    pinMode(ledHeatHIGH, OUTPUT);
    pinMode(ledCoolLOW, OUTPUT);
    pinMode(ledCoolMID, OUTPUT);
    pinMode(ledCoolHIGH, OUTPUT);
    
    pinMode(motorPin, OUTPUT);

    Serial.begin(9600);        
}

void loop(){ 
    val=analogRead(analPin);
    x=map(val, 0, 1023, 0, vcc);
    th=(((float)(vcc-x)*10.0)/(float)x)*1000.0;
    celcius=((log(4.0*th-3000.0)/(-0.024119329)+473)-32.0)/1.8;    
    t=(log(4.0*th-3000.0)/(-0.024119329)+473);
    tc=t-273.15;

    Serial.println(celcius);

//FSR
    fsrReading=analogRead(fsrAnalogPin);
    Serial.print("Anlaog reading=");
    Serial.println(fsrReading);
//If there is no pressure, fsrReading will be 73.
// We would define that there is a enough pressure 
// if the value of fsrReading become more than 300. 


//Motor (0~255)speed
    
    digitalWrite(ledDefault, HIGH);
    delay(1000);
    digitalWrite(ledHeatLOW, HIGH);
    delay(1000);
    digitalWrite(ledHeatMID, HIGH);    
    delay(1000);
    digitalWrite(ledHeatHIGH, HIGH);
    delay(1000);
    digitalWrite(ledCoolLOW, HIGH);
    delay(1000);
    digitalWrite(ledCoolMID, HIGH);    
    delay(1000);
    digitalWrite(ledCoolHIGH, HIGH);
    delay(1000);
    
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, LOW);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, LOW);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);

    delay(2000);
}

