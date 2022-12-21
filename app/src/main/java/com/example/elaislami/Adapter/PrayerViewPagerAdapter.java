package com.example.elaislami.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 * This is the ayah list adapter for the view pager
 */
public class PrayerViewPagerAdapter extends PagerAdapter {

    /*
     * attributes for adapter
     */
    private final List<PrayerModel> prayerModels;
    private final Context context;
    private final String currentPrayer;

    // Here is the constructor
    public PrayerViewPagerAdapter(List<PrayerModel> models, Context context, String currentPrayer) {
        this.prayerModels = models;
        this.context = context;
        this.currentPrayer=currentPrayer;
    }

    /*
     * Get size of ayah array
     */
    @Override
    public int getCount() {
        return prayerModels.size();
    }

    /*
     * Get if view is from object or not
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    /*
     * Here is the function to instantiate items
     */
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.prayer_viewpager_item, container, false);

        /*
         * Declare and get all text views and constraint layouts
         */
        TextView fajrTime,sunriseTime,dhuhrTime,asrTime,maghribTime,ishaTime;
        ConstraintLayout fajr,dhuhr,asr,maghrib,isha;

        fajrTime = view.findViewById(R.id.fajr_time);
        sunriseTime = view.findViewById(R.id.sunrise_time);
        dhuhrTime = view.findViewById(R.id.dhuhr_time);
        asrTime = view.findViewById(R.id.asr_time);
        maghribTime = view.findViewById(R.id.maghrib_time);
        ishaTime = view.findViewById(R.id.isha_time);

        fajr=view.findViewById(R.id.salat_container);
        dhuhr=view.findViewById(R.id.salat_container2);
        asr=view.findViewById(R.id.salat_container3);
        maghrib=view.findViewById(R.id.salat_container4);
        isha=view.findViewById(R.id.salat_container5);

        /*
         * Check current position
         */
        if(position==2){
            switch (currentPrayer) {
                case "Fajr":
                    fajr.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
                    break;
                case "Asr":
                    asr.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
                    break;
                case "Dhuhr":
                    dhuhr.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
                    break;
                case "Maghrib":
                    maghrib.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
                    break;
                case "Isha":
                    isha.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn));
                    break;
            }
        }

        /*
         * Define formatter to change from 24 hour format to 12 hour in all 5 salat
         */
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        String dateInFormat = prayerModels.get(position).getFajr().substring(0,5);
        String AoP= prayerModels.get(position).getFajr().substring(6,8);

        Date date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        String formattedDateString = formatter2.format(date);

        fajrTime.setText(formattedDateString+" "+AoP);


        dateInFormat = prayerModels.get(position).getSunrise().substring(0,5);
         AoP= prayerModels.get(position).getSunrise().substring(6,8);

         date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        formattedDateString = formatter2.format(date);

        sunriseTime.setText(formattedDateString+" "+AoP);


        dateInFormat = prayerModels.get(position).getDhuhr().substring(0,5);
        AoP= prayerModels.get(position).getDhuhr().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        formattedDateString = formatter2.format(date);

        dhuhrTime.setText(formattedDateString+" "+AoP);

        dateInFormat = prayerModels.get(position).getAsr().substring(0,5);
        AoP= prayerModels.get(position).getAsr().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        formattedDateString = formatter2.format(date);

        asrTime.setText(formattedDateString+" "+AoP);

        dateInFormat = prayerModels.get(position).getMaghrib().substring(0,5);
        AoP= prayerModels.get(position).getMaghrib().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        formattedDateString = formatter2.format(date);

        maghribTime.setText(formattedDateString+" "+AoP);

        dateInFormat = prayerModels.get(position).getIsha().substring(0,5);
        AoP= prayerModels.get(position).getIsha().substring(6,8);

        date = null;
        try {
            date = formatter2.parse(dateInFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        formattedDateString = formatter2.format(date);

        ishaTime.setText(formattedDateString+" "+AoP);


        container.addView(view, 0);
        return view;
    }

    /*
     * Here is the function called to destroy an item
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}