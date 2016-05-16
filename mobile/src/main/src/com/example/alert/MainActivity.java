package com.example.alert;

import android.app.Activity;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button buttonChangePW;// 비밀번호 변경
	Button buttonSetPhoneNum;// 수신자 설정
	private long backbuttonPressedTime = 0;// 백버튼을 누른 시간을 재기 위한 변수

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonChangePW = (Button) findViewById(R.id.buttonChangePW);
		buttonChangePW.setOnClickListener(this);

		buttonSetPhoneNum = (Button) findViewById(R.id.buttonSetSMS);
		buttonSetPhoneNum.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonChangePW:
			Intent intent = new Intent(this, ChangePWActivity.class);
			startActivity(intent);
			break;
		case R.id.buttonSetSMS:
			Intent intent3 = new Intent(this, PhoneActivity.class);
			startActivity(intent3);
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() { // 백버튼을 눌렀을 경우 실행되는 메서드
		// 2초 이후로 백버튼을 눌렀을 경우
		if (System.currentTimeMillis() > backbuttonPressedTime + 2000) {
			backbuttonPressedTime = System.currentTimeMillis();
			Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
			return;
		}
		// 2초 이내로 백버튼을 눌렀을 경우 앱을 종료함
		else if (System.currentTimeMillis() <= backbuttonPressedTime + 2000) {
			// ///////////여기가 속도 늦춘 부분이야!!1000ms만큼 늦춘거ㅋㅋㅋ근데 네이버처럼 꺼지게는 못하겠음ㅋㅋㅋ
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 1500);
			
		} else
			super.onBackPressed();
	}
}
