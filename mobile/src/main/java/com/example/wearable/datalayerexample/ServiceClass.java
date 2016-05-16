package com.example.wearable.datalayerexample;
import android.app.Service;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.os.Looper;
import android.util.Log;

import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;


public class ServiceClass extends Service implements /*Runnable, */GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener {
    private GoogleApiClient mGoogleApiClient;
    int count = 0;

    public void onCreate() {
        Context context = getApplicationContext();
        //서비스는 다똑같지만, 내부적으로 돌아가기떄문에 layout을 설정하지 않아도 된다.

        Toast.makeText(this, "호신용 어플 실행", Toast.LENGTH_LONG).show();


 //       Thread myThread = new Thread(this);

  //      myThread.start();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }
/*
    @Override

    public void run() {

        // TODO Auto-generated method stub

        while (true) {
            try {

                Log.e(null, "my service called #" + count + "   ###   isFromWear : " +  isFromWear);

                count++;

                Thread.sleep(5000);
//5초에 한번씩 로그를 찍어 보도록한다.

            } catch (Exception ex) {

                Log.e(null, ex.toString());

            }

        }

    }
*/

    @Override

    public IBinder onBind(Intent arg0) {

        // TODO Auto-generated method stub

        return null;

    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        for (DataEvent event : dataEvents) {
            // 데이터 변경 이벤트일 때 실행된다.
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();


                Handler mHandler = new Handler(Looper.getMainLooper());

                mHandler.postDelayed(new Runnable() {


                    @Override

                    public void run() { // 버튼눌렀을 떄 해야할 일은 이부분에

                        Context context = getApplicationContext();
                        Intent intent = new Intent(context, PassWordActivity.class);
                        intent.putExtra("isFromWear", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
;

                    }

                }, 0);


/*
                // 동작을 구분할 패스를 가져온다.
                String path = event.getDataItem().getUri().getPath();

                // 패스가 문자 데이터 일 때
                if (path.equals("/STRING_DATA_PATH")) {
                    // 이벤트 객체로부터 데이터 맵을 가져온다.
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());

                    // 데이터맵으로부터 수신한 문자열을 가져온다.
                    final String receiveString = dataMapItem.getDataMap().getString("sendString");

                    // UI 스레드를 실행하여 텍스트 뷰의 값을 수정한다.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(receiveString);
                        }
                    });
                }
*/
                // 데이터 삭제 이벤트일 때 실행된다.
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // 데이터가 삭제됐을 때 수행할 동작
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
