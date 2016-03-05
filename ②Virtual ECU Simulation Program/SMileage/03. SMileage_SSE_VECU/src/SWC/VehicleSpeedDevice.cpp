/*
 * VehicleSpeedDevice.cpp
 *
 *  Created on: 12.16.2014
 *      Author: Yoonsung
 */

#include "VehicleSpeedDevice.h"
#include "vrtaSampleDevices.h"

class VehicleSpeed_Sensor: public vrtaSensor {
public:
	VehicleSpeed_Sensor() :
			vrtaSensor((char*) "VehicleSpeed_SensorDevice") {
	}
};

VehicleSpeed_Sensor VehicleSpeed_SensorDevice;

float readEngine_VehicleSpeedSensorDevice(void) {
	return (float) VehicleSpeed_SensorDevice.GetValue();
}
