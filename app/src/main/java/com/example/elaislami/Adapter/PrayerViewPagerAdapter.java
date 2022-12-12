package com.example.elaislami.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrayerViewPagerAdapter extends PagerAdapter {

    private List<PrayerModel> prayerModels;
    private Context context;



    public PrayerViewPagerAdapter(List<PrayerModel> models, Context context) {
        this.prayerModels = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return prayerModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.prayer_viewpager_item, container, false);

        TextView fajr_time,sunrise_time,dhuhr_time,asr_time,maghrib_time,isha_time;

        fajr_time = view.findViewById(R.id.fajr_time);
        sunrise_time = view.findViewById(R.id.sunrise_time);
        dhuhr_time = view.findViewById(R.id.dhuhr_time);
        asr_time = view.findViewById(R.id.asr_time);
        maghrib_time = view.findViewById(R.id.maghrib_time);
        isha_time = view.findViewById(R.id.isha_time);

        fajr_time.setText(prayerModels.get(position).getFajr());
        sunrise_time.setText(prayerModels.get(position).getSunrise());
        dhuhr_time.setText(prayerModels.get(position).getDhuhr());
        asr_time.setText(prayerModels.get(position).getAsr());
        maghrib_time.setText(prayerModels.get(position).getMaghrib());
        isha_time.setText(prayerModels.get(position).getIsha());

       /* Date current_date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm aa");
        String result_time = formatTime.format(current_date);
        char first = result_time.charAt(0);

        if(String.valueOf(first).equals("0")){
            result_time = result_time.substring(1);
        }

          if(prayerModelInternal.get(position).getSalatTime().equals(result_time)){
            holder.constraintLayoutMain.setBackgroundResource(R.drawable.rounded_btn);
            holder.constraintLayoutSecondary.setBackgroundResource(R.drawable.rounded_btn);
        }
        holder.salatName.setText(prayerModelInternal.get(position).getSalatName());
        holder.salatTime.setText(prayerModelInternal.get(position).getSalatTime());*/


        container.addView(view, 0);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}