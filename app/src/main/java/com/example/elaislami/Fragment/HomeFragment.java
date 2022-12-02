package com.example.elaislami.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.elaislami.Activity.PrayerStatisticsActivity;
import com.example.elaislami.Activity.TodoActivity;
import com.example.elaislami.R;


public class HomeFragment extends Fragment {


    private ImageView todo_btn;
    private ImageView statistics_btn;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        todo_btn = view.findViewById(R.id.img1);
        statistics_btn = view.findViewById(R.id.img2);



        todo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), TodoActivity.class);
                startActivity(intent);
            }
        });

        statistics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), PrayerStatisticsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}