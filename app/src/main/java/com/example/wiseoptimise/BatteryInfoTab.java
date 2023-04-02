package com.example.wiseoptimise;

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

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BatteryInfoTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BatteryInfoTab extends Fragment {

     TextView textView ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BatteryInfoTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BatteryInfoTab.
     */
    // TODO: Rename and change types and number of parameters
    public static BatteryInfoTab newInstance(String param1, String param2) {
        BatteryInfoTab fragment = new BatteryInfoTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery_info_tab, container, false);
        textView = view.findViewById(R.id.information);
        textView.setText(getBatteryPercentage(getContext()));
        return view ;
    }


    public static String getBatteryPercentage(Context context)
    {
        String bstatus       = "isChrg=false usbChrg=false acChrg=false wlChrg=false 0% t=70°F";
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);
        int status           = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging   = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int level            = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale            = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct     = level * 100 / (float)scale;
        //How are we charging?
        int chargePlug       = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge    = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge     = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        boolean wlCharge     = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        int temp             = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
        float tempTwo        = ((float) temp) / 10;
        double d             = CelsiusToFahrenheit(tempTwo);
        bstatus              = String.format(Locale.US, "isChrg=%b usbChrg=%b acChrg=%b wlChrg=%b %.0f%% t=%.2f°F",
                isCharging,
                usbCharge,
                acCharge,
                wlCharge,
                batteryPct,
                d);
        return bstatus;
    }

    private static double CelsiusToFahrenheit(float tempTwo) {
       double temp = (tempTwo * (9/5)) + 32 ;
       return  temp ;
    }

}