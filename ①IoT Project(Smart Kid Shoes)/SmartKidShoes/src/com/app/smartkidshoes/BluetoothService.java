package com.app.smartkidshoes;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;

public class BluetoothService extends Service {
	// Unique UUID for this application
	private static final UUID UUID_INSECURE = UUID.fromString("0000111f-0000-1000-8000-00805f9b34fb");
	
	private int mState;
	
	// Constants that indicate the current connection state
	public static final int STATE_NONE = 0;			// We're doing nothing
	public static final int STATE_LISTEN = 1;		// Now listening for incoming connections
	public static final int STATE_CONNECTING = 2;	// Now initiating an outgoing connection
	public static final int STATE_CONNECTED = 3;	// Now connected to a remote device
	
	// Data member
	private final BluetoothAdapter mAdapter;
	private final Handler mHandler;
	private Context mContext;
	private ConnectThread mConnectThread;
	private ConnectedThread mConnectedThread;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public BluetoothService(Context context) {
		mContext = context;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mHandler = null;
		mState = STATE_NONE;
	}
	
	public BluetoothService(Context context, Handler handler) {
		mContext = context;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mHandler = handler;
		mState = STATE_NONE;
		// Create New Unique UUID
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

		Method getUuidsMethod = null;
		try {
			getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		ParcelUuid[] uuids = null;
		try {
			uuids = (ParcelUuid[]) getUuidsMethod.invoke(adapter, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		for (ParcelUuid uuid: uuids) {
		    Log.e("Debug", "UUID: " + uuid.getUuid().toString());
		}
	}
	
	private synchronized void setState(int state) {
		mState = state;
		
		// Give the new state to the Handler so the UI Activity can update
		//mHandler.obtainMessage(BluetoothActivity.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
	}
	
	// Return the current connection state
	public synchronized int getState() {
		return mState;
	}
	
	public void reConnect(String address) {
		BluetoothDevice device = mAdapter.getRemoteDevice(address);
		this.connect(device);
	}
	
	public synchronized void connect(BluetoothDevice device) {
		// Cancel any thread attempting to make a connection
		if(mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		
		// Cancel any thread currently running a connection
		if(mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		
		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device);
		mConnectThread.start();
		setState(STATE_CONNECTING);
	}
	
	public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
		// Cancel the thread that completed the connection
		if(mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		
		// Cancel any thread currently running a connection
		if(mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		
		PreferenceUtil.putLastRequestDeviceAddress(socket.getRemoteDevice().getAddress());
		mAdapter.cancelDiscovery();
		
		// Start the thread to manage the connection and perform transmissions
		mConnectedThread = new ConnectedThread(socket);
		mConnectedThread.start();

		if(mHandler != null) {
			// Send the name of the connected device back to the UI Activity
			Message msg = mHandler.obtainMessage(BluetoothActivity.MESSAGE_DEVICE_NAME);
			Bundle bundle = new Bundle();
			bundle.putString(BluetoothActivity.DEVICE_NAME, device.getName());
			msg.setData(bundle);
			mHandler.sendMessage(msg);
		}
		setState(STATE_CONNECTED);
	}
	
	private void connectionFailed() {
		if(mHandler != null) {
			// Send a failure message back to the Activity
			Message msg = mHandler.obtainMessage(BluetoothActivity.MESSAGE_TOAST);
			Bundle bundle = new Bundle();
			bundle.putString(BluetoothActivity.TOAST, "Unable to connect device");
			msg.setData(bundle);
		}
	}
	
	private class ConnectThread extends Thread {
		private final BluetoothSocket mSocket;
		private final BluetoothDevice mDevice;
		
		public ConnectThread(BluetoothDevice device) {
			mDevice = device;
			BluetoothSocket temp = null;
			
			// Get a BluetoothSocket for a connection with the given BluetoothDevice
			try {
				temp = device.createRfcommSocketToServiceRecord(UUID_INSECURE);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			mSocket = temp;
		}
		
		public void run() {
			setName("ConnectThread");
			
			// Always cancel discovery because it will slow down a connection
			mAdapter.cancelDiscovery();
			// Make a connection to the BluetoothSocket
			try {	// This is a blocking call and will only return on a successful connection or an exception
				mSocket.connect();
			} catch(Exception e) {
				// Close the socket
				try {
					if(mSocket.isConnected()) {
						mSocket.close();
					}
				} catch(Exception ee) {
					ee.printStackTrace();
				}
				connectionFailed();
				return ;
			}
			
			// Reset the ConnectThread because we are done
			synchronized(BluetoothService.this) {
				mConnectThread = null;
			}
			
			// Start the connected thread
			connected(mSocket, mDevice);
		}
		
		public void cancel() {
			try {
				mSocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class ConnectedThread extends Thread {
		private final BluetoothSocket mSocket;
		private final InputStream mInputStream;
		
		public ConnectedThread(BluetoothSocket socket) {
			mSocket = socket;
			InputStream tempInputStream = null;
			
			// Get the BluetoothSocket input steream
			try {
				tempInputStream = socket.getInputStream();
			} catch(IOException e) {
				e.printStackTrace();
			}
			mInputStream = tempInputStream;
		}
		
		public void run() {
			byte[] buffer = new byte[1024];
			int bytes;
			
			// Keep listening to the InputStream while connected
			while(true) {
				try{	
					// Read from the InputStream
					bytes = mInputStream.read(buffer);
					
					Intent intent = new Intent("com.app.testbluetoothspp.receive");
					intent.putExtra("signal", bytesToString(buffer, bytes));
					mContext.sendBroadcast(intent);
				} catch(Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}
		
		private String bytesToString(byte[] bytes, int count) {
			ArrayList<String> result = new ArrayList<String>();
			for(int i=0;i<count;i++) {
				String myInt = Integer.toHexString((int)(bytes[i] & 0xFF));
				result.add("0x" + myInt);
			}
			return TextUtils.join("-", result);
		}
		
		public void cancel() {
			try {
				mSocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
