package com.example.elaislami.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;

import java.util.List;
import java.util.Locale;

public class PrayerStatsViewPagerAdapter extends PagerAdapter {

    private List<PrayerStatisticsDBModel> prayerStatisticsDBModels;
    private final Context context;

    public PrayerStatsViewPagerAdapter(List<PrayerStatisticsDBModel> models, Context context) {

        this.prayerStatisticsDBModels = models;
        Log.d("hiii",   this.prayerStatisticsDBModels.size()+"");

        this.context = context;

    }

    @Override
    public int getCount() {
        return prayerStatisticsDBModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.prayer_stats_viewpager_item, container, false);

        CheckBox chk1,chk2,chk3,chk4,chk5;

        chk1 = view.findViewById(R.id.chk1);
        chk2 = view.findViewById(R.id.chk2);
        chk3 = view.findViewById(R.id.chk3);
        chk4 = view.findViewById(R.id.chk4);
        chk5 = view.findViewById(R.id.chk5);

        chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prayerStatisticsDBModels.get(position).setFajr(b);
            }
        });

        chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prayerStatisticsDBModels.get(position).setDhuhr(b);
            }
        });

        chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prayerStatisticsDBModels.get(position).setAsr(b);
            }
        });

        chk4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prayerStatisticsDBModels.get(position).setMaghrib(b);
            }
        });

        chk5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prayerStatisticsDBModels.get(position).setIsha(b);
            }
        });
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

    public void setPrayerStatisticsDBModels(List<PrayerStatisticsDBModel> prayerStatisticsDBModels){
        this.prayerStatisticsDBModels = prayerStatisticsDBModels;

        notifyDataSetChanged();
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
