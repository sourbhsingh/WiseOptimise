package com.example.wiseoptimise;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    TabLayout tab ;
    ViewPager viewPager ;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSettings();
            }
        });
        tab= findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

     ViewpagerOptimiseAdapter adapter = new ViewpagerOptimiseAdapter(getSupportFragmentManager());
     viewPager.setAdapter(adapter);
     tab.setupWithViewPager(viewPager);
    }
    public  void  openSettings(){
        Intent intent = new Intent(this, appSettings.class);
        startActivity(intent);
    }

    public void Settings(View view) {
        Toast.makeText(this, "appSettings", Toast.LENGTH_SHORT).show();
    }

    public void Menu(View view) {
        Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
    }

//    public void DisableInternet(){
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            Intent panelIntent = new
//                    Intent(appSettings.Panel.ACTION_INTERNET_CONNECTIVITY);
//            startActivityForResult(panelIntent, 0);
//        } else {
//            // for previous android version
//            WifiManager wifiManager = (WifiManager)
//                    this.getApplicationContext().getSystemService(WIFI_SERVICE);
//            wifiManager.setWifiEnabled(false);
//        }
//    }
}