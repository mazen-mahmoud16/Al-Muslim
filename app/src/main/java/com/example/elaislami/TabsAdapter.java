package com.example.elaislami;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 1:
                PrayerFragment prayerFragment = new PrayerFragment();
                return prayerFragment;

            case 2:
                QiblaFragment qiblaFragment = new QiblaFragment();
                return qiblaFragment;

            case 3:
                QuranFragment quranFragment = new QuranFragment();
                return quranFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}

