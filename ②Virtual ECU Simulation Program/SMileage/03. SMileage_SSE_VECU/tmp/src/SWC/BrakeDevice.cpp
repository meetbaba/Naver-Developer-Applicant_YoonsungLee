/*
BrakeStateDevice.cpp
 *
 *  Created on: 12.16.2014
 *      Author: Yoonsung
 */

#include "BrakeDevice.h"
#include "vrtaSampleDevices.h"

class Brake_Sensor: public vrtaSensor {
public:
	Brake_Sensor() :
			vrtaSensor((char*) "BrakeState_SensorDevice") {
	}
};

Brake_Sensor Brake_SensorDevice;

int readEngine_BrakeSensorDevice(void)  {
	return (int) Brake_SensorDevice.GetValue();
}
