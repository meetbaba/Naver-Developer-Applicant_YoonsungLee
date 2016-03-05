package com.app.smartkidshoes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	private Handler mHandler = new Handler();
	private SharedPreferences sp;
	private String lastActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_splash);
		
		sp = getSharedPreferences("LAST_ACTIVITY", 0);
		lastActivity = sp.getString("LAST_ACTIVITY", "INFORM_ACTIVITY");

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(lastActivity.equals("INFORM_ACTIVITY")) {
					Intent intent = new Intent(getApplicationContext(), InformActivity.class);
					startActivity(intent);
				} else if(lastActivity.equals("BLUETOOTH_ACTIVITY")) {
					Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
					startActivity(intent);
				}
				finish();
			}
		}, 3000);
	}
}
