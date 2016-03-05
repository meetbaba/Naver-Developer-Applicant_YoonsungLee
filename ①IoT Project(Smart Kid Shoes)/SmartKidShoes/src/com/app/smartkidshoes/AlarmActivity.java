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
	private Button btnGMap,btnMute; // ��ư 1 -> Google Map���� �Ѿ��   // ��ư 2 -> ���Ұ� & �������߱� 
	private String setGPSvalue,freq;  // GPS �� �޾ƿ°� // �Ƶ��̳� �� �޾ƿ°� 
	private Handler mHandler; // Ÿ�̸� 
	private ProgressDialog mProgressDialog;  // �������� �� �޴µ��� ���̾˷α� ������ 
	private UDPclient udpThread = new UDPclient(); // UDP ����
	private Thread thread;  // ������ ������ ���� ������
	private ServiceConnection mConnection; // ��� ������ֱ� ���ؼ� Service
	private boolean mBound; 
	public MusicService mService;
	public Vibrator vibe; // ���� �︮�� ���ؼ� 
	private AudioManager audioManager;
	private int systemMaxVolume; // �ڵ��� ���� ũ�� 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.enableDefaults(); // �� �κ��� �־���� ���൵��ϴ�.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		ReConnectService.instance(ContextUtil.CONTEXT).stopReconnect(); // ������ activity���� �ð��ʸ� ��� �ִ� instance�� �����. 
		BluetoothActivity.activity.finish(); // ������ ACTIVITY�� Finish ��Ų��
		
		mConnection = new ServiceConnection() { // ���� ���� ȣ�� 
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
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  // ���� ���� 
		vibe.vibrate(30000);
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE); 
		systemMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // �ȵ���̵��� �⺻ �������� �ƽ��� Ű���
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,systemMaxVolume, 0);
		
		BluetoothActivity.mBTService.stopSelf();
		ReConnectService.instance(ContextUtil.CONTEXT).stopReconnect();
	}
	
	@Override
	public void onClick(View v) {
		if(v==btnGMap){
		setGPSvalue="0/0"; // GPS�� �ʱ�ȭ
		thread = new Thread(udpThread);
		mHandler = new Handler(); // handler�� �������� GPS�� ���� �޾ƿ��� ���� ���α׷��� ���̾�α׸� �����ش�.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgressDialog = ProgressDialog.show(
						AlarmActivity.this, "", "��ø� ��ٷ� �ּ���.",true);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							if (mProgressDialog != null	&& mProgressDialog.isShowing()) {
								mProgressDialog.dismiss();
								if(setGPSvalue.equals("0/0")){ // 5�ʵ��� ���α׷����� ����Ǵµ��� �����κ��� ���� �޾ƿ��� ���ϸ� �����带 ���δ�.....
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
		else if(v==btnMute){ // �ι��� ��ư�� �������� 
			vibe.cancel(); // ���� ��ž
			mService.musicStop(); // �������� ��ž
		}

	}
	private class UDPclient implements Runnable {

		private static final String serverIP = "202.30.10.6"; // ���� ������
		private static final int serverPort = 14444; // ex: 5555 // ���� ��Ʈ
		DatagramSocket socket;
		byte[] buf;
		byte[] inbuf = new byte[50];
		InetAddress serverAddr;
		DatagramPacket packet,packet2;
		public void run() {
			try {
				socket = new DatagramSocket();
				serverAddr = InetAddress.getByName(serverIP);
				
				buf = ("REQ/").getBytes(); // ������ GPS���� �Ƶ��̳뿡�� ���� ���� �޶�� REQ
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				packet2 = new DatagramPacket(inbuf, inbuf.length, serverAddr,serverPort);
				socket.receive(packet2); // �������� ������ ���� RECEIVE �Ѵ�
				
				buf = ("ACK/").getBytes(); // RECEIVE�� �Ǹ� ACK�� �����־� �������� ������ ���߰��Ѵ�. 
				packet = new DatagramPacket(buf, buf.length, serverAddr,serverPort);
				socket.send(packet);
				
				String a = new String(packet2.getData()).split("/")[0]; // ���� ���۹��� ��Ŷ����  ����/�浵/�����󵵼� �� ����ֵ� 
				String b = new String(packet2.getData()).split("/")[1];
				setGPSvalue=a+"/"+b;
				freq = new String(packet2.getData()).split("/")[2];
				if (setGPSvalue.equals("0/0")||freq.equals("a")) { // ������ ��ϵ� ���� ���� ��� �Ź߿��� ���۵� ���� ������� 
				}
				else{
					mProgressDialog.dismiss(); // ���̾�α׸� ���߰� 
					Intent in = new Intent(getApplicationContext(),	ShoesMapActivity.class);
					in.putExtra("GPS", setGPSvalue);
					in.putExtra("FREQ", freq);
					finish();
					startActivity(in); // Google API Map���� ��Ƽ��Ƽ�� ��ȯ�Ѵ� .
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
