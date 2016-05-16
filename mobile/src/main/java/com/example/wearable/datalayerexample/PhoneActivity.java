package com.example.wearable.datalayerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class PhoneActivity extends Activity implements OnClickListener {
    static String[] INPUTPHONE = new String[]{"inputPhone1", "inputPhone2", "inputPhone3"};
    EditText phoneText;// 수신자 번호 입력
    ListView listView;// 수신자 번호 리스트
    private int countPhoneNum = 0; //수신자 번호 개수를 저장하는 변수
    private ArrayList<String> phoneNumbers; //전화번호를 저장하는 배열
    private ArrayAdapter<String> adapter; //phoneNumbers의 어댑터
    Button buttonHelp;
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
        setTitle("수신자 설정");

        pref = getSharedPreferences("prefs", 0);
        editor = pref.edit();

        //전화번호 배열을 리스트뷰에 배열
        phoneText = (EditText) findViewById(R.id.editTextPhone);
        phoneNumbers = new ArrayList<String>();

        if(pref.getString(INPUTPHONE[0], "") != "")
            phoneNumbers.add(pref.getString(INPUTPHONE[0], ""));
        if(pref.getString(INPUTPHONE[1], "") != "")
            phoneNumbers.add(pref.getString(INPUTPHONE[1], ""));
        if(pref.getString(INPUTPHONE[2], "") != "")
            phoneNumbers.add(pref.getString(INPUTPHONE[2], ""));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phoneNumbers);
        listView = (ListView) findViewById(R.id.list);
//		listView.setOnItemClickListener(listViewClickListener);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(listViewClickListener);
        listView.setAdapter(adapter);

        for(int i = 0; i < phoneNumbers.size(); i++)
            countPhoneNum++; //개수 카운트하여 변수에 저장

        buttonHelp = (Button) findViewById(R.id.buttonHelp);
        buttonHelp.setOnClickListener(this);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);

        buttonOK = (Button) findViewById(R.id.buttonOK_SMS);
        buttonOK.setOnClickListener(this);

        buttonCancel = (Button) findViewById(R.id.buttonCancel_SMS);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(phoneText.getWindowToken(), 0);
        switch (v.getId()) {
            case R.id.buttonHelp:
                final Dialog helpDialog = new Dialog(this);
                helpDialog.setContentView(R.layout.dialog_layout_sms);
                helpDialog.setTitle("도움말");
                TextView tv = (TextView) helpDialog.findViewById(R.id.textView1);
                ImageView iv = (ImageView) helpDialog.findViewById(R.id.imageView1);
                Button btn = (Button) helpDialog.findViewById(R.id.buttonOK_Dialog);
                btn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        helpDialog.dismiss();
                    }
                });
                helpDialog.show();
                break;
            case R.id.buttonAdd:
                if (phoneText.getText().length() > 0) {
                    if(countPhoneNum >= 3) { //세 번 까지만 입력 받도록 함
                        Toast.makeText(this, "수신자는 3명까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    countPhoneNum++; //카운트
                    //입력받은 번호를 배열에 추가
                    phoneNumbers.add(phoneText.getText().toString());
                    adapter.notifyDataSetChanged();
                    phoneText.setText("");
                } else {
                    Toast.makeText(this, "번호를 모두 입력해 주십시오.", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.buttonOK_SMS: //프리퍼런스에 번호를 저장
                for(int i = 0; i < 3; i++)//프리퍼런스 초기화
                    editor.putString(INPUTPHONE[i], "");
                editor.commit();
                //프리퍼런스에 다시 저장
                for(int i = 0; i < phoneNumbers.size(); i++) {
                    editor.putString(INPUTPHONE[i], phoneNumbers.get(i));
                }
                editor.commit();
                finish();
                break;
            case R.id.buttonCancel_SMS:
                finish();
            default:
                break;
        }
    }

    OnItemLongClickListener listViewClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            final String delPhoneNum = adapter.getItem(position);

            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(PhoneActivity.this);
            deleteDialog.setTitle(delPhoneNum);
            deleteDialog.setMessage("해당 전화번호를 삭제하시겠습니까?");
            deleteDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.remove(delPhoneNum);
                    adapter.notifyDataSetChanged();

                    countPhoneNum--;
                }
            });
            deleteDialog.setNegativeButton("취소", null);
            deleteDialog.show();

            return false;
        }
    };

}