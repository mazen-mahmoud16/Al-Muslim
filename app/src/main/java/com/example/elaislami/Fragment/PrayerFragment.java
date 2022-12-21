package com.example.elaislami.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/*
 * Here is prayer fragment class that is triggered when we open the second tab in the view pager
 */
public class PrayerFragment extends Fragment {

    /*
     * Text views to show location and date
     */
    private TextView tvLocation;
    private TextView tvDate;

    // View pager
    private ViewPager viewPager;
    // Adapter
    private PrayerViewPagerAdapter adapter;
    // List of prayer models
    private List<PrayerModel> prayerModels;

    /*
     * To use shared preference
     */
    public static final String PREFS_NAME = "MyPreferenceFile";
    private SharedPreferences settings;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    /*
     * Here is on create view function
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_prayer, container, false);

        /*
         * Assign text views and view pager
         */
        tvLocation = view.findViewById(R.id.loc);
        tvDate = view.findViewById(R.id.day);
        viewPager = view.findViewById(R.id.view_pager_prayer);

        /*
         * Image buttons to navigate between view pager
         */
        ImageButton prevDay = view.findViewById(R.id.prev_day);
        ImageButton nextDay = view.findViewById(R.id.next_day);

        // Assign shared preference settings
        settings = requireActivity().getSharedPreferences(PREFS_NAME, 0);


        /*
         * Listen on address (location), and current prayer change on shared preference
         */
        sharedPreferenceChangeListener = (sharedPreferences, key) -> {

            // If address is changed
            if(key.equals("address")){

                // Assign text view and location
                tvLocation.setText(settings.getString("address", "Loading"));
                double longDouble=Double.parseDouble(settings.getString("long", "0.0"));
                double latDouble=Double.parseDouble(settings.getString("lat", "0.0"));

                /*
                 * These parameters are used in object Prayer Times to calculate prayer times
                 */
                Coordinates coordinates = new Coordinates(latDouble, longDouble);
                CalculationParameters params =
                        CalculationMethod.EGYPTIAN.getParameters();
                params.madhab = Madhab.SHAFI;

                // Make an empty arraylists
                prayerModels = new ArrayList<>();
                List<Calendar> list = new ArrayList<>();

                /*
                 * Get for 2 days before, and 13 days after the prayer times
                 */
                for(int i=-2;i<13;i++){
                    list.add(Calendar.getInstance());
                    list.get(i+2).add(Calendar.DATE, +i);
                    DateComponents date = DateComponents.from(list.get(i+2).getTime());
                    PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

                    // Format it
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");

                    // Set timezone
                    formatter.setTimeZone(TimeZone.getDefault());

                    // Add in our list
                    prayerModels.add(new PrayerModel(list.get(i+2),formatter.format(prayerTimes.fajr),formatter.format(prayerTimes.sunrise),formatter.format(prayerTimes.dhuhr),formatter.format(prayerTimes.asr),formatter.format(prayerTimes.maghrib),formatter.format(prayerTimes.isha)));

                }

                // Initialize the adapter
                adapter = new PrayerViewPagerAdapter(prayerModels, getActivity(),settings.getString("currentPrayer","Loading"));

                // Set current day in the view pager
                viewPager.setCurrentItem(2);

                // Set adapter
                viewPager.setAdapter(adapter);

                /*
                 * Get the current date and assign it to the text view
                 */
                Date date_new = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
                @SuppressLint("SimpleDateFormat") String formattedDate = new SimpleDateFormat("E, d MMMM").format(date_new);
                tvDate.setText(formattedDate);

            }

            // If address changed
            if(key.equals("currentPrayer")){
                // Initialize adapter
                adapter = new PrayerViewPagerAdapter(prayerModels, getActivity(),settings.getString("currentPrayer","Loading"));
                // Set adapter and assign current day to view pager
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(2);


            }
        };

        /*
         * Get the current location from the shared preference
         */
        double longDouble=Double.parseDouble(settings.getString("long", "0.0"));
        double latDouble=Double.parseDouble(settings.getString("lat", "0.0"));

        /*
         * These parameters are used in object Prayer Times to calculate prayer times
         */
        Coordinates coordinates = new Coordinates(latDouble, longDouble);
        CalculationParameters params =
                CalculationMethod.EGYPTIAN.getParameters();
        params.madhab = Madhab.SHAFI;

        // Make empty lists
        prayerModels = new ArrayList<>();
        List<Calendar> list = new ArrayList<>();

        /*
         * Get for 2 days before, and 13 days after the prayer times
         */
        for(int i=-2;i<13;i++){
            list.add(Calendar.getInstance());
            list.get(i+2).add(Calendar.DATE, +i);
            DateComponents date = DateComponents.from(list.get(i+2).getTime());
            PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

            // Format it
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");

            // Set Timezone
            formatter.setTimeZone(TimeZone.getDefault());

            // Add prayer to the list
            prayerModels.add(new PrayerModel(list.get(i+2),formatter.format(prayerTimes.fajr),formatter.format(prayerTimes.sunrise),formatter.format(prayerTimes.dhuhr),formatter.format(prayerTimes.asr),formatter.format(prayerTimes.maghrib),formatter.format(prayerTimes.isha)));
        }

        // Initialize the adapter
        adapter = new PrayerViewPagerAdapter(prayerModels, getActivity(),settings.getString("currentPrayer","Loading"));

        // Set the adapter and view the current date
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);

        /*
         * Show the current date in the textview
         */
        Date date_new = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
        @SuppressLint("SimpleDateFormat") String formattedDate = new SimpleDateFormat("E, d MMMM").format(date_new);
        tvDate.setText(formattedDate);

        /*
         * Send to the home fragment the current day prayers
         */
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(prayerModels.get(2));
        editor.putString("prayers", json);
        editor.apply();

        /*
         * Navigate to the previous day
         */
        prevDay.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            Date date_new2 = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
            @SuppressLint("SimpleDateFormat") String formattedDate2 = new SimpleDateFormat("E, d MMMM").format(date_new2);
            tvDate.setText(formattedDate2);
        });

        /*
         * Navigate to the next day
         */
        nextDay.setOnClickListener(view1 -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            Date date_new2 = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
            @SuppressLint("SimpleDateFormat") String formattedDate2 = new SimpleDateFormat("E, d MMMM").format(date_new2);
            tvDate.setText(formattedDate2);
        });

        /*
         * Handling when user scroll to the next or previous day
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Date date_new2 = prayerModels.get(viewPager.getCurrentItem()).getDate().getTime();
                @SuppressLint("SimpleDateFormat") String formattedDate2 = new SimpleDateFormat("E, d MMMM").format(date_new2);
                tvDate.setText(formattedDate2);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    /*
     * Overriding function on Resume to register a listener to shared preference and set textview location
     */
    @Override
    public void onResume() {
        super.onResume();
        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        tvLocation.setText(settings.getString("address", "Loading"));
    }

    /*
     * Overriding function on Pause to unregister a listener to shared preference
     */
    @Override
    public void onPause() {
        super.onPause();
        settings.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}