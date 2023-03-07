package com.example.wiseoptimise;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TabLayout tab ;
    ViewPager viewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tab= findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

     ViewpagerOptimiseAdapter adapter = new ViewpagerOptimiseAdapter(getSupportFragmentManager());
     viewPager.setAdapter(adapter);
     tab.setupWithViewPager(viewPager);
    }

    public void Settings(View view) {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
    }

    public void Menu(View view) {
        Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
    }


//    public void DisableInternet(){
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            Intent panelIntent = new
//                    Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
//            startActivityForResult(panelIntent, 0);
//        } else {
//            // for previous android version
//            WifiManager wifiManager = (WifiManager)
//                    this.getApplicationContext().getSystemService(WIFI_SERVICE);
//            wifiManager.setWifiEnabled(false);
//        }
//    }
}