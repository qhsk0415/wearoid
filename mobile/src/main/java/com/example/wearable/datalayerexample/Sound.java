package com.example.wearable.datalayerexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Sound extends Service {
	private MediaPlayer mediaPlayer = null; // �溸�� mp3 ����ϱ� ����
	private AudioManager audioManager = null; // ����� ���� �����ϱ� ����

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// ����� ���� �ִ��
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				(int) (audioManager
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC)), 0);
		// �溸��
		mediaPlayer = MediaPlayer.create(this, R.raw.sound);
		mediaPlayer.start();
		mediaPlayer.setLooping(true);// �ݺ� ��� ����
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mediaPlayer.stop();// ����
		super.onDestroy();
	}
}
