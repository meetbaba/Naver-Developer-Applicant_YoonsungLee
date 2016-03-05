/*
GyroDevice.cpp
 *
 *  Created on: 12.16.2014
 *      Author: Yoonsung
 */

#include "GyroDevice.h"
#include "vrtaSampleDevices.h"

class Gyro_Sensor: public vrtaSensor {
public:
	Gyro_Sensor() :
			vrtaSensor((char*) "Gyro_SensorDevice") {
	}
};

Gyro_Sensor Gyro_SensorDevice;

float readEngine_GyroSensorDevice(void) {
	return (float) Gyro_SensorDevice.GetValue();
}
