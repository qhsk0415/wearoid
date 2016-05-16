// C:\Users\DMapS1\AppData\Local\Android\sdk\platform-tools
// adb -d forward tcp:5601 tcp:5601

package com.example.wearable.datalayerexample;


        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.PendingResult;
        import com.google.android.gms.common.api.Result;
        import com.google.android.gms.common.api.ResultCallback;
        import com.google.android.gms.wearable.Asset;
        import com.google.android.gms.wearable.DataApi;
        import com.google.android.gms.wearable.Node;
        import com.google.android.gms.wearable.NodeApi;
        import com.google.android.gms.wearable.PutDataMapRequest;
        import com.google.android.gms.wearable.PutDataRequest;
        import com.google.android.gms.wearable.Wearable;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;


public class MyActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private EditText mEditText; // 시계로 전송 할 텍스트뷰

    private GoogleApiClient mGoogleApiClient; // 구글 플레이 서비스 API 객체

    @Override // Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // 시계로 전송 할 텍스트뷰

        // 구글 플레이 서비스 객체를 시계 설정으로 초기화
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    // 액티비티가 시작할 때 실행
    @Override // Activity
    protected void onStart() {
        super.onStart();

        // 구글 플레이 서비스에 접속돼 있지 않다면 접속한다.
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    // 액티비티가 종료될 때 실행
    @Override // Activity
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    // 구글 플레이 서비스에 접속 됐을 때 실행
    @Override // GoogleApiClient.ConnectionCallbacks
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    // 구글 플레이 서비스에 접속이 일시정지 됐을 때 실행
    @Override // GoogleApiClient.ConnectionCallbacks
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();
    }

    // 구글 플레이 서비스에 접속을 실패했을 때 실행
    @Override // GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }


    // 시계로 데이터 및 메시지를 전송 후 실행되는 메소드
    private ResultCallback resultCallback = new ResultCallback() {
        @Override
        public void onResult(Result result) {

            String resultString = "Sending Result : " + result.getStatus().isSuccess();

            Toast.makeText(getApplication(), resultString, Toast.LENGTH_SHORT).show();
        }
    };

    // 데이터 전송 횟수이다.
    // 텍스트가 같더라도 데이터가 계속 변할 수 있도록 count 값을 같이 보낸다.
    private int sendCount = 0;

    // Send Data String 버튼을 클릭했을 때 실행
    public void onSendDataString(View view) {
        Log.d("123", "버튼클릭");
        // 전송할 텍스트를 생성한다.
        String text = "wearableSend";
        // 시계로 전송할 데이터 묶음인 데이터 맵을 생성한다.
        PutDataMapRequest dataMap = PutDataMapRequest.create("/STRING_DATA_PATH");
        dataMap.getDataMap().putString("sendString", text);
        // 전송할 텍스트를 지정한다.

        // 현재 보내는 텍스트와 지난번 보냈던 텍스트가 같으면
        // onDataChanged() 메소드가 실행되지 않는다.
        // 텍스트가 같더라도 데이터가 계속 변할 수 있도록 count 값을 같이 보낸다.
       dataMap.getDataMap().putInt("count", sendCount++);

        // 데이터 맵으로 전송할 요청 객체를 생성한다.
       PutDataRequest request = dataMap.asPutDataRequest();

        // 데이터 전송 및 전송 후 실행 될 콜백 함수 지정
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);
    }


}
