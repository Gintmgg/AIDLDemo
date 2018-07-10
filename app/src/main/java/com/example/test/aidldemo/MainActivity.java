package com.example.test.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.localservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private Button btnResult;
    private TextView tvResult;
    private IMyAidlInterface mStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tv_resutl);
        btnResult = (Button) findViewById(R.id.btn_result);
        Intent intent = new Intent();
        intent.setAction("com.example.test.localservice.myService");
        //android 5.0以后直设置action不能启动相应的服务，需要设置packageName或者Component
        intent.setPackage("com.example.test.localservice"); //packageName 需要和服务端的一致
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //调用asInterface()方法获得IMyAidlInterface实例
            mStub = IMyAidlInterface.Stub.asInterface(iBinder);
            if (mStub == null){
                Log.e("MainActivity", "the mStub is null");
            }else {
                try{
                    final int value = mStub.add(1, 2);
                    btnResult.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tvResult.setText(value + "");
                        }
                    });
                }catch (RemoteException exception){
                    exception.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
