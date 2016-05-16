package com.example.wearable.datalayerexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    Button buttonChangePW;// 비밀번호 변경
    Button buttonSetPhoneNum;// 수신자 설정
    Button buttonFindLocation;//위치찾기
    Button buttonHelp;// 도움말
    private long backbuttonPressedTime = 0;// 백버튼을 누른 시간을 재기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("안전 지킴이");

        buttonChangePW = (Button) findViewById(R.id.buttonChangePW);
        buttonChangePW.setOnClickListener(this);

        buttonSetPhoneNum = (Button) findViewById(R.id.buttonSetSMS);
        buttonSetPhoneNum.setOnClickListener(this);

        buttonHelp = (Button) findViewById(R.id.buttonHelp);
        buttonHelp.setOnClickListener(this);
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
                break;

            case R.id.buttonHelp:
                final Dialog helpDialog = new Dialog(this);
                helpDialog.setContentView(R.layout.dialog_layout_intro);
                helpDialog.setTitle("소개");
                TextView tv1 = (TextView) helpDialog.findViewById(R.id.textView2);
                TextView tv2 = (TextView) helpDialog.findViewById(R.id.textView3);
                TextView tv3 = (TextView) helpDialog.findViewById(R.id.textView4);
                Button btn = (Button) helpDialog.findViewById(R.id.buttonOK_Dialog_Intro);
                btn.setOnClickListener(new OnClickListener(){
                    public void onClick(View v){
                        helpDialog.dismiss();
                    }
                });
                helpDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() { // 백버튼을 눌렀을 경우 실행되는 메서드
        // 2초 이후로 백버튼을 눌렀을 경우
        if (System.currentTimeMillis() > backbuttonPressedTime + 2000) {
            backbuttonPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 2초 이내로 백버튼을 눌렀을 경우 앱을 종료함
        else if (System.currentTimeMillis() <= backbuttonPressedTime + 2000) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        } else
            super.onBackPressed();
    }
}
