/*
GearStateDevice.cpp
 *
 *  Created on: 12.16.2014
 *      Author: Yoonsung
 */

#include "GearDevice.h"
#include "vrtaSampleDevices.h"

class Gear_Sensor: public vrtaSensor {
public:
	Gear_Sensor() :
			vrtaSensor((char*) "GearState_SensorDevice") {
	}
};

Gear_Sensor Gear_SensorDevice;

int readEngine_GearSensorDevice(void) {
	return (int) Gear_SensorDevice.GetValue();
}
