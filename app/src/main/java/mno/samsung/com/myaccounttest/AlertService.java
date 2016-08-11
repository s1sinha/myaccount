package mno.samsung.com.myaccounttest;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlertService extends Service{
    private SmsReceiver mSMSreceiver;
    private IntentFilter mIntentFilter;
    @Override
    public void onCreate() {
        super.onCreate();
        mSMSreceiver = new SmsReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
    }

    @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSMSreceiver);
    }
}
