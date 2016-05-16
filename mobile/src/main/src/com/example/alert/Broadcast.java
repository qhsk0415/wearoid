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
	String originDate;// ���� ��¥
	String origNumber;// ������ ��ȣ
	String origLocation;// ���⿡�ٰ� ���� ��ġ �ϸ� �ɵ�!!

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
					"yyyy�� MM�� dd�� HH�� mm�� ss��", Locale.KOREA);

			originDate = mDateFormat.format(curDate);
			origNumber = smsMessage[0].getOriginatingAddress();
		}
	}

}
