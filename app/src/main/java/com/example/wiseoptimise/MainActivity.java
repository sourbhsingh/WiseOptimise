package com.example.wiseoptimise;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BatteryLevelReceiver batteryLevelReceiver;

    TabLayout tab ;
    ViewPager viewPager ;
    ImageButton imageButton;
    ImageButton imageButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevelReceiver = new BatteryLevelReceiver();

        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryLevelReceiver);
    }
}