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
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class OptimiseFragment extends Fragment implements View.OnClickListener{
    Context _context;
    Button Optimisebutton ;
    private ProgressBar progressBar ;
    private TextView showBatteryPercentage;
    private BluetoothAdapter bluetoothAdapter;
    View view ;
    private BroadcastReceiver fetchBatteryPercentage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            showBatteryPercentage.setText(String.valueOf(level)+"%");

            progressBar.setProgress(level);
        }
    };


    public OptimiseFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_optimise, container, false);
        showBatteryPercentage = (TextView) view.findViewById(R.id.Batpercent);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar5);
        Optimisebutton = (Button) view.findViewById(R.id.OptimiseButton);
        Optimisebutton.setOnClickListener(this);
         getActivity().registerReceiver(this.fetchBatteryPercentage,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return  view;
    }



    public void optimiseButton(View view) {
      disableBluetooth();

    }


    public void disableBluetooth(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED){
            if(Build.VERSION.SDK_INT>=31){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.BLUETOOTH_CONNECT},100);
                return ;
            }
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);

        if(Build.VERSION.SDK_INT>=31){
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
        else bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
          
        }
    }



    @Override
    public void onClick(View v) {
        disableBluetooth();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        openBatterySaverSettings();
    }

    private void openBatterySaverSettings() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            intent.setAction(Settings.ACTION_BATTERY_SAVER_SETTINGS);
        } else {
            intent.setClassName("com.android.settings", "com.android.settings.appSettings$BatterySaverSettingsActivity");
        }

        startActivity(intent);
    }

}