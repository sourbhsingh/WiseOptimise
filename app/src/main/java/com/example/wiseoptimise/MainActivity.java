package com.example.wiseoptimise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BatteryLevelReceiver batteryLevelReceiver;

    ActionBarDrawerToggle drawerToggle;
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
        navigationView.setNavigationItemSelectedListener(this);
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
        tab.getTabAt(0).setIcon(R.drawable.optimise_tab_24);
        tab.getTabAt(1).setIcon(R.drawable.battery_info_tab_24);
        tab.getTabAt(2).setIcon(R.drawable.app_usage_tab_24);
    }
    public  void  openSettings(){
        Intent intent = new Intent(this, appSettings.class);
        startActivity(intent);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle the item click events here
        switch (item.getItemId()) {
            case R.id.optimise_tab_24:
                Toast.makeText(this, "Optimise Tab", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
                break;
            case R.id.battery_info_tab_24:
                Toast.makeText(this, "Battery Info Tab", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
                break;
            case R.id.app_usage_tab_24:
                Toast.makeText(this, "App Usage Tap", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(2);

                break;
            case R.id.Privacypolicy:
                AppNavigation.openWebsite(this,"https://sourbhsingh.github.io/wiseoptimise.github.io/privacy.html");

                break;
            case R.id.nav_share:

                Toast.makeText(this, "Sharing this Apk", Toast.LENGTH_SHORT).show();
                AppNavigation.shareContent(this,"Hello Buddy ! Use this app to increase Your Phone's Battery Health");

                break;
            case R.id.About_us:
                AppNavigation.openWebsite(this,"https://sourbhsingh.github.io/wiseoptimise.github.io/about.html");
                break;
            case R.id.Contactus:
                AppNavigation.openWebsite(this,"https://sourbhsingh.github.io/wiseoptimise.github.io/contact.html");
                break;
            // Add more cases for other menu items

        }
        drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer after item selection
        return true;
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