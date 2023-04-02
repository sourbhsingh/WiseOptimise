package com.example.wiseoptimise;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
