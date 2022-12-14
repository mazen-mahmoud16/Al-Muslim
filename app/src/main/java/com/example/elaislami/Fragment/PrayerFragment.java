package com.example.elaislami.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.Madhab;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.example.elaislami.Adapter.PrayerViewPagerAdapter;
import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;
import com.example.elaislami.Receiver.LocationReceiver;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class PrayerFragment extends Fragment {

    TextView loc;
    ViewPager viewPager;
    PrayerViewPagerAdapter adapter;
    List<PrayerModel> prayerModels;
    ImageButton prevDay;
    ImageButton nextDay;
    TextView tv_date;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    public static final String PREFS_NAME = "MyPreferenceFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_prayer, container, false);

        loc = view.findViewById(R.id.loc);

        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        loc.setText(settings.getString("address", "Loading"));


        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("address")){
                    loc.setText(settings.getString("address", "Loading"));

                    double longDouble=Double.parseDouble(settings.getString("long", "0.0"));
                    double latDouble=Double.parseDouble(settings.getString("lat", "0.0"));

                    tv_date = view.findViewById(R.id.day);


                    Coordinates coordinates = new Coordinates(latDouble, longDouble);

                    CalculationParameters params =
                            CalculationMethod.EGYPTIAN.getParameters();
                    params.madhab = Madhab.SHAFI;


                    prayerModels = new ArrayList<>();


                    List<Calendar> list = new ArrayList<>();


                    for(int i=-2;i<13;i++){
                        list.add(Calendar.getInstance());
                        list.get(i+2).add(Calendar.DATE, +i);

                        DateComponents date = DateComponents.from(list.get(i+2).getTime());

                        PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");

                        formatter.setTimeZone(TimeZone.getDefault());

                        prayerModels.add(new PrayerModel(list.get(i+2),formatter.format(prayerTimes.fajr),formatter.format(prayerTimes.sunrise),formatter.format(prayerTimes.dhuhr),formatter.format(prayerTimes.asr),formatter.format(prayerTimes.maghrib),formatter.format(prayerTimes.isha)));


                    }


                    adapter = new PrayerViewPagerAdapter(prayerModels, getActivity(),settings.getString("currentPrayer","Loading"));
                    viewPager.setCurrentItem(2);


                    viewPager = view.findViewById(R.id.view_pager_prayer);
                    viewPager.setAdapter(adapter);


                    prevDay = view.findViewById(R.id.prev_day);
                    nextDay = view.findViewById(R.id.next_day);

                    viewPager.setCurrentItem(2);

                    Date date_new = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
                    String formattedDate = new SimpleDateFormat("E, d MMMM").format(date_new);

                    tv_date.setText(formattedDate);

                }
                if(key.equals("currentPrayer")){
                    adapter = new PrayerViewPagerAdapter(prayerModels, getActivity(),settings.getString("currentPrayer","Loading"));
                    viewPager = view.findViewById(R.id.view_pager_prayer);
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(2);


                }
            }
        };

        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

        double longDouble=Double.parseDouble(settings.getString("long", "0.0"));
        double latDouble=Double.parseDouble(settings.getString("lat", "0.0"));


        tv_date = view.findViewById(R.id.day);


        Coordinates coordinates = new Coordinates(latDouble, longDouble);

        CalculationParameters params =
                CalculationMethod.EGYPTIAN.getParameters();
        params.madhab = Madhab.SHAFI;


        prayerModels = new ArrayList<>();


        List<Calendar> list = new ArrayList<>();


        for(int i=-2;i<13;i++){
            list.add(Calendar.getInstance());
            list.get(i+2).add(Calendar.DATE, +i);

            DateComponents date = DateComponents.from(list.get(i+2).getTime());

            PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");

            formatter.setTimeZone(TimeZone.getDefault());

            prayerModels.add(new PrayerModel(list.get(i+2),formatter.format(prayerTimes.fajr),formatter.format(prayerTimes.sunrise),formatter.format(prayerTimes.dhuhr),formatter.format(prayerTimes.asr),formatter.format(prayerTimes.maghrib),formatter.format(prayerTimes.isha)));
        }

        adapter = new PrayerViewPagerAdapter(prayerModels, getActivity(),settings.getString("currentPrayer","Loading"));

        viewPager = view.findViewById(R.id.view_pager_prayer);
        viewPager.setAdapter(adapter);


        prevDay = view.findViewById(R.id.prev_day);
        nextDay = view.findViewById(R.id.next_day);

        viewPager.setCurrentItem(2);

        Date date_new = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
        String formattedDate = new SimpleDateFormat("E, d MMMM").format(date_new);

        tv_date.setText(formattedDate);

        editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(prayerModels.get(7));
        editor.putString("prayers", json);
        editor.commit();

        prevDay.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            Date date_new2 = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
            String formattedDate2 = new SimpleDateFormat("E, d MMMM").format(date_new2);

            tv_date.setText(formattedDate2);
        });

        nextDay.setOnClickListener(view1 -> {

            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            Date date_new2 = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
            String formattedDate2 = new SimpleDateFormat("E, d MMMM").format(date_new2);
            tv_date.setText(formattedDate2);
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Date date_new2 = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
                String formattedDate2 = new SimpleDateFormat("E, d MMMM").format(date_new2);

                tv_date.setText(formattedDate2);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      /*  Date current_date = new Date();
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

        return view;
    }

}