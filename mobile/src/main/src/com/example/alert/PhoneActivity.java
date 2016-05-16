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
	EditText phoneText;// ������ ��ȣ �Է�
	Button buttonAdd;// ������ �Է¶� �߰� ��ư
	Button buttonSub;// ������ �Է¶� ���� ��ư
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

		phoneText.setText(pref.getString(INPUTPHONE, ""));// �Է��� ��й�ȣ ��������.
															// �ʱ⿡�� ��ĭ

		phoneText.setSelection(phoneText.length());// Ŀ���� �ؽ�Ʈ�� ����������
	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(phoneText.getWindowToken(), 0);
		switch (v.getId()) {
		case R.id.buttonAdd:
			// ���⿡ �����Ҵ� �ϴ� �ɷ� �ϸ� ���� �� ���ƿ�~ ���� ���Ͽ� ��� �ϸ� ������ ���� �����帱����~
			break;
		case R.id.buttonOK_SMS:
			if (phoneText.getText().length() > 0) {
				editor.putString(INPUTPHONE, phoneText.getText().toString());
				editor.commit();
				finish();
			} else {
				Toast.makeText(this, "��ȣ�� ��� �Է��� �ֽʽÿ�.", Toast.LENGTH_SHORT)
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
