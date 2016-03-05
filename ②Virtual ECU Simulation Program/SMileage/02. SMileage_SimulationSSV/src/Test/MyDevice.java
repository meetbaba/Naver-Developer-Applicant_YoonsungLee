/*
 * MyDevice.java
 *
 * Copyright (c) 2012 Etas GmbH, Stuttgart.
 *              All rights reserved.
 *
 * <description>  The interface which needs to be implemented by each of the Device class to perform write, read and flip events to VECU
 *
 */
package Test;

import com.etas.vrta.monitor.core.interfaces.VDevice;

/**
 * Interface to perform write, read and flip events for a device to VECU
 */
public interface MyDevice extends VDevice {

	// code specific to the vecu
	
	// Writes an event to channel in VECU
	void sendWriteChannel(Object param1, Object param2);

	// Sends the flip channel events
	void sendFlipChannel(Object param1);

	// Reads an event from a channel
	Object readReadChannel(Object param1);

}
