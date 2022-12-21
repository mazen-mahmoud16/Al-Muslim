package com.example.elaislami.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.elaislami.R;
import com.example.elaislami.RoomDBManager.RoomDBModel.PrayerStatisticsDBModel;

import java.util.List;

/*
 * This is the prayer statistics adapter for the view pager
 */
public class PrayerStatsViewPagerAdapter extends PagerAdapter {

    /*
     * attributes for adapter
     */
    private List<PrayerStatisticsDBModel> prayerStatisticsDBModels;
    private final Context context;

    // Here is the constructor
    public PrayerStatsViewPagerAdapter(List<PrayerStatisticsDBModel> models, Context context) {
        this.prayerStatisticsDBModels = models;
        this.context = context;
    }

    /*
     * Get size of prayer statistics array
     */
    @Override
    public int getCount() {
        return prayerStatisticsDBModels.size();
    }

    /*
     * Check whether view is from object
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }

    /*
     * Here is the function to instantiate items
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.prayer_stats_viewpager_item, container, false);

        CheckBox chk1,chk2,chk3,chk4,chk5;

        chk1 = view.findViewById(R.id.chk_fajr);
        chk2 = view.findViewById(R.id.chk_dhuhr);
        chk3 = view.findViewById(R.id.chk_asr);
        chk4 = view.findViewById(R.id.chk_maghrib);
        chk5 = view.findViewById(R.id.chk_isha);

        // If fajr is pressed
        chk1.setOnCheckedChangeListener((compoundButton, b) -> prayerStatisticsDBModels.get(position).setFajr(b));

        // If dhuhr is pressed
        chk2.setOnCheckedChangeListener((compoundButton, b) -> prayerStatisticsDBModels.get(position).setDhuhr(b));

        // If asr is pressed
        chk3.setOnCheckedChangeListener((compoundButton, b) -> prayerStatisticsDBModels.get(position).setAsr(b));

        // If maghrib is pressed
        chk4.setOnCheckedChangeListener((compoundButton, b) -> prayerStatisticsDBModels.get(position).setMaghrib(b));

        // If ishaa is pressed
        chk5.setOnCheckedChangeListener((compoundButton, b) -> prayerStatisticsDBModels.get(position).setIsha(b));

        /*
         * Check each checklist to make it true or false
         */
        if(prayerStatisticsDBModels.get(position).isFajr()){
            chk1.setChecked(true);
        }
        if(prayerStatisticsDBModels.get(position).isDhuhr()){
            chk2.setChecked(true);
        }
        if(prayerStatisticsDBModels.get(position).isAsr()){
            chk3.setChecked(true);
        }
        if(prayerStatisticsDBModels.get(position).isMaghrib()){
            chk4.setChecked(true);
        }
        if(prayerStatisticsDBModels.get(position).isIsha()){
            chk5.setChecked(true);
        }

        container.addView(view, 0);
        return view;
    }

    /*
     * As we deal with live data so we need to update the current list
     */
    public void setPrayerStatisticsDBModels(List<PrayerStatisticsDBModel> prayerStatisticsDBModels){
        this.prayerStatisticsDBModels = prayerStatisticsDBModels;

        notifyDataSetChanged();
    }

    /*
     * Here is the function called to destroy an item
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
