/*
 * MyDeviceImpl.java
 *
 * Copyright (c) 2012 Etas GmbH, Stuttgart.
 *              All rights reserved.
 *
 * <description>  Sample Device class to perform write, read and flip events for a device 
 *
 */
package Test.impl;

import com.etas.vrta.comms.EventHook;
import Test.MyDevice;
import com.etas.vrta.monitor.core.interfaces.VECU;
import com.etas.vrta.monitor.core.devices.impl.VDeviceImpl;


public class MyDeviceImpl extends VDeviceImpl implements MyDevice {

	/**
	 * Sends an event to VECU
	 * 
	 * @param name
	 *            name of event
	 * @param params
	 *            parameters for the event
	 */
	@Override
	public void sendAction(String name, Object... params) {
		super.sendAction(name, params);
	}

	/**
	 * Read an event from the VECU
	 * 
	 * @param name
	 *            name of the event
	 * @param params
	 *            parameters for the event
	 * @return value of the event
	 */
	@Override
	public Object readEvent(String name, Object... params) {
		return super.readEvent(name, params);
	}

	/**
	 * Init hooks for an event
	 * 
	 * @param name
	 *            name of the event
	 * @param hook
	 *            hook event
	 */
	@Override
	public void initHooks(String name, EventHook hook) {
		super.initHooks(name, hook);
	}

	/**
	 * Name of the Device for which the events need to be performed
	 * 
	 * @return device name
	 */
	@Override
	public String getName() {
		return "Digital_I/O";
	}

	/**
	 * Gets the VECU instance
	 * 
	 * @return the VECU instance
	 */
	@Override
	public VECU getVECU() {
		return MyVECUImpl.instance;
	}

	// start VECU specific code
	/**
	 * Performs Write event to a channel
	 * 
	 * @param param1
	 *            channel
	 * @param param2
	 *            value of the event
	 */
	@Override
	public void sendWriteChannel(Object param1, Object param2) {
		sendAction("WriteChannel", new Object[] { param1, param2 });
	}

	/**
	 * Performs flip event
	 * 
	 * @param param1
	 *            channel
	 */
	@Override
	public void sendFlipChannel(Object param1) {
		sendAction("FlipChannel", new Object[] { param1 });
	}

	/**
	 * Reads event from an channel
	 * 
	 * @param param1
	 *            channel from which the value is to be read.
	 * @return the value in the channel
	 */
	@Override
	public Object readReadChannel(Object param1) {
		return readEvent("ReadChannel", param1);
	}
	// end VECU specific code

}
