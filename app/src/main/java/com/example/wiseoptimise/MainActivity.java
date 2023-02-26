package com.example.wiseoptimise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar ;
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
}