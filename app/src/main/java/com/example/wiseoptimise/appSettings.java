package com.example.wiseoptimise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;



public class appSettings extends AppCompatActivity {
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SwitchCompat notificationsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ImageButton redirectButton = findViewById(R.id.back_settings);
        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToMainActivity();
            }
        });


        ///////////////////////////////////////////////////////////

        // Find the SwitchCompat view
        notificationsSwitch = findViewById(R.id.notifications_switch);

        // Load the switch state from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isBatteryNotificationEnabled = sharedPreferences.getBoolean("battery_notification_switch", false);

        // Set the switch state
        notificationsSwitch.setChecked(isBatteryNotificationEnabled);

        // Set an OnCheckedChangeListener to listen for switch state changes
        SharedPreferences finalSharedPreferences = sharedPreferences;
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the shared preferences with the new switch state
                finalSharedPreferences.edit().putBoolean("battery_notification_switch", isChecked).apply();
            }
        });






        ///////////////////////////////////////////////////////////
        switchMode = findViewById(R.id.dark_mode_switch);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode",false);

        if (nightMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        SharedPreferences finalSharedPreferences1 = sharedPreferences;
        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = finalSharedPreferences1.edit();
                    editor.putBoolean("nightMode",false);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = finalSharedPreferences1.edit();
                    editor.putBoolean("nightMode",true);
                }
                editor.apply();
            }
        });



}
    private void redirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
