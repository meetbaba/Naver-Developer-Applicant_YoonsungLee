/*
AccelerateDevice.cpp
 *
 *  Created on: 12.16.2014
 *      Author: Yoonsung
 */

#include "AccelerateDevice.h"
#include "vrtaSampleDevices.h"

class Accelerate_Sensor: public vrtaSensor {
public:
	Accelerate_Sensor() :
			vrtaSensor((char*) "Accelerate_SensorDevice") {
	}
};

Accelerate_Sensor Accelerate_SensorDevice;

int readEngine_AccelerateSensorDevice(void) {
	return (int) Accelerate_SensorDevice.GetValue();
}
