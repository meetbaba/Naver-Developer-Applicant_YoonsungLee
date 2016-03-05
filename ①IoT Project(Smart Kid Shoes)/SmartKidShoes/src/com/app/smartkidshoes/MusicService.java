package com.app.smartkidshoes;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;


public class MusicService extends Service {
	
	private float volume = 0.5f;
	private MediaPlayer player;
	private final IBinder mBinder = new MusicBinder();
	
	public MusicService() {
	}
	
	public class MusicBinder extends Binder {
		MusicService getService() {
			Toast.makeText(MusicService.this, "알람 울림", Toast.LENGTH_SHORT);
			player = MediaPlayer.create(MusicService.this, R.raw.music);
			player.setLooping(true);
			player.start();
			return MusicService.this;
		}
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public void musicStart() {
		player = MediaPlayer.create(MusicService.this, R.raw.music);
		player.setLooping(false);
		player.start();
	}
	
	public void musicStop() {
		volume = 0.5f;
		player.stop();
	}
	

}