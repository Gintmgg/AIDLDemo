package com.example.test.localservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by zhangchunpeng on 2018/7/10.
 */

public class MyService extends Service {
    IMyAidlInterface.Stub mStub = new IMyAidlInterface.Stub() {
        @Override
        public int add(int arg1, int arg2) throws RemoteException {
            return arg1 + arg2;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }
}
