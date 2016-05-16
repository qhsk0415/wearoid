package com.example.alert;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class Broadcast extends BroadcastReceiver {

	Context mContext;
	String originDate;// 현재 날짜
	String origNumber;// 수신자 번호
	String origLocation;// 여기에다가 현재 위치 하면 될듯!!

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
			Bundle bundle = intent.getExtras();

			Object messages[] = (Object[]) bundle.get("pdus");
			SmsMessage smsMessage[] = new SmsMessage[messages.length];

			for (int i = 0; i < messages.length; i++) {
				smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			}
			Date curDate = new Date(smsMessage[0].getTimestampMillis());
			SimpleDateFormat mDateFormat = new SimpleDateFormat(
					"yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA);

			originDate = mDateFormat.format(curDate);
			origNumber = smsMessage[0].getOriginatingAddress();
		}
	}

}
