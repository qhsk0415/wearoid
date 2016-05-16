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

public class ChangePWActivity extends Activity implements OnClickListener {
	public static final String INPUTPW = "InputPW";
	public static final String INPUTHINT = "InputHint";

	EditText PWText;// ��й�ȣ ���� �ؽ�Ʈ
	EditText CheckText;// ��й�ȣ ��Ȯ�� �ؽ�Ʈ
	Button buttonChage;// ���� ��ư
	Button buttonCancel;// ���� ��� ��ư
	EditText HintText;// ��Ʈ ���� �ؽ�Ʈ

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepw);

		PWText = (EditText) findViewById(R.id.editText_change);
		CheckText = (EditText) findViewById(R.id.editText_check);

		buttonChage = (Button) findViewById(R.id.buttonChange);
		buttonChage.setOnClickListener(this);

		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);

		HintText = (EditText) findViewById(R.id.hintText);

		pref = getSharedPreferences("prefs", 0);
		editor = pref.edit();
	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(PWText.getWindowToken(), 0);
		switch (v.getId()) {
		case R.id.buttonChange:
			// ��й�ȣ�� �ּ� 4�ڸ�
			if (PWText.getText().length() < 4) {
				Toast.makeText(this, "��й�ȣ�� 4�ڸ� �̻��̾�� �մϴ�.", Toast.LENGTH_SHORT)
						.show();
				PWText.setText("");
				CheckText.setText("");
			} else {
				// ��й�ȣ ��Ȯ��
				if (PWText.getText().toString()
						.equals(CheckText.getText().toString())) {
					editor.putString(INPUTPW, PWText.getText().toString());
					editor.putString(INPUTHINT, HintText.getText().toString());
					editor.commit();
					Toast.makeText(this, "��й�ȣ�� ����Ǿ����ϴ�.", Toast.LENGTH_LONG)
							.show();
					finish();
				} else {
					Toast.makeText(this, "��й�ȣ�� �ٸ��ϴ�.\n�ٽ� �Է����ֽʽÿ�.",
							Toast.LENGTH_SHORT).show();
					PWText.setText("");
					CheckText.setText("");
				}
			}
			break;
		case R.id.buttonCancel:
			finish();
			break;
		default:
			break;
		}
	}
}
