/*
   VALUES
*/

int airConditionerInput=0;
String fsrValue;
String celciusValue;
String sendingValue;
String divider=":";

char AIR_DEFAULT='0';
char AIR_COOL_L='1';
char AIR_COOL_M='2';
char AIR_COOL_H='3';
char AIR_HEAT_L='4';
char AIR_HEAT_M='5';
char AIR_HEAT_H='6';

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

String fsrResult(float value){

  if(value>300){
    return "1";
  }else{
    return "0";
  }
}

void airConditioner(char value){


//Motor (0~255)speed
    
    digitalWrite(ledDefault, HIGH);
    digitalWrite(ledHeatLOW, HIGH);
    digitalWrite(ledHeatMID, HIGH);    
    digitalWrite(ledHeatHIGH, HIGH);
    digitalWrite(ledCoolLOW, HIGH);
    digitalWrite(ledCoolMID, HIGH);    
    digitalWrite(ledCoolHIGH, HIGH);
    
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, LOW);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, LOW);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);

  if(value==AIR_DEFAULT){
       digitalWrite(ledDefault, HIGH);
    digitalWrite(ledHeatLOW, LOW);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, LOW);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);

  }else if(value==AIR_COOL_L){
       digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, HIGH);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, LOW);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);

  }else if(value==AIR_COOL_M){
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, HIGH);    
    digitalWrite(ledHeatMID, HIGH);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, LOW);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);

  }else if(value==AIR_COOL_H){
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, HIGH);    
    digitalWrite(ledHeatMID, HIGH);    
    digitalWrite(ledHeatHIGH, HIGH);
    digitalWrite(ledCoolLOW, LOW);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);
  }else if(value == AIR_HEAT_L){
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, LOW);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, HIGH);
    digitalWrite(ledCoolMID, LOW);    
    digitalWrite(ledCoolHIGH, LOW);
  }else if(value == AIR_HEAT_M){
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, LOW);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, HIGH);
    digitalWrite(ledCoolMID, HIGH);    
    digitalWrite(ledCoolHIGH, LOW);
  }else if(value == AIR_HEAT_H){
    digitalWrite(ledDefault, LOW);
    digitalWrite(ledHeatLOW, LOW);    
    digitalWrite(ledHeatMID, LOW);    
    digitalWrite(ledHeatHIGH, LOW);
    digitalWrite(ledCoolLOW, HIGH);
    digitalWrite(ledCoolMID, HIGH);    
    digitalWrite(ledCoolHIGH, HIGH);
  }
}

void loop(){ 
    val=analogRead(analPin);
    x=map(val, 0, 1023, 0, vcc);
    th=(((float)(vcc-x)*10.0)/(float)x)*1000.0;
    celcius=((log(4.0*th-3000.0)/(-0.024119329)+473)-32.0)/1.8;    
    t=(log(4.0*th-3000.0)/(-0.024119329)+473);
    tc=t-273.15;
//FSR
    fsrReading=analogRead(fsrAnalogPin);
  
//If there is no pressure, fsrReading will be 73.
// We would define that there is a enough pressure 
// if the value of fsrReading become more than 300. 

  celciusValue=String(celcius);
  fsrValue=fsrResult(fsrReading);

  sendingValue=celciusValue;
  sendingValue.concat(divider);
  sendingValue.concat(fsrValue);
  //"celcius:fsr"

  //send to java program
  Serial.print(fsrValue);
  
  //read from java program
  char temp;
  temp=Serial.read();
  //airConditionerInput=temp-'0';
  //Serial.print(airConditionerInput);
  airConditioner(airConditionerInput);

  delay(2000);
}


