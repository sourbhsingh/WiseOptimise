package com.example.wiseoptimise;

import android.graphics.drawable.Drawable;

public class AppUsageData {
    private String appName;
    private String packageName;
    private Drawable appIcon;
    private long timeUsed;

    public AppUsageData(String appName, String packageName, Drawable appIcon, long timeUsed) {
        this.appName = appName;
        this.packageName = packageName;
        this.appIcon = appIcon;
        this.timeUsed = timeUsed;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public long getTimeUsed() {
        return timeUsed;
    }
}
