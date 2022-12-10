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

import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PrayerFragment extends Fragment {

    RecyclerView rvPrayers;
    ArrayList<PrayerModel> prayerModels=new ArrayList<>();
    TextView loc;
    public static final String PREFS_NAME = "MyPreferenceFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_prayer, container, false);

        loc = view.findViewById(R.id.loc);


        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        loc.setText(settings.getString("address", "Loading"));

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("address")){
                    loc.setText(settings.getString("address", "Loading"));
                }
            }
        };

        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);


        Date current_date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm aa");
        String result_time = formatTime.format(current_date);
        char first = result_time.charAt(0);

        if(String.valueOf(first).equals("0")){
            result_time = result_time.substring(1);
        }

       /* if(prayerModelInternal.get(position).getSalatTime().equals(result_time)){
            holder.constraintLayoutMain.setBackgroundResource(R.drawable.rounded_btn);
            holder.constraintLayoutSecondary.setBackgroundResource(R.drawable.rounded_btn);
        }
        holder.salatName.setText(prayerModelInternal.get(position).getSalatName());
        holder.salatTime.setText(prayerModelInternal.get(position).getSalatTime());*/


        return view;
    }

}