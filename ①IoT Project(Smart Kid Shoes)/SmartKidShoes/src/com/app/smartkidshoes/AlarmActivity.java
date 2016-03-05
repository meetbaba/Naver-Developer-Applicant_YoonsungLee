package com.app.smartkidshoes;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.app.smartkidshoes.MusicService.MusicBinder;

public class AlarmActivity extends Activity implements OnClickListener {
	private Button btnGMap,btnMute; // 버튼 1 -> Google Map으로 넘어가기   // 버튼 2 -> 음소거 & 진동멈추기 
	private String setGPSvalue,freq;  // GPS 값 받아온거 // 아두이노 값 받아온거 
	private Handler mHandler; // 타이머 
	private ProgressDialog mProgressDialog;  // 서버에서 값 받는동안 다이알로그 돌리기 
	private UDPclient udpThread = new UDPclient(); // UDP 서버
	private Thread thread;  // 서버를 돌리기 위한 쓰레드
	private ServiceConnection mConnection; // 경고 음울려주기 위해서 Service
	private boolean mBound; 
	public MusicService mService;
	public Vibrator vibe; // 진동 울리기 위해서 
	private AudioManager audioManager;
	private int systemMaxVolume; // 핸드폰 볼륨 크기 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.enableDefaults(); // 이 부분을 넣어줘야 실행도비니다.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		ReConnectService.instance(ContextUtil.CONTEXT).stopReconnect(); // 이전에 activity에서 시간초를 재고 있던 instance를 멈춘다. 
		BluetoothActivity.activity.finish(); // 이전의 ACTIVITY를 Finish 시킨다
		
		mConnection = new ServiceConnection() { // 뮤직 서비스 호출 
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				MusicBinder binder = (MusicBinder)service;
				mService = binder.getService();
				mBound = true;
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mBound = false;
			}
		};
		
		btnGMap = (Button)findViewById(R.id.button);
		btnMute = (Button)findViewById(R.id.button2);
		
		btnGMap.setOnClickListener(this);
		btnMute.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent service = new Intent(AlarmActivity.this, MusicService.class);
		bindService(service, mConnection, Context.BIND_AUTO_CREATE);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  // 진동 시작 
		vibe.vibrate(30000);
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE); 
		systemMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 안드로이드의 기본 볼륨값을 맥스로 키운다
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,systemMaxVolume, 0);
		
		BluetoothActivity.mBTService.stopSelf();
		ReConnectService.instance(ContextUtil.CONTEXT).stopReconnect();
	}
	
	@Override
	public void onClick(View v) {
		if(v==btnGMap){
		setGPSvalue="0/0"; // GPS값 초기화
		thread = new Thread(udpThread);
		mHandler = new Handler(); // handler로 서버에서 GPS을 값을 받아오는 동안 프로그레스 다이얼로그를 돌려준다.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgressDialog = ProgressDialog.show(
						AlarmActivity.this, "", "잠시만 기다려 주세요.",true);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							if (mProgressDialog != null	&& mProgressDialog.isShowing()) {
								mProgressDialog.dismiss();
								if(setGPSvalue.equals("0/0")){ // 5초동안 프로그레스가 진행되는동안 서버로부터 값을 받아오지 못하면 쓰레드를 죽인다.....
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
		// TODO Auto-generated method stub
		}
		else if(v==btnMute){ // 두번재 버튼이 눌렸을때 
			vibe.cancel(); // 진동 스탑
			mService.musicStop(); // 뮤직서비스 스탑
		}

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
				
				buf = ("REQ/").getBytes(); // 서버에 GPS값과 아두이노에서 받은 값을 달라고 REQ
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				packet2 = new DatagramPacket(inbuf, inbuf.length, serverAddr,serverPort);
				socket.receive(packet2); // 서버에서 보내는 값을 RECEIVE 한다
				
				buf = ("ACK/").getBytes(); // RECEIVE가 되면 ACK를 보내주어 서버에서 전송을 멈추게한다. 
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				String a = new String(packet2.getData()).split("/")[0]; // 현재 전송받은 패킷에는  위도/경도/센서빈도수 가 들어있따 
				String b = new String(packet2.getData()).split("/")[1];
				setGPSvalue=a+"/"+b;
				freq = new String(packet2.getData()).split("/")[2];
				if (setGPSvalue.equals("0/0")||freq.equals("a")) { // 서버에 기록된 값이 없을 경우 신발에서 전송된 값이 없을경우 
				}
				else{
					mProgressDialog.dismiss(); // 다이얼로그를 멈추고 
					Intent in = new Intent(getApplicationContext(),	ShoesMapActivity.class);
					in.putExtra("GPS", setGPSvalue);
					in.putExtra("FREQ", freq);
					finish();
					startActivity(in); // Google API Map으로 액티비티를 전환한다 .
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

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences("LAST_ACTIVITY", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("LAST_ACTIVITY", "BLUETOOTH_ACTIVITY");
		editor.commit();
	}
}
