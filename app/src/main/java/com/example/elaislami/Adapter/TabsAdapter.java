package com.example.elaislami.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.elaislami.Fragment.HomeFragment;
import com.example.elaislami.Fragment.PrayerFragment;
import com.example.elaislami.Fragment.QiblaFragment;
import com.example.elaislami.Fragment.SurahsListFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {

    int nNumOfTabs;

    public TabsAdapter(FragmentManager fm, int nNumOfTabs) {
        super(fm);
        this.nNumOfTabs = nNumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new HomeFragment();

            case 1:
                return new PrayerFragment();

            case 2:
                return new QiblaFragment();

            case 3:
                return new SurahsListFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}

