package com.app.smartkidshoes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
	public static int stopFlag;
	
	private Button btnFindDevice, btnMap, btnStop;
	private SharedPreferences sp;
	private String lastActivity;

	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	
	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_DEVICE_NAME = 2;
	public static final int MESSAGE_TOAST = 3;
	   
	private static final int REQUEST_CONNECT_DEVICE = 0;
	private static final int REQUEST_ENABLE_BT = 1;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the service
	public static BluetoothService mBTService;
	private String setGPSvalue,freq;
	private Handler mGPSHandler;
	private ProgressDialog mProgressDialog;
	private UDPclient udpThread = new UDPclient();
	private Thread thread;
	
	public static Activity activity;
	public static LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		activity = BluetoothActivity.this;
		stopFlag = 0;
		layout = (LinearLayout)findViewById(R.id.layout);
		ContextUtil.CONTEXT = getApplicationContext();
		mBTService = null;
		mBTService = new BluetoothService(getApplicationContext(), mHandler);
		
		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter == null) {
			Toast.makeText(getApplicationContext(), "Bluetooth is not available. Please check again.", Toast.LENGTH_LONG).show();
		}
		
		btnFindDevice = (Button)findViewById(R.id.btnFindDevice);
		btnFindDevice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent serverIntent = null;
				serverIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			}
		});

		if(PreferenceUtil.lastConnectedDeviceAddress() == null) {
			btnFindDevice.setEnabled(true);
		} else {
			btnFindDevice.setEnabled(false);
			//BluetoothService BTService = new BluetoothService(getApplicationContext());
			mBTService.reConnect(PreferenceUtil.lastConnectedDeviceAddress());
		}

		sp = getSharedPreferences("LAST_ACTIVITY", 0);
		lastActivity = sp.getString("LAST_ACTIVITY", "INFORM_ACTIVITY");
		btnMap = (Button)findViewById(R.id.btnMap);
		if(lastActivity.equals("INFORM_ACTIVITY")) {
			btnMap.setEnabled(false);
		} else if(lastActivity.equals("BLUETOOTH_ACTIVITY")) {
			btnMap.setEnabled(true);
		}
		btnMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBTService.stopSelf();
				setGPSvalue="0/0";
				thread = new Thread(udpThread);
				mGPSHandler = new Handler();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mProgressDialog = ProgressDialog.show(
								BluetoothActivity.this, "", "잠시만 기다려 주세요.",true);
						mGPSHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								try {
									if (mProgressDialog != null	&& mProgressDialog.isShowing()) {
										mProgressDialog.dismiss();
										if(setGPSvalue.equals("0/0")){
											Toast.makeText(getApplicationContext(), "서버로부터 값을 받지 못했습니다.", Toast.LENGTH_SHORT).show();
											Log.e("여기다에러",setGPSvalue);
											System.out.println("쓰레드 죽이냐");
											thread.destroy();
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, 5000);
					}
				});
				thread.start(); 
			}
		});
		
		btnStop = (Button)findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBTService.stopSelf();
				ReConnectService.instance(ContextUtil.CONTEXT).stopReconnect();
				stopFlag = 1;
				finish();
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// If Bluetooth is not on, request that it e enabled
		if(!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}
	}
	
	private void connectDevice(Intent data) {
		// Get the device MAC address
		String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		
		// Get the BluetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		
		// Attempt to connect to the device
		mBTService.connect(device);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// When DeviceListActivity returns with a device to connect
		if(requestCode == REQUEST_CONNECT_DEVICE) {
			if(resultCode == RESULT_OK) {
				connectDevice(data);
			}
		} else if(requestCode == REQUEST_ENABLE_BT) {
			if(resultCode == RESULT_OK) {
				Toast.makeText(this, "Bluetooth was enabled.", Toast.LENGTH_LONG).show();
			}
		}
	}

	// The handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
					break;
				case BluetoothService.STATE_CONNECTING:
					Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG).show();
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
					Toast.makeText(getApplicationContext(), "Device was not connected", Toast.LENGTH_LONG).show();
					break;
				}
				break;
			case MESSAGE_DEVICE_NAME:
				// Save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	public Handler getHandler() {
		return this.mHandler;
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences("LAST_ACTIVITY", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("LAST_ACTIVITY", "INFORM_ACTIVITY");
		editor.commit();
	}
	private class UDPclient implements Runnable {

		private static final String serverIP = "202.30.10.6"; // 서버 아이피
		private static final int serverPort = 14444; // ex: 5555 // 접속 포트
		DatagramSocket socket;
		byte[] buf;
		byte[] inbuf = new byte[50];
		InetAddress serverAddr;
		DatagramPacket packet,packet2;
		public void run() {
			try {
				socket = new DatagramSocket();
				serverAddr = InetAddress.getByName(serverIP);
				
				buf = ("INT/값을비우고 시작하자").getBytes();
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				buf = ("REQ/").getBytes();
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				System.out.println("여기까지는 오겠지");
				packet2 = new DatagramPacket(inbuf, inbuf.length, serverAddr,serverPort);
				socket.receive(packet2);
				System.out.println("아직 여기까지 안왔다.");
				
				buf = ("ACK/").getBytes();
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				String a = new String(packet2.getData()).split("/")[0];
				String b = new String(packet2.getData()).split("/")[1];
				setGPSvalue=a+"/"+b;
				freq = new String(packet2.getData()).split("/")[2];
				System.out.println(setGPSvalue);
				if (setGPSvalue.equals("0/0")||freq.equals("a")) {
				}
				else{
					mProgressDialog.dismiss();
					Intent in = new Intent(getApplicationContext(),	ShoesMapActivity.class);
					in.putExtra("GPS", setGPSvalue);
					in.putExtra("FREQ", freq);
					finish();
					startActivity(in);
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}// run
	}
}