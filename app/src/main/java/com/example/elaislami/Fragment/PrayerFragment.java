package com.example.elaislami.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elaislami.Activity.MainActivity;
import com.example.elaislami.Adapter.PrayerAdapter;
import com.example.elaislami.Adapter.SurahListAdapter;
import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.Model.SurahsModel;
import com.example.elaislami.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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



        rvPrayers=view.findViewById(R.id.prayerRV);
        prayerModels.add(new PrayerModel("Fajr","3:28 AM"));
        prayerModels.add(new PrayerModel("Duhur","7:40 PM"));
        prayerModels.add(new PrayerModel("Asr","5:00 AM"));
        prayerModels.add(new PrayerModel("Maghrib","5:00 AM"));
        prayerModels.add(new PrayerModel("Isha","5:00 AM"));

        PrayerAdapter prayerAdapter=new PrayerAdapter(prayerModels,getContext());
        rvPrayers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPrayers.setAdapter(prayerAdapter);

        return view;
    }

}