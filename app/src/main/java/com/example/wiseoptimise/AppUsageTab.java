package com.example.wiseoptimise;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AppUsageTab extends Fragment {

    private AppUsageAdapter adapter;
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;

    private ListView appList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_usage_tab, container, false);

        appList = view.findViewById(R.id.app_list);

        adapter = new AppUsageAdapter(getContext());
        appList.setAdapter(adapter);

        if (checkForPermission()) {
            displayUsageStats();
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
    private String getTimeUsedString(long timeUsedInMillis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeUsedInMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeUsedInMillis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(timeUsedInMillis);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void displayUsageStats() {
        UsageStatsManager usageStatsManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long start = cal.getTimeInMillis();
        long end = System.currentTimeMillis();
        List<UsageStats> usageStatsList = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end);
        }

        PackageManager packageManager = getActivity().getPackageManager();
        ArrayList<AppUsageData> appUsageDataList = new ArrayList<>();

        for (UsageStats usageStats : usageStatsList) {
            String packageName = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                packageName = usageStats.getPackageName();
            }
            long timeUsed = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                timeUsed = usageStats.getTotalTimeInForeground();
            }

            ApplicationInfo appInfo = null;
            try {
                appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (appInfo != null) {
                String appName = packageManager.getApplicationLabel(appInfo).toString();
                Drawable appIcon = packageManager.getApplicationIcon(appInfo);

                appUsageDataList.add(new AppUsageData(appName, packageName, appIcon, timeUsed));
            }
        }

        // Sort the app usage data list based on time used
        Collections.sort(appUsageDataList, new Comparator<AppUsageData>() {
            @Override
            public int compare(AppUsageData app1, AppUsageData app2) {
                return Long.compare(app2.getTimeUsed(), app1.getTimeUsed());
            }
        });

        // Update the adapter with the sorted app usage data
        adapter.setAppUsageDataList(appUsageDataList);
        adapter.notifyDataSetChanged();
    }


    private class AppUsageAdapter extends ArrayAdapter<AppUsageData> {

        private ArrayList<AppUsageData> appUsageDataList;

        public AppUsageAdapter(@NonNull Context context) {
            super(context, 0);
            appUsageDataList = new ArrayList<>();
        }

        public void setAppUsageDataList(ArrayList<AppUsageData> appUsageDataList) {
            this.appUsageDataList = appUsageDataList;
        }

        @Override
        public int getCount() {
            return appUsageDataList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_app_usage, parent, false);

                holder = new ViewHolder();
                holder.appIcon = convertView.findViewById(R.id.app_icon_image_view);
                holder.appName = convertView.findViewById(R.id.app_name_text_view);
                holder.appUsage = convertView.findViewById(R.id.time_used_text_view);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AppUsageData appUsageData = appUsageDataList.get(position);

            holder.appIcon.setImageDrawable(appUsageData.getAppIcon());
            holder.appName.setText(appUsageData.getAppName());

            long timeUsedInMillis = appUsageData.getTimeUsed();
            String usageText = getTimeUsedString(timeUsedInMillis);
            holder.appUsage.setText(usageText);

            return convertView;
        }

        private class ViewHolder {
            ImageView appIcon;
            TextView appName;
            TextView appUsage;
        }
    }
}