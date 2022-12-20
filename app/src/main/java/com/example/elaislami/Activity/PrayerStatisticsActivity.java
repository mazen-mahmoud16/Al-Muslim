package com.example.elaislami.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.example.elaislami.Adapter.PrayerStatsViewPagerAdapter;
import com.example.elaislami.Adapter.PrayerViewPagerAdapter;
import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;
import com.example.elaislami.ViewModel.PrayerStatsViewModel;
import com.example.elaislami.ViewModel.SurahViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class PrayerStatisticsActivity extends AppCompatActivity {

    int backState;
    ImageButton back_btn;
    ProgressBar progressBar;
    Button generate;
    TextView date,numberSalat;
    ViewPager viewPager;
    ImageButton prevDay;
    ImageButton nextDay;
    PrayerStatsViewPagerAdapter adapter;
    List<PrayerStatisticsDBModel> prayerStatisticsDBModels= new ArrayList<>();

    PrayerStatsViewModel prayerStatsViewModel;

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    public static final String PREFS_NAME = "MyPreferenceFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_statistics);

        settings = getSharedPreferences(PREFS_NAME, 0);
        editor =settings.edit();
        editor.putString("date_week", "Sun, 11 December");
        editor.commit();

        viewPager = findViewById(R.id.view_pager_prayer_stats);
        prevDay = findViewById(R.id.prev_day);
        nextDay = findViewById(R.id.next_day);
        date = findViewById(R.id.day_stats);


        backState = getIntent().getIntExtra("back_state", -1);

        Toolbar toolbar = findViewById(R.id.tool_bar_prayer);

        setSupportActionBar(toolbar);

        back_btn = findViewById(R.id.back_btn);
        prayerStatsViewModel = new ViewModelProvider(this).get(PrayerStatsViewModel.class);
        adapter = new PrayerStatsViewPagerAdapter(prayerStatisticsDBModels, PrayerStatisticsActivity.this);
        viewPager.setAdapter(adapter);

        prayerStatsViewModel.getAllPrayerStatsList().observe(this, new Observer<List<PrayerStatisticsDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<PrayerStatisticsDBModel> prayerStatistics) {
                // Update the cached copy of the words in the adapter.
                adapter.setPrayerStatisticsDBModels(prayerStatistics);
                prayerStatisticsDBModels = prayerStatistics;


            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrayerStatisticsActivity.this, MainActivity.class);
                i.putExtra("back_state", 0);
                startActivity(i);

            }
        });

        generate = findViewById(R.id.generate);

        progressBar = findViewById(R.id.prog_bar);

        numberSalat = findViewById(R.id.num_salat);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* numberSalat.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                numberSalat.setText("20/35");
                ObjectAnimator.ofInt(progressBar, "progress", 20)
                        .setDuration(1000)
                        .start();*/
            }
        });

        getPosition();

        prevDay.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            date.setText(prayerStatisticsDBModels.get(viewPager.getCurrentItem()).getDate());

        });

        nextDay.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            date.setText(prayerStatisticsDBModels.get(viewPager.getCurrentItem()).getDate());

        });


/*
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("keyy",prayerStatisticsDBModels.size()+"");
                if(!(prayerStatisticsDBModels.size()==0)){
                    date.setText(prayerStatisticsDBModels.get(position).getDate());
                }else {
                    date.setText("hhh");
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

 */
    }

    public void getPosition(){
        SimpleDateFormat formatter = new SimpleDateFormat("E, d MMMM");
        settings.getString("date_week", "Loading");

        String stringDate=settings.getString("date_week","Loading");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR));

        Calendar currentTime=Calendar.getInstance();

        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);

        currentTime.set(Calendar.HOUR_OF_DAY, 0);
        currentTime.set(Calendar.MINUTE, 0);
        currentTime.set(Calendar.SECOND, 0);
        currentTime.set(Calendar.MILLISECOND, 0);

        Log.d("jj",currentTime.getTime().toString());
        Log.d("jj",calender.getTime().toString());

        long days=0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             days = Duration.between(calender.toInstant(),currentTime.toInstant()).toDays();
        }
        Log.d("jj",days+"");

        if(days>=7)
        {
            Log.d("jj","ok");
            prayerStatsViewModel.deleteAllPrayer();
            for(int i=0;i<6;i++){

                Calendar current=Calendar.getInstance();
                current.add(Calendar.DATE,i);

                SimpleDateFormat format1 = new SimpleDateFormat("E, d MMMM");

                prayerStatsViewModel.insertPrayer(new PrayerStatisticsDBModel(format1.format(current.getTime()), false,
                        false, false, false, false));

            }

        }

    }

}