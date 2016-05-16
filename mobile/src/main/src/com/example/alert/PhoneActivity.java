package com.example.alert;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneActivity extends Activity implements OnClickListener {
	public static final String INPUTPHONE = "InputPhone";
	EditText phoneText;// 수신자 번호 입력
	Button buttonAdd;// 수신자 입력란 추가 버튼
	Button buttonSub;// 수신자 입력란 삭제 버튼
	Button buttonOK;
	Button buttonCancel;

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);

		phoneText = (EditText) findViewById(R.id.editTextPhone);

		buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonAdd.setOnClickListener(this);

		buttonOK = (Button) findViewById(R.id.buttonOK_SMS);
		buttonOK.setOnClickListener(this);

		buttonCancel = (Button) findViewById(R.id.buttonCancel_SMS);
		buttonCancel.setOnClickListener(this);

		pref = getSharedPreferences("prefs", 0);
		editor = pref.edit();

		phoneText.setText(pref.getString(INPUTPHONE, ""));// 입력한 비밀번호 가져오기.
															// 초기에는 빈칸

		phoneText.setSelection(phoneText.length());// 커서를 텍스트의 마지막으로
	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(phoneText.getWindowToken(), 0);
		switch (v.getId()) {
		case R.id.buttonAdd:
			// 여기에 동적할당 하는 걸로 하면 좋을 거 같아요~ 압축 파일에 어떻게 하면 좋을지 같이 보내드릴께요~
			break;
		case R.id.buttonOK_SMS:
			if (phoneText.getText().length() > 0) {
				editor.putString(INPUTPHONE, phoneText.getText().toString());
				editor.commit();
				finish();
			} else {
				Toast.makeText(this, "번호를 모두 입력해 주십시오.", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.buttonCancel_SMS:
			finish();

		default:
			break;
		}
	}
}
