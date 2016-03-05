/*
 * OutFuelCutDevice.cpp
 *
 *  Created on: 14.16.2014
 *      Author: Yoonsung
 */

#include "OutFuelCutDevice.h"
#include "vrtaSampleDevices.h"

class OutFuelCutDevice: public vrtaActuator {
public:
	OutFuelCutDevice() :
			vrtaActuator((char*) "OutFuelCutDevice") {
	}
};

OutFuelCutDevice outFuelCutStateDevice;

void writeFuelCutDevice(int value) {
	outFuelCutStateDevice.SetValue(value);
}
