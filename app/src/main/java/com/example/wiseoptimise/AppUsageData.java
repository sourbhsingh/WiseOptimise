package com.example.wiseoptimise;
import android.graphics.drawable.Drawable;

public class AppUsageData {
    private String appName;
    private Drawable appIcon;
    private long timeUsed;

    public AppUsageData(String appName, Drawable appIcon, long timeUsed) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.timeUsed = timeUsed;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public long getTimeUsed() {
        return timeUsed;
    }
}
