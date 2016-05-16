package com.example.alert;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Sound extends Service{
	private MediaPlayer mediaPlayer = null;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		mediaPlayer = MediaPlayer.create(this, R.raw.sound);
		mediaPlayer.start();
		mediaPlayer.setLooping(true);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mediaPlayer.stop();
		super.onDestroy();
	}
}
