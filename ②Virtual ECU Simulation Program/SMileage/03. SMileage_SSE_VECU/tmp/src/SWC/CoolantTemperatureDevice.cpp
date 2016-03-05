/*
CoolantTemperatureDevice.cpp
 *
 *  Created on: 12.16.2014
 *      Author: Yoonsung
 */

#include "CoolantTemperatureDevice.h"
#include "vrtaSampleDevices.h"

class CoolantTemperature_Sensor: public vrtaSensor {
public:
	CoolantTemperature_Sensor() :
			vrtaSensor((char*) "CoolantTemperature_SensorDevice") {
	}
};

CoolantTemperature_Sensor CoolantTemperature_SensorDevice;

float readEngine_CoolantTemperatureDevice(void) {
	return (float) CoolantTemperature_SensorDevice.GetValue();
}
