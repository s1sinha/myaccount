package mno.samsung.com.myaccounttest;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by s1sinha on 8/8/16.
 */
public class MyPhoneStateListener extends PhoneStateListener {

    public static Boolean phoneRinging = false;

    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "IDLE");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "RINGING");
                phoneRinging = true;

                break;
        }
    }
}