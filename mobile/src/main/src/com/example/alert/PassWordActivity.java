package com.example.alert;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PassWordActivity extends Activity implements OnClickListener {
	EditText pwText;// 비밀번호 입력 부분
	Button buttonOK;
	TextView hintText;// 힌트 부분

	// MediaPlayer
	MediaPlayer mediaPlayer;

	// preference 이용하기 위함
	SharedPreferences pref;
	SharedPreferences.Editor editor;

	String PassWord = "0000";// 초기 비밀번호
	String text = "위험에 처했습니다!!";// 보낼 문자 -> 선택권을 줘서 문자 내용을 고를 수 있도록 하는 것도 좋을듯
	String PhoneNum;// PhoneActivity에서 받은 전화번호
	int countWrong;// 비밀번호 틀린 횟수
	Context mContext;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);

		intent = new Intent("com.example.alert");
		startService(intent);

		pwText = (EditText) findViewById(R.id.editText);

		buttonOK = (Button) findViewById(R.id.buttonOK);
		buttonOK.setOnClickListener(this);

		hintText = (TextView) findViewById(R.id.textHint);

		pref = getSharedPreferences("prefs", 0);
		editor = pref.edit();
		
		// 정보 불러오기
		PassWord = pref.getString(ChangePWActivity.INPUTPW, "0000");
		hintText.setText(pref.getString(ChangePWActivity.INPUTHINT,
				"초기 비밀번호는 0000입니다."));
		PhoneNum = pref.getString(PhoneActivity.INPUTPHONE, "");

		pwText.setText("");
		mContext = this;
	}

	@Override
	public void onClick(View v) {
		// 확인버튼 누르면 키보드 사라지게 하기 위함
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(pwText.getWindowToken(), 0);
		switch (v.getId()) {
		case R.id.buttonOK:
			// 입력한 비밀번호가 맞을 경우
			if (pwText.getText().toString().equals(PassWord)) {
				stopService(intent);
				Intent intent2 = new Intent(this, MainActivity.class);
				startActivity(intent2);				
				finish();
			} else {
				countWrong++;
				Toast.makeText(this, "비밀번호가 " + countWrong + "회 틀렸습니다.",
						Toast.LENGTH_SHORT).show();
				pwText.setText("");
				if (countWrong == 1)
					Toast.makeText(this, "5회 이상 틀리시면 문자가 전송됩니다.", 1000).show();
				if (countWrong >= 5) {// ////////////5번이상이면 문자전송!!!6번 틀리면 또
										// 전송...이런식
					sendSMS(PhoneNum);// 앱 종료 부분이 빠짐!!!
					// /이후에 사이렌 종료 부분도 넣어야함.
				}
			}
			break;

		default:
			break;
		}
	}

	// 문자 전송 메서드
	public void sendSMS(String phoneNum) {
		PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0,
				new Intent("SMS_SENT_ACTION"), 0);
		PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0,
				new Intent("SMS_DELIVERED_ACTION"), 0);

		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
			}
		}, new IntentFilter("SMS_SENT_ACTION"));

		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
			}
		}, new IntentFilter("SMS_DELIVERED_ACTION"));

		SmsManager mSmsManager = SmsManager.getDefault();
		mSmsManager.sendTextMessage(phoneNum, null, text, sentIntent,
				deliveredIntent);
	}
}
