package com.app.smartkidshoes;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class ConnectionReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ContextUtil.CONTEXT = context;
		String action = intent.getAction();
		  
		/*
		if(Intent.ACTION_BOOT_COMPLETED.equals(action)) {
	         BluetoothService BTService = new BluetoothService(context);
	         BTService.reConnect(PreferenceUtil.lastConnectedDeviceAddress());
	         return ;
	      }*/
		  
		  
		BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		String address = device.getAddress();
		
		if(TextUtils.isEmpty(address)) {
			return ;
		}
		
		if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
			if(BluetoothActivity.stopFlag == 0) {
				this.reConnect(context, address);
			}
			BluetoothActivity.layout.setBackgroundResource(R.drawable.disconnect);
		} else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
			String lastRequestAddress = PreferenceUtil.lastRequestDeviceAddress();
			if(TextUtils.isEmpty(lastRequestAddress)) {
				return ;
			}

			BluetoothActivity.layout.setBackgroundResource(R.drawable.connect);
			if(address.equals(lastRequestAddress)) {
				PreferenceUtil.putLastConnectedDeviceAddress(lastRequestAddress);
				ReConnectService.instance(context).stopReconnect();
				//BluetoothActivity.activity.finish();
				
				//Intent mIntent = new Intent(context, BluetoothActivity.class);
				//mIntent.setAction("com.app.testbluetoothspp.receive");
				//mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//context.startActivity(mIntent);
			}
		}
	}
	
	private void reConnect(Context context, String address) {
		String lastConnectAddress = PreferenceUtil.lastConnectedDeviceAddress();
		if(TextUtils.isEmpty(lastConnectAddress)) {
			return ;
		}
		
		// Rescanning per 10 sec
		if(address.equals(lastConnectAddress)) {
			ReConnectService.instance(context).autoReconnect();
		}
	}
}
