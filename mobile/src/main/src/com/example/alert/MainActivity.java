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
	Button buttonChangePW;// ��й�ȣ ����
	Button buttonSetPhoneNum;// ������ ����
	private long backbuttonPressedTime = 0;// ���ư�� ���� �ð��� ��� ���� ����

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
	public void onBackPressed() { // ���ư�� ������ ��� ����Ǵ� �޼���
		// 2�� ���ķ� ���ư�� ������ ���
		if (System.currentTimeMillis() > backbuttonPressedTime + 2000) {
			backbuttonPressedTime = System.currentTimeMillis();
			Toast.makeText(this, "\'�ڷ�\'��ư�� �ѹ� �� �����ø� ����˴ϴ�.", Toast.LENGTH_SHORT).show();
			return;
		}
		// 2�� �̳��� ���ư�� ������ ��� ���� ������
		else if (System.currentTimeMillis() <= backbuttonPressedTime + 2000) {
			// ///////////���Ⱑ �ӵ� ���� �κ��̾�!!1000ms��ŭ ����Ť������ٵ� ���̹�ó�� �����Դ� ���ϰ���������
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
