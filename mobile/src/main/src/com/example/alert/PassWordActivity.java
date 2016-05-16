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
	EditText pwText;// ��й�ȣ �Է� �κ�
	Button buttonOK;
	TextView hintText;// ��Ʈ �κ�

	// MediaPlayer
	MediaPlayer mediaPlayer;

	// preference �̿��ϱ� ����
	SharedPreferences pref;
	SharedPreferences.Editor editor;

	String PassWord = "0000";// �ʱ� ��й�ȣ
	String text = "���迡 ó�߽��ϴ�!!";// ���� ���� -> ���ñ��� �༭ ���� ������ �� �� �ֵ��� �ϴ� �͵� ������
	String PhoneNum;// PhoneActivity���� ���� ��ȭ��ȣ
	int countWrong;// ��й�ȣ Ʋ�� Ƚ��
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
		
		// ���� �ҷ�����
		PassWord = pref.getString(ChangePWActivity.INPUTPW, "0000");
		hintText.setText(pref.getString(ChangePWActivity.INPUTHINT,
				"�ʱ� ��й�ȣ�� 0000�Դϴ�."));
		PhoneNum = pref.getString(PhoneActivity.INPUTPHONE, "");

		pwText.setText("");
		mContext = this;
	}

	@Override
	public void onClick(View v) {
		// Ȯ�ι�ư ������ Ű���� ������� �ϱ� ����
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(pwText.getWindowToken(), 0);
		switch (v.getId()) {
		case R.id.buttonOK:
			// �Է��� ��й�ȣ�� ���� ���
			if (pwText.getText().toString().equals(PassWord)) {
				stopService(intent);
				Intent intent2 = new Intent(this, MainActivity.class);
				startActivity(intent2);				
				finish();
			} else {
				countWrong++;
				Toast.makeText(this, "��й�ȣ�� " + countWrong + "ȸ Ʋ�Ƚ��ϴ�.",
						Toast.LENGTH_SHORT).show();
				pwText.setText("");
				if (countWrong == 1)
					Toast.makeText(this, "5ȸ �̻� Ʋ���ø� ���ڰ� ���۵˴ϴ�.", 1000).show();
				if (countWrong >= 5) {// ////////////5���̻��̸� ��������!!!6�� Ʋ���� ��
										// ����...�̷���
					sendSMS(PhoneNum);// �� ���� �κ��� ����!!!
					// /���Ŀ� ���̷� ���� �κе� �־����.
				}
			}
			break;

		default:
			break;
		}
	}

	// ���� ���� �޼���
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
