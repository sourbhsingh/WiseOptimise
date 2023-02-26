package com.example.wiseoptimise;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//f

import java.nio.InvalidMarkException;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button optimise ;
    private WifiManager wifiManager ;
    private TextView showBatteryPercentage;
    private BroadcastReceiver fetchBatteryPercentage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            showBatteryPercentage.setText(String.valueOf(level) + "%");
            progressBar.setProgress(level);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBatteryPercentage = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar5);
        optimise = findViewById(R.id.button2);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.registerReceiver(this.fetchBatteryPercentage, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        optimise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager.setWifiEnabled(false);
//                if(wifiManager.isWifiEnabled()) {
//                    wifiManager.disconnect();
//                    wifiManager.setWifiEnabled(false);
//                    Toast.makeText(MainActivity.this, "Your wifi is Turned off :"+wifiManager.isWifiEnabled(), Toast.LENGTH_SHORT).show();
//                }

            }
        });
        }


    }

