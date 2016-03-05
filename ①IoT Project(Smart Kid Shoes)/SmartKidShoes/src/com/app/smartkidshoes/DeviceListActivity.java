package com.app.smartkidshoes;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DeviceListActivity extends Activity {
	
	private Button btnScanDevices;
	private ListView pairedListView, newDevicesListView;
	
	private IntentFilter filter;
	
	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	
	// Data members
	private BluetoothAdapter mBluetoothAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_devicelist);
		
		// Set result CANCELED in case the user backs out
		setResult(RESULT_CANCELED);
		
		// Initialize the button to perform device in discovery
		btnScanDevices = (Button)findViewById(R.id.btnScanDevices);
		btnScanDevices.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});
		
		// Initialize array adapters. One for already paired devices and one for newly discovered devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.item_devicename);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.item_devicename);
		
		// Find and set up the ListView for paired devices
		pairedListView = (ListView)findViewById(R.id.tvPairedDevices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);
		
		// Find and set up the ListView for newly discovered devices
		newDevicesListView = (ListView)findViewById(R.id.tvNewDevices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);
		
		// Register for broadcasts when a device is discovered
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);
		
		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
		
		// Get the local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		
		// If there are paired devices, add each one to the ArrayAdapter
		if(pairedDevices.size() > 0) {
			findViewById(R.id.tvTitlePairedDevices).setVisibility(View.VISIBLE);
			for(BluetoothDevice device : pairedDevices) {
				mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			}
		} else {
			String noDevices = "Can't found any devices";
			mPairedDevicesArrayAdapter.add(noDevices);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Make sure we're not doing discovery anymore
		if(mBluetoothAdapter != null) {
			mBluetoothAdapter.cancelDiscovery();
		}
		
		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}
	
	private void doDiscovery() {
		
		// Indicate scanning in the title
		setProgressBarIndeterminate(true);
		setTitle("Scanning for other devices...");
		
		// Turn on sub-title for new devices
		findViewById(R.id.tvTitleNewDevices).setVisibility(View.VISIBLE);
		
		// If we are already discovering, stop it
		if(mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}
		
		// Request discover from BluetoothAdapter
		mBluetoothAdapter.startDiscovery();
	}
	
	// The on-click listener for all devices in the ListViews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			// Cancel discovery because it's costly and we're about to connect
			mBluetoothAdapter.cancelDiscovery();
			
			// Get the device MAC address, which is the last 17 chars in the View
			String information = ((TextView)view).getText().toString();
			String address = information.substring(information.length() - 17);
			
			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
			
			// Set result and finish this Activity
			setResult(RESULT_OK, intent);
			finish();
		}
	};
	
	// The BroadcastReceiver that listens for discovered devices and changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			// When discovery finds a device
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				
				// If it's already paired, skip it. Because it's been listed already
				if(device.getBondState() != BluetoothDevice.BOND_BONDED) {
					mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				}
			} else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {	// When discovery is finished, change the Activity title
				setProgressBarIndeterminateVisibility(false);
				setTitle("Select a device to connect");
				if(mNewDevicesArrayAdapter.getCount() == 0) {
					String noDevices = "Can't found any devices";
					mNewDevicesArrayAdapter.add(noDevices);
				}
			}
		}
	};
}
