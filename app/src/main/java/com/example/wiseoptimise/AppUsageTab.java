package com.example.wiseoptimise;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppUsageTab extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;

    private ListView appList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_usage_tab, container, false);

        appList = view.findViewById(R.id.app_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        appList.setAdapter(adapter);

        if (checkForPermission()) {
            displayUsageStats(adapter);
        } else {
            requestPermission();
        }

        return view;
    }

    private boolean checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppOpsManager appOps = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), getActivity().getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED;
        } else {
            return true;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !checkForPermission()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayUsageStats((ArrayAdapter<String>) appList.getAdapter());
            } else {
                Snackbar.make(appList, "Permission denied.", Snackbar.LENGTH_LONG).show();
            }
        }
    }
    private void displayUsageStats(ArrayAdapter<String> adapter) {
        UsageStatsManager usageStatsManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long start = cal.getTimeInMillis();
        long end = System.currentTimeMillis();
        List<UsageStats> usageStatsList = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end);
        }

        PackageManager packageManager = getActivity().getPackageManager();
        List<AppUsageData> appUsageDataList = new ArrayList<>();

        for (UsageStats usageStats : usageStatsList) {
            String packageName = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                packageName = usageStats.getPackageName();
            }
            long timeUsed = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                timeUsed = usageStats.getTotalTimeInForeground();
            }
            if (timeUsed > 0) {
                try {
                    // Get the app name and icon
                    String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                    Drawable appIcon = packageManager.getApplicationIcon(packageName);

                    // Create an instance of AppUsageData and add it to the list
                    AppUsageData appUsageData = new AppUsageData(appName, appIcon, timeUsed);
                    appUsageDataList.add(appUsageData);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // Sort the appUsageDataList based on usage time in descending order
        Collections.sort(appUsageDataList, (o1, o2) -> Long.compare(o2.getTimeUsed(), o1.getTimeUsed()));

        adapter.clear();
        for (AppUsageData appUsageData : appUsageDataList) {
            String appName = appUsageData.getAppName();
            String timeUsed = getTimeUsedString(appUsageData.getTimeUsed());

            // Append app name and time used to the adapter
            adapter.add(appName + " - " + timeUsed);
        }
    }

//    private void displayUsageStats(ArrayAdapter<String> adapter) {
//        UsageStatsManager usageStatsManager = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
//        }
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_YEAR, -1);
//        long start = cal.getTimeInMillis();
//        long end = System.currentTimeMillis();
//        List<UsageStats> usageStatsList = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end);
//        }
//
//        Map<String, Long> appUsageTimeMap = new HashMap<>();
//        for (UsageStats usageStats : usageStatsList) {
//            String packageName = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                packageName = usageStats.getPackageName();
//            }
//            long timeUsed = 0;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                timeUsed = usageStats.getTotalTimeInForeground();
//            }
//            if (timeUsed > 0) {
//                appUsageTimeMap.put(packageName, timeUsed);
//            }
//        }
//
//        List<Map.Entry<String, Long>> sortedList = new ArrayList<>(appUsageTimeMap.entrySet());
//        Collections.sort(sortedList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
//
//        adapter.clear();
//        for (Map.Entry<String, Long> entry : sortedList) {
//            String packageName = entry.getKey();
//            long timeUsedInMillis = entry.getValue();
//            String appName = getAppNameFromPackage(packageName);
//            String timeUsed = getTimeUsedString(timeUsedInMillis);
//            adapter.add(appName + " - " + timeUsed);
//        }
//    }

    private String getTimeUsedString(long timeUsedInMillis) {
        long seconds = (timeUsedInMillis / 1000) % 60;
        long minutes = (timeUsedInMillis / (1000 * 60)) % 60;
        long hours = (timeUsedInMillis / (1000 * 60 * 60));
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String getAppNameFromPackage(String packageName) {
        PackageManager packageManager = getActivity().getPackageManager();
        String appName = packageName;
        try {
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // Remove the package name prefix from the app name
        int index = appName.lastIndexOf(".");
        if (index >= 0) {
            appName = appName.substring(index + 1);
        }
        return appName;
    }

}
