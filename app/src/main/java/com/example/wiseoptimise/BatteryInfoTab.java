package com.example.wiseoptimise;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BatteryInfoTab extends Fragment {
    Context _context;
    PendingIntent pi;
  public TextView batteryHealth , voltage , temperature , batterytype, charging_sourse , status ;



    private void setHealth(Intent intent) {
        int val = intent.getIntExtra("health",0);
        switch (val){
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                batteryHealth.setText("Unknown") ;
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                batteryHealth.setText("Good");
                break ;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                batteryHealth.setText("Overheated");
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                batteryHealth.setText("Dead");
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                batteryHealth.setText("Over Voltage");
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                batteryHealth.setText("Unspecified Failure");
                break;
            case BatteryManager.BATTERY_HEALTH_COLD:
                batteryHealth.setText("Cold");
                break;
            default:
                batteryHealth.setText("Unable to fetch");
        }
    }

    public BatteryInfoTab() {
        // Required empty public constructor
    }
    private BroadcastReceiver myBroadcastReceiver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery_info_tab, container, false);
        batteryHealth = view.findViewById(R.id.bh);
        temperature = view.findViewById(R.id.temperature);
        status = view.findViewById(R.id.charStatus);
        voltage = view.findViewById(R.id.voltage);
        batterytype = view.findViewById(R.id.BatteryType);
        charging_sourse = view.findViewById(R.id.chargingsource);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        setup();
        getContext().registerReceiver(myBroadcastReceiver, intentFilter);

        return view ;
    }
    private void setup()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
       myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
                if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                    float voltagetemp = (float) (intent.getIntExtra("voltage" ,0)*0.001);
                    voltage.setText(voltagetemp+"V");
                    setHealth(intent);
                    batterytype.setText(intent.getStringExtra("technology"));
                    getChargingSource(intent);
                    float tempBat = (float) intent.getIntExtra("temperature",-1)/10 ;
                    temperature.setText(tempBat+"'C");
                    getchargingStatus(intent);

                }
            }
        };


    }

    private void getchargingStatus(Intent intent) {
        int chstatus = intent.getIntExtra("status",-1);
        switch (chstatus){
            case BatteryManager.BATTERY_STATUS_CHARGING:
                   status.setText("Charging");
                   break ;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                status.setText("DisCharging");
                break ;
            case BatteryManager.BATTERY_STATUS_FULL:
                status.setText("Battery Full");
                break ;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                status.setText("Not Charging");
                break ;
            default: status.setText("unknown");
        }
    }

    private void getChargingSource(Intent intent) {
        int status  = intent.getIntExtra("plugged",-1);
        switch (status){
            case BatteryManager.BATTERY_PLUGGED_AC:
                charging_sourse.setText(" AC");
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                charging_sourse.setText(" USB");
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                charging_sourse.setText(" WireLess");
                break;
            case BatteryManager.BATTERY_PLUGGED_DOCK:C:
                charging_sourse.setText(" Dock");
                break;
            default:
                charging_sourse.setText("Not Plugged");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(myBroadcastReceiver);
    }
}