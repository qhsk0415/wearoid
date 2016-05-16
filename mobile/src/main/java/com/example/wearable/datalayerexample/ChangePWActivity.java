package com.example.wearable.datalayerexample;

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

    EditText PWText;// 비밀번호 변경 텍스트
    EditText CheckText;// 비밀번호 재확인 텍스트
    Button buttonChage;// 변경 버튼
    Button buttonCancel;// 변경 취소 버튼
    EditText HintText;// 힌트 변경 텍스트

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepw);
		setTitle("비밀번호 변경");

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
            // 비밀번호는 최소 4자리
            if (PWText.getText().length() < 4) {
                Toast.makeText(this, "비밀번호는 4자리 이상이어야 합니다.", Toast.LENGTH_SHORT)
                        .show();
                PWText.setText("");
                CheckText.setText("");
            } else {
                // 비밀번호 재확인
                if (PWText.getText().toString()
                        .equals(CheckText.getText().toString())) {
                    editor.putString(INPUTPW, PWText.getText().toString());
                    editor.putString(INPUTHINT, HintText.getText().toString());
                    editor.commit();
                    Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG)
                            .show();
                    finish();
                } else {
                    Toast.makeText(this, "비밀번호가 다릅니다.\n다시 입력해주십시오.",
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
