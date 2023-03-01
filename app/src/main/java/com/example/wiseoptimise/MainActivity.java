package com.example.wiseoptimise;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar ;
    private BluetoothAdapter bAdapter;
    private TextView showBatteryPercentage;
    private BroadcastReceiver fetchBatteryPercentage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            showBatteryPercentage.setText(String.valueOf(level)+"%");
            progressBar.setProgress(level);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBatteryPercentage= findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar5);
        this.registerReceiver(this.fetchBatteryPercentage, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    public void optimiseBattery(View view) {
   disableBluetooth();





    }

    public void disableBluetooth(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED){
            if(Build.VERSION.SDK_INT>=31){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.BLUETOOTH_CONNECT},100);
                return ;
            }
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if(Build.VERSION.SDK_INT>=31){
            bAdapter = bluetoothManager.getAdapter();
        }
        else bAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bAdapter.isEnabled()) {
            bAdapter.disable();
            Toast.makeText(this, "Bluetooth Disabled", Toast.LENGTH_SHORT).show();
        }
    }
}