package com.example.elaislami.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.Madhab;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class PrayerFragment extends Fragment {

    TextView loc;
    TextView fajrTime;
    TextView duhurTime;
    TextView asrTime;
    TextView maghribTime;
    TextView ishaTime;

    public static final String PREFS_NAME = "MyPreferenceFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_prayer, container, false);

        loc = view.findViewById(R.id.loc);
        fajrTime = view.findViewById(R.id.fajr_time);
        duhurTime = view.findViewById(R.id.duhur_time);
        asrTime = view.findViewById(R.id.asr_time);
        maghribTime = view.findViewById(R.id.maghrib_time);
        ishaTime = view.findViewById(R.id.isha_time);

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        loc.setText(settings.getString("address", "Loading"));

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("address")){
                    loc.setText(settings.getString("address", "Loading"));

                    double longDouble=Double.parseDouble(settings.getString("long", "0.0"));
                    double latDouble=Double.parseDouble(settings.getString("lat", "0.0"));

                    Coordinates coordinates = new Coordinates(latDouble, longDouble);

                    DateComponents date = DateComponents.from(new Date());

                    CalculationParameters params =
                            CalculationMethod.EGYPTIAN.getParameters();
                    params.madhab = Madhab.HANAFI;

                    PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);
                }
            }
        };

        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

        double longDouble=Double.parseDouble(settings.getString("long", "0.0"));
        double latDouble=Double.parseDouble(settings.getString("lat", "0.0"));

        Coordinates coordinates = new Coordinates(latDouble, longDouble);

        DateComponents date = DateComponents.from(new Date());

        CalculationParameters params =
                CalculationMethod.EGYPTIAN.getParameters();
        params.madhab = Madhab.HANAFI;

        PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");

        formatter.setTimeZone(TimeZone.getDefault());

        fajrTime.setText(formatter.format(prayerTimes.fajr));
        duhurTime.setText(formatter.format(prayerTimes.dhuhr));
        asrTime.setText(formatter.format(prayerTimes.asr));
        maghribTime.setText(formatter.format(prayerTimes.maghrib));
        ishaTime.setText(formatter.format(prayerTimes.isha));


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