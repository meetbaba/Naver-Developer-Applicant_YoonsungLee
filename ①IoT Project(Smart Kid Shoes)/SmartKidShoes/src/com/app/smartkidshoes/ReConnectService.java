package com.app.smartkidshoes;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReConnectService {
	private static int timeCounter = 0;
	private static ReConnectService mInstance;
	private Context mContext;
	private static Timer mTimer;
	private static ScheduledExecutorService mScheduledExecutorService;
	
	private ReConnectService(Context context) {
		mContext = context;
	}
	
	public static synchronized ReConnectService instance(Context context) {
		if(mInstance == null) {
			mInstance = new ReConnectService(context);
		}
		return mInstance;
	}
	
	// Reconnection per 10 sec
	public void autoReconnect() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(timeCounter == 0) {
					Log.e("Debug", "Reconnection Start!");
				} else {
					if(timeCounter == 15) {
						/*
						  여기에 GPS로 전환 
						 */
						Log.e("Debug","인텐트전환 ㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
						Intent intent = new Intent(ContextUtil.CONTEXT, AlarmActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						ContextUtil.CONTEXT.startActivity(intent);
					}
					Log.e("Debug", "Reconnection... " + timeCounter + "sec");
				}
				timeCounter++;
				BluetoothService BTService = new BluetoothService(mContext);
				BTService.reConnect(PreferenceUtil.lastConnectedDeviceAddress());
			}
		};
		
		mTimer = new Timer();
		mTimer.schedule(task, 0, 1000);
	}
	
	public void stopReconnect() {
		timeCounter = 0;
		if(mTimer != null) {
			mTimer.cancel();
		}
		
		if(mScheduledExecutorService != null) {
			mScheduledExecutorService.shutdown();
		}
	}
}
