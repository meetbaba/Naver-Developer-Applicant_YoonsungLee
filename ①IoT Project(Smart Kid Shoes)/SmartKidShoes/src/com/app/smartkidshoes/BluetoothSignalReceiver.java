package com.app.smartkidshoes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothSignalReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent in) {
		Toast.makeText(context, in.getStringExtra("signal"), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, BluetoothActivity.class);
		intent.setAction(in.getAction());
		intent.putExtra("msg", in.getStringExtra("signal"));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
