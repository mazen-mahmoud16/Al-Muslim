package com.example.elaislami.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elaislami.Activity.AzkarActivity;
import com.example.elaislami.Activity.PrayerStatisticsActivity;
import com.example.elaislami.Activity.TodoActivity;
import com.example.elaislami.R;


public class HomeFragment extends Fragment {


    private ImageView todo_btn;
    private ImageView statistics_btn;
    private ImageView azkar_btn;
    private TextView loc;
    private TextView sibhaCounter;
    private ImageButton reset;
    private ImageButton add;


    Intent intent;
    public static final String PREFS_NAME = "MyPreferenceFile";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        todo_btn = view.findViewById(R.id.img1);
        statistics_btn = view.findViewById(R.id.img2);
        azkar_btn = view.findViewById(R.id.img3);

        sibhaCounter = view.findViewById(R.id.sibha);
        reset = view.findViewById(R.id.reset);
        add = view.findViewById(R.id.add);


        loc=view.findViewById(R.id.loc);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        loc.setText(settings.getString("address", "Loading"));

        sibhaCounter.setText(String.valueOf(settings.getInt("sibha", 0)));

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("sibha",0);
                editor.commit();
                sibhaCounter.setText(String.valueOf(0));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curr_counter = Integer.parseInt(sibhaCounter.getText().toString());
                curr_counter++;
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("sibha",curr_counter);
                editor.commit();
                sibhaCounter.setText(String.valueOf(curr_counter));
            }
        });


        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("address")) {
                    loc.setText(settings.getString("address", "Loading"));
                }
            }
        };

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

        azkar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), AzkarActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}