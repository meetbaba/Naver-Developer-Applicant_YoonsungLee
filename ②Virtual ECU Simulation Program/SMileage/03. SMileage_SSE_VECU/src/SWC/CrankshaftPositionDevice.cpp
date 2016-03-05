/*
 * CrankshaftPositionDevice.cpp
 *
 *  Created on: 12.16.2013
 *      Author: Yoonsung
 */

#include "CrankshaftPositionDevice.h"
#include "vrtaSampleDevices.h"

class CrankshaftPosition_Sensor: public vrtaSensor {
public:
	CrankshaftPosition_Sensor() :
			vrtaSensor((char*) "CrankshaftPosition_SensorDevice") {
	}
};

CrankshaftPosition_Sensor CrankshaftPosition_SensorDevice;

float readEngine_CrankshaftPositionSensorDevice(void) {
	return (float) CrankshaftPosition_SensorDevice.GetValue();
}
