package com.example.wearable.datalayerexample;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class PassWordActivity extends Activity implements OnClickListener {
    EditText pwText;// 비밀번호 입력 부분
    Button buttonOK;
    ImageButton imageButton;
    TextView hintText;// 힌트 부분
    private long backbuttonPressedTime = 0;// 백버튼을 누른 시간을 재기 위한 변수
    // MediaPlayer
    MediaPlayer mediaPlayer;

    // preference 이용하기 위함
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String PassWord = "0000";// 초기 비밀번호
    String text = "위험에 처했습니다!!";// 보낼 문자 -> 선택권을 줘서 문자 내용을 고를 수 있도록 하는 것도 좋을듯
    String PhoneNum1;// PhoneActivity에서 받은 전화번호
    String PhoneNum2;
    String PhoneNum3;
    int countWrong;// 비밀번호 틀린 횟수
    Context mContext;
    Intent intent;
    boolean isFromWear = false;
    boolean isStartSound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        setTitle("안전 지킴이");

        pwText = (EditText) findViewById(R.id.editText);

        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);

        hintText = (TextView) findViewById(R.id.textHint);

        pref = getSharedPreferences("prefs", 0);
        editor = pref.edit();

        // 정보 불러오기
        PassWord = pref.getString(ChangePWActivity.INPUTPW, "0000");
        hintText.setText(pref.getString(ChangePWActivity.INPUTHINT,
                "초기 비밀번호는 0000입니다."));
        PhoneNum1 = pref.getString(PhoneActivity.INPUTPHONE[0], "");
        PhoneNum2 = pref.getString(PhoneActivity.INPUTPHONE[1], "");
        PhoneNum3 = pref.getString(PhoneActivity.INPUTPHONE[2], "");

        pwText.setText("");
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try
        {
            Context context = getApplicationContext();
            Intent i = new Intent(context, ServiceClass.class);
            context.startService(i);
        }
        catch(Exception e)
        {

        }
        try {
            Intent dataIntent = getIntent();
            isFromWear = dataIntent.getExtras().getBoolean("isFromWear");

            if (isFromWear && !isStartSound) {
                if (PhoneNum1 != "")
                    sendSMS(PhoneNum1);
                if (PhoneNum2 != "")
                    sendSMS(PhoneNum2);
                if (PhoneNum3 != "")
                    sendSMS(PhoneNum3);
                intent = new Intent("com.example.wearable.datalayerexample");
                startService(intent);
                isStartSound = true;
            }
        }
        catch(Exception e)
        {

        }
    }

    // '뒤로' 버튼 누르면 토스트만 띄워짐. 비밀번호 입력해야 끌 수 있음.
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "비밀번호를 입력하십시오.", Toast.LENGTH_SHORT).show();
    }

    // 볼륨키 제어 -> 눌러도 아무 변동 없음.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP)
            ;// 토스트 띄우는것 보다 아무것도 안하는게 나을 거 같기두..
        else if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(isStartSound)
            {
                Toast.makeText(this, "비밀번호를 입력하십시오.", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (System.currentTimeMillis() > backbuttonPressedTime + 2000) {
                backbuttonPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                        Toast.LENGTH_SHORT).show();
            }
            // 2초 이내로 백버튼을 눌렀을 경우 앱을 종료함
            else if (System.currentTimeMillis() <= backbuttonPressedTime + 2000) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
            else
                super.onBackPressed();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        // 확인버튼 누르면 키보드 사라지게 하기 위함
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pwText.getWindowToken(), 0);

        switch (v.getId()) {
            case R.id.buttonOK:
                // 입력한 비밀번호가 맞을 경우
                if (pwText.getText().toString().equals(""))
                    Toast.makeText(this, "비밀번호를 입력해주십시오.", Toast.LENGTH_SHORT)
                            .show();
                else if (pwText.getText().toString().equals(PassWord)) {

                    try {
                        stopService(intent);
                        isStartSound = false;
                        isFromWear = false;
                    }
                    catch(Exception e)
                    {

                    }
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
                    finish();
                } else {
                    countWrong++;
                    Toast.makeText(this, "비밀번호가 " + countWrong + "회 틀렸습니다.",
                            Toast.LENGTH_SHORT).show();
                    pwText.setText("");
                    if (countWrong == 1)
                        Toast.makeText(this, "5회 이상 틀리시면 문자가 전송됩니다.", Toast.LENGTH_SHORT).show();
                    if (countWrong >= 5) {// ////////////5번이상이면 문자전송!!!6번 틀리면 또
                        if (PhoneNum1 != "")
                            sendSMS(PhoneNum1);
                        if (PhoneNum2 != "")
                            sendSMS(PhoneNum2);
                        if (PhoneNum3 != "")
                            sendSMS(PhoneNum3);
                    }
                }
                break;
            case R.id.imageButton:
                if(isFromWear == false && !isStartSound) {
                    isFromWear = true;
                    intent = new Intent("com.example.wearable.datalayerexample");
                    startService(intent);
                    if (PhoneNum1 != "")
                        sendSMS(PhoneNum1);
                    if (PhoneNum2 != "")
                        sendSMS(PhoneNum2);
                    if (PhoneNum3 != "")
                        sendSMS(PhoneNum3);
                    isStartSound = true;
                }
                    break;

            default:
                break;
        }
    }

    // 문자 전송 메서드
    public void sendSMS(String phoneNum) {
        String text = "위험에 처했습니다!!";// 보낼 문자
        GpsInfo gps = new GpsInfo(mContext);
        Geocoder mCoder = new Geocoder(this);
        String origLocation;
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        List<Address> addr;

        String slat = Double.toString(latitude);
        String slon = Double.toString(longitude);
        try {
            addr = mCoder.getFromLocation(Double.parseDouble(slat),
                    Double.parseDouble(slon), 5);
            Address address = addr.get(0);
            origLocation = address.getCountryName() + " " + address.getAdminArea() + " " + address.getLocality() + " " + address.getFeatureName();
        } catch (IOException e) {
            return;
        }
        origLocation += "\n위도 : " + latitude + "\n경도 : " + longitude;
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
        if(origLocation.length() >= 80)
        {
            String sub1 = origLocation.substring(0, 79);
            String sub2 = origLocation.substring(80);
            mSmsManager.sendTextMessage(phoneNum, null, text + "\n" + sub1, sentIntent,
                    deliveredIntent);
            mSmsManager.sendTextMessage(phoneNum, null, text + "\n" + sub2, sentIntent,
                    deliveredIntent);
        }
        else mSmsManager.sendTextMessage(phoneNum, null, text + "\n" + origLocation, sentIntent,
                deliveredIntent);
    }
}
