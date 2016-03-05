/**
 * MyVECUImpl.java
 *
 * Copyright (c) 2012 Etas GmbH, Stuttgart.
 *              All rights reserved.
 *
 * <description>  Gives an instance of the VECU running in the provided target
 *
 */
package Test.impl;

import com.etas.vrta.monitor.core.interfaces.VECU;

/**
 * Provides an instance of the VECU
 */
public class MyVECUImpl implements VECU{

	public static VECU instance = new MyVECUImpl();
	
	private MyVECUImpl(){
		
	}
	
	/**
	 * Gives the name of the VECU
	 * @return name of the VECU
	 */
	@Override
	public String getName() {
		return "VECU_BasicRefSys_AR4";
	}
	
	/**
	 * Gives the target in which the VECU is running
	 * @return the target ipAddress 
	 */
	@Override
	public String getTarget() {
		return "192.168.40.14";
	}

}
