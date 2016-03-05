package com.app.smartkidshoes;

public class PreferenceUtil extends BasePreferenceUtil {
	private static final String LAST_REQUEST_DEVICE_ADDRESS = "LAST_REQUEST_DEVICE_ADDRESS";
	private static final String LAST_CONNECT_DEVICE_ADDRESS = "LAST_CONNECT_DEVICE_ADDRESS";
	
	public static void putLastRequestDeviceAddress(String address) {
		put(LAST_REQUEST_DEVICE_ADDRESS, address);
	}
	
	public static String lastRequestDeviceAddress() {
		return get(LAST_REQUEST_DEVICE_ADDRESS);
	}
	
	public static void putLastConnectedDeviceAddress(String address) {
		put(LAST_CONNECT_DEVICE_ADDRESS, address);
	}
	
	public static String lastConnectedDeviceAddress() {
		return get(LAST_CONNECT_DEVICE_ADDRESS);
	}
}
