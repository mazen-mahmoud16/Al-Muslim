package com.example.elaislami.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    RecyclerView rvPrayers;
    ArrayList<PrayerModel> prayerModels=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_prayer, container, false);
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