package com.example.wiseoptimise;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewpagerOptimiseAdapter extends FragmentPagerAdapter {
    public ViewpagerOptimiseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       if(position==0){
           return new OptimiseFragment();
       } else if(position==1){
            return new BatteryInfoTab();
        } else
           return  new AppUsageTab();

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Optimise";
        } else if(position==1){
            return "Battery Info";
        } else
            return  "App Usage";
    }
}
