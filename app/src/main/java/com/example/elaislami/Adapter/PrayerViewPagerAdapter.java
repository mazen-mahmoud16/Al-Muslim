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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PrayerViewPagerAdapter extends PagerAdapter {

    private final List<PrayerModel> prayerModels;
    private final Context context;
    private final String currentPrayer;

    public PrayerViewPagerAdapter(List<PrayerModel> models, Context context, String currentPrayer) {
        this.prayerModels = models;
        this.context = context;
        this.currentPrayer=currentPrayer;
    }

    @Override
    public int getCount() {
        return prayerModels.size();
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
        View view = layoutInflater.inflate(R.layout.prayer_viewpager_item, container, false);

        TextView fajr_time,sunrise_time,dhuhr_time,asr_time,maghrib_time,isha_time;
        ConstraintLayout fajr,dhuhr,asr,maghrib,isha;

        fajr_time = view.findViewById(R.id.fajr_time);
        sunrise_time = view.findViewById(R.id.sunrise_time);
        dhuhr_time = view.findViewById(R.id.dhuhr_time);
        asr_time = view.findViewById(R.id.asr_time);
        maghrib_time = view.findViewById(R.id.maghrib_time);
        isha_time = view.findViewById(R.id.isha_time);

        fajr=view.findViewById(R.id.salat_container);
        dhuhr=view.findViewById(R.id.salat_container2);
        asr=view.findViewById(R.id.salat_container3);
        maghrib=view.findViewById(R.id.salat_container4);
        isha=view.findViewById(R.id.salat_container5);

        if(position==2){
            if(currentPrayer.equals("Fajr")){
                fajr.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
            }else if(currentPrayer.equals("Asr")){
                asr.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
            }else if(currentPrayer.equals("Dhuhr")){
                dhuhr.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
            }else if(currentPrayer.equals("Maghrib")){
                maghrib.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
            }else if(currentPrayer.equals("Isha")){
                isha.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
            }
        }

        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

        String dateInFormat = prayerModels.get(position).getFajr().substring(0,5);
        String AoP= prayerModels.get(position).getFajr().substring(6,8);

        Date date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDateString = formatter2.format(date);

        fajr_time.setText(formattedDateString+" "+AoP);


        dateInFormat = prayerModels.get(position).getSunrise().substring(0,5);
         AoP= prayerModels.get(position).getSunrise().substring(6,8);

         date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formattedDateString = formatter2.format(date);

        sunrise_time.setText(formattedDateString+" "+AoP);


        dateInFormat = prayerModels.get(position).getDhuhr().substring(0,5);
        AoP= prayerModels.get(position).getDhuhr().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formattedDateString = formatter2.format(date);

        dhuhr_time.setText(formattedDateString+" "+AoP);

        dateInFormat = prayerModels.get(position).getAsr().substring(0,5);
        AoP= prayerModels.get(position).getAsr().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formattedDateString = formatter2.format(date);

        asr_time.setText(formattedDateString+" "+AoP);

        dateInFormat = prayerModels.get(position).getMaghrib().substring(0,5);
        AoP= prayerModels.get(position).getMaghrib().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formattedDateString = formatter2.format(date);

        maghrib_time.setText(formattedDateString+" "+AoP);

        dateInFormat = prayerModels.get(position).getIsha().substring(0,5);
        AoP= prayerModels.get(position).getIsha().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formattedDateString = formatter2.format(date);

        isha_time.setText(formattedDateString+" "+AoP);


        container.addView(view, 0);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}