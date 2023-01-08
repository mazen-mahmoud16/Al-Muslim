package com.almuslim.elaislami.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import com.almuslim.elaislami.Adapter.PrayerStatsViewPagerAdapter;
import com.almuslim.elaislami.R;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.PrayerStatisticsDBModel;
import com.almuslim.elaislami.RoomDBManager.ViewModel.PrayerStatsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

/*
 * Here is prayer statistics activity that is triggered when we choose prayer statistics option from home fragment
 */
public class PrayerStatisticsActivity extends AppCompatActivity {

    /*
     * Edit texts, image buttons, recycler view and buttons used
     */
    private ImageButton imgBackBtn;
    private ProgressBar progressBar;
    private Button btnGenerate, btnSave;
    private TextView tvDate, tvNumberSalat;
    private ViewPager viewPager;
    private ImageButton imgPrevDayBtn;
    private ImageButton imgNextDayBtn;
    private Toolbar toolbar;

    // Declare adapter and empty list to be passed to adapter
    private PrayerStatsViewPagerAdapter adapter;
    private List<PrayerStatisticsDBModel> prayerStatisticsDBModels= new ArrayList<>();

    // Declare view model
    private PrayerStatsViewModel prayerStatsViewModel;

    /*
     * To use shared preference
     */
    public static final String PREFS_NAME = "MyPreferenceFile";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    /*
     * Here is on create function
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_statistics);

        // Initialize shared preference
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor =settings.edit();

        /*
         * Assign elements in layout
         */
        btnSave =findViewById(R.id.save);
        viewPager = findViewById(R.id.view_pager_prayer_stats);
        imgPrevDayBtn = findViewById(R.id.prev_day);
        imgNextDayBtn = findViewById(R.id.next_day);
        tvDate = findViewById(R.id.day_stats);
        imgBackBtn = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.tool_bar_prayer);
        btnGenerate = findViewById(R.id.generate);
        progressBar = findViewById(R.id.progress_bar);
        tvNumberSalat = findViewById(R.id.num_salat);

        // Set toolbar
        setSupportActionBar(toolbar);

        /*
         * Initialize view model and adapter and set adapter to view pager
         */
        prayerStatsViewModel = new ViewModelProvider(this).get(PrayerStatsViewModel.class);
        adapter = new PrayerStatsViewPagerAdapter(prayerStatisticsDBModels, PrayerStatisticsActivity.this);
        viewPager.setAdapter(adapter);

        /*
         * Observe on the live data change
         */
        prayerStatsViewModel.getAllPrayerStatsList().observe(this, prayerStatistics -> {
            adapter.setPrayerStatisticsDBModels(prayerStatistics);
            prayerStatisticsDBModels = prayerStatistics;
            initViewPager();
            getPosition();
        });

        /*
         * Handle when user clicks back
         */
        imgBackBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        /*
         * Handle when user clicks generate to generate previous week report
         */
        btnGenerate.setOnClickListener(view -> {

            // Animate progress bar
            int prayerCounter =settings.getInt("prayer_counter",0);
            tvNumberSalat.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            tvNumberSalat.setText(prayerCounter+getString(R.string.out_of));
            ObjectAnimator.ofInt(progressBar, "progress", prayerCounter)
                    .setDuration(300)
                    .start();
        });

        /*
         * Handles when user wants to view previous item in view pager
         */
        imgPrevDayBtn.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            tvDate.setText(prayerStatisticsDBModels.get(viewPager.getCurrentItem()).getDate());
        });

        /*
         * Handles when user wants to view next item in view pager
         */
        imgNextDayBtn.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            tvDate.setText(prayerStatisticsDBModels.get(viewPager.getCurrentItem()).getDate());

        });

        /*
         * Handles when user clicks save
         */
        btnSave.setOnClickListener(view -> {
            for (PrayerStatisticsDBModel prayerStatisticsDBModel: prayerStatisticsDBModels)
            {
                prayerStatsViewModel.updatePrayerStatistics(prayerStatisticsDBModel);
            }
            Toasty.success(PrayerStatisticsActivity.this,"Saved Successfully",Toasty.LENGTH_SHORT,true).show();
        });

        /*
         * Listen on changes on view pager
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(!prayerStatisticsDBModels.isEmpty()){
                    tvDate.setText(prayerStatisticsDBModels.get(viewPager.getCurrentItem()).getDate());
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

    /*
     * Show current day in view pager
     */
    public void initViewPager(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("E, d MMMM");
        Calendar currentTime=Calendar.getInstance();

        switch (currentTime.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:
                viewPager.setCurrentItem(0);
                break;
            case Calendar.MONDAY:
                viewPager.setCurrentItem(1);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(2);
                break;
            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(3);
                break;
            case Calendar.THURSDAY:
                viewPager.setCurrentItem(4);
                break;
            case Calendar.FRIDAY:
                viewPager.setCurrentItem(5);
                break;
            case Calendar.SATURDAY:
                viewPager.setCurrentItem(6);
                break;
        }

        // Set date in textview
        tvDate.setText(formatter.format(currentTime.getTime()));
    }

    /*
     * Check whether Sunday has come or not, and if yes generate previous week report and reset database
     */
    public void getPosition(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("E, d MMMM");

        // Get last Sunday date
        editor=settings.edit();
        String stringDate=settings.getString("date_week","xxx");

        // Get number of days between last sunday and today
        long days=0;

        Calendar currentTime=Calendar.getInstance();

        // Declare a new calendar with the current date and make it point to last sunday
        Calendar current=Calendar.getInstance();

        /*
         * Initially when downloading the application, the date will be xxx
         */
        if(!stringDate.equals("xxx")){
            /*
             * The following code is to compare number of days between today and the last sunday
             */
            Date date = null;
            try {
                date = dateFormatter.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            /*
             * Parse dates to calendar to compare dates
             */
            Calendar lastSundayDate = Calendar.getInstance();
            assert date != null;
            lastSundayDate.setTime(date);
            lastSundayDate.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR));



            /*
             * Ignore time in calendar
             */
            lastSundayDate.set(Calendar.HOUR_OF_DAY, 0);
            lastSundayDate.set(Calendar.MINUTE, 0);
            lastSundayDate.set(Calendar.SECOND, 0);
            lastSundayDate.set(Calendar.MILLISECOND, 0);

            currentTime.set(Calendar.HOUR_OF_DAY, 0);
            currentTime.set(Calendar.MINUTE, 0);
            currentTime.set(Calendar.SECOND, 0);
            currentTime.set(Calendar.MILLISECOND, 0);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                days = Duration.between(lastSundayDate.toInstant(),currentTime.toInstant()).toDays();
            }


        }


        // A week is passed
        if(days>=7 || stringDate.equals("xxx") || days<0)
        {
            while(current.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
                current.add(Calendar.DATE,-1);
            }

            /*
            Compute the previous week report
             */
            int prayerCounter=0;
            for (PrayerStatisticsDBModel prayerStatisticsDBModel:prayerStatisticsDBModels) {
                if (prayerStatisticsDBModel.isFajr()) {
                    prayerCounter++;
                }
                if (prayerStatisticsDBModel.isDhuhr()) {
                    prayerCounter++;
                }
                if (prayerStatisticsDBModel.isAsr()) {
                    prayerCounter++;
                }
                if (prayerStatisticsDBModel.isMaghrib()) {
                    prayerCounter++;
                }
                if (prayerStatisticsDBModel.isIsha()) {
                    prayerCounter++;
                }
            }

            // Put it in a shared preference
            editor.putInt("prayer_counter",prayerCounter);

            // Delete all table
            prayerStatsViewModel.deleteAllPrayerStatistics();

            // Put last Sunday date in a shared preference
            editor.putString("date_week",dateFormatter.format(current.getTime()));
            editor.commit();
            editor=settings.edit();

            // Insert in the database all new week starting from sunday
            for(int i=0;i<=6;i++){
                prayerStatsViewModel.insertPrayerStatistics(new PrayerStatisticsDBModel(dateFormatter.format(current.getTime()), false,
                        false, false, false, false));
                current.add(Calendar.DATE,1);
            }

            // Declare view model and set adapter to view pager
            prayerStatsViewModel = new ViewModelProvider(this).get(PrayerStatsViewModel.class);
            adapter = new PrayerStatsViewPagerAdapter(prayerStatisticsDBModels, PrayerStatisticsActivity.this);
            viewPager.setAdapter(adapter);

            /*
             * Restarting activity to generate new values
             */
            Intent intent = getIntent();
            Toasty.info(PrayerStatisticsActivity.this, "Restarting the page as a new week is started", Toasty.LENGTH_LONG, true).show();
            finish();
            startActivity(intent);


        }
    }

}