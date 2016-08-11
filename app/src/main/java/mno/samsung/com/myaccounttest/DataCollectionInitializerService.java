package mno.samsung.com.myaccounttest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class DataCollectionInitializerService extends Service {
    public DataCollectionInitializerService() {
    }

    WifiManager mainWifi;
    BluetoothAdapter btAdapter;
    BluetoothDevice device;
    BluetoothAdapter mBluetoothAdapter;
    TelephonyManager tManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //WIFI
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (mainWifi.isWifiEnabled() == false) {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();

            mainWifi.setWifiEnabled(true);
        }
        //BLUETOOTH
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            //Bluetooth is disabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(enableBtIntent);
        }

        //Activity Manager
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
        int currentPid = android.os.Process.myPid();
        int pids[] = new int[1];
        pids[0] = currentPid;
        Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(pids);
        for (Debug.MemoryInfo pidMemoryInfo : memoryInfoArray) {
            Log.i("TOTAL", " pidMemoryInfo.getTotalPss(): " + pidMemoryInfo.getTotalPss() + "\n");
        }
        //PhoneStateListener
        MyPhoneStateListener phoneListener = new MyPhoneStateListener();
        tManager = (TelephonyManager) getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        tManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }
        @Override
        public IBinder onBind (Intent intent){
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

}
