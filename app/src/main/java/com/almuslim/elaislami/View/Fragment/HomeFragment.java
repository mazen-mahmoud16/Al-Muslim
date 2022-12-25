package com.almuslim.elaislami.View.Fragment;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.almuslim.elaislami.Model.PrayerModel;
import com.almuslim.elaislami.R;
import com.almuslim.elaislami.View.Activity.AzkarActivity;
import com.almuslim.elaislami.View.Activity.PrayerStatisticsActivity;
import com.almuslim.elaislami.View.Activity.TasabehActivity;
import com.almuslim.elaislami.View.Activity.TodoActivity;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
 * Here is Home fragment class that is triggered when we open the first tab in the view pager
 */
public class HomeFragment extends Fragment {

    // Image views to icons of horizontal scroll view
    private ImageView imgStatistics,imgTasabeeh,imgAzkar,imgTodo;

    // Text Views to show location, prayer name, prayer time, prayer count down, sibha counter
    private TextView tvLocation, tvPrayerName, tvPrayerTime, tvPrayerCountdown, tvSibhaCounter;

    // Image views to icons of reset and add butons
    private ImageButton tvResetSibha,tvIncrementSibha;

    // To store the current prayer got from shared preferences from prayer fragment to check whether the current prayer changed or not
    private String currentPrayer = "";

    // Intent to go through other activities
    private Intent intent;

    /*
     * To use shared preference
     */
    public static final String PREFS_NAME = "MyPreferenceFile";
    SharedPreferences settings;
    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    SharedPreferences.Editor editor;

    // To store the prayer times of the current day
    Double fajrTime,dhuhrTime,asrTime,maghribTime,ishaTime, currentTime;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mainFormatter = new SimpleDateFormat("HH:mm aa");
    SimpleDateFormat secondaryFormatter = new SimpleDateFormat("hh:mm", Locale.ENGLISH);



    /*
     * Here is on create view function
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*
         * Assign Image views
         */
        imgTodo = view.findViewById(R.id.img_todo);
        imgStatistics = view.findViewById(R.id.img_prayer_stat);
        imgAzkar = view.findViewById(R.id.img_azkar);
        imgTasabeeh = view.findViewById(R.id.img_tasabeeh);

        /*
         * Assign Text views
         */
        tvPrayerName = view.findViewById(R.id.salat);
        tvPrayerTime = view.findViewById(R.id.salat_tim);
        tvPrayerCountdown =view.findViewById(R.id.counter);
        tvSibhaCounter = view.findViewById(R.id.sibha);
        tvResetSibha = view.findViewById(R.id.reset);
        tvIncrementSibha = view.findViewById(R.id.add);
        tvLocation =view.findViewById(R.id.loc);

        // Assign shared preference settings
        settings = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        // Setting sibha counter by the value stored in the shared preferences
        tvSibhaCounter.setText(String.valueOf(settings.getInt("sibha", 0)));

        // Initialize Gson to be used in getting the current day prayers from shared preference coming from prayer fragment
        Gson gson = new Gson();
        editor=settings.edit();

        /*
         * Initializing and running new thread
         */
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void run() {

                /*
                 * Getting the current day prayer times
                 */
                String json = settings.getString("prayers", "Loading");
                PrayerModel todaysPrayers = gson.fromJson(json, PrayerModel.class);

                // Checking if the object obtained equals null or not
                if (todaysPrayers != null)
                {
                    currentPrayer = settings.getString("currentPrayer", "Loading");

                    // Getting the current date and time
                    Date current_date = new Date();

                    // Formatting today's time
                    String result_time = mainFormatter.format(current_date);

                    /*
                     * Calling prayerTimeGenerator method and pass with it the string current time or the string prayer time (in 24-hour format)
                     * to convert the string time into double format to be used in comparison easily later in the if statements
                     */
                    currentTime = prayerTimeGenerator(result_time);
                    fajrTime=prayerTimeGenerator(todaysPrayers.getFajr());
                    dhuhrTime=prayerTimeGenerator(todaysPrayers.getDhuhr());
                    asrTime=prayerTimeGenerator(todaysPrayers.getAsr());
                    maghribTime=prayerTimeGenerator(todaysPrayers.getMaghrib());
                    ishaTime=prayerTimeGenerator(todaysPrayers.getIsha());

                    // get the current time without taking AM or PM to be used later in the count down function
                    result_time=result_time.substring(0,5);

                    // Initialize the prayerTime to be assigned later with the string time of the upcoming prayer time
                    String prayerTime="";

                    /*
                     * Checking whether the current time is between ishaa time and fajr time or not
                     */
                    if ((currentTime <= fajrTime && currentTime < ishaTime) || (currentTime > fajrTime && currentTime > ishaTime))
                    {
                        // Checking whether the current shared preferences value changed or not
                        if(!currentPrayer.equals("Fajr"))
                        {
                            editor.putString("currentPrayer", "Fajr");
                            editor.commit();
                        }

                        // Checking whether the prayer time before 10 AM or not to see if there is 0 at the beginning of the string or not
                        if(fajrTime<=9)
                        {
                            if(Double.parseDouble(fajrTime.toString().substring(2,4))==0 &&fajrTime.toString().length()==4){
                                prayerTime = "0"+ fajrTime.toString().charAt(0)+":"+"0"+fajrTime.toString().charAt(2);

                            }else{
                                prayerTime = "0"+ fajrTime.toString().charAt(0)+":"+fajrTime.toString().substring(2,4);

                            }

                        }
                        else
                        {
                            if(Double.parseDouble(fajrTime.toString().substring(2,4))<=0){
                                prayerTime = fajrTime.toString().substring(0, 2)+":"+"0"+fajrTime.toString().substring(3);

                            }else{
                                prayerTime = fajrTime.toString().substring(0, 2)+":"+fajrTime.toString().substring(3, 5);

                            }

                        }
                        tvPrayerName.setText("Fajr");
                        prayerTimeSetter(todaysPrayers.getFajr());
                    }

                    /*
                     * Checking whether the current time is between fajr time and dhuhr time or not
                     */
                    else if (currentTime > fajrTime && currentTime <= dhuhrTime)
                    {
                        // Checking whether the current shared preferences value changed or not
                        if(!currentPrayer.equals("Dhuhr"))
                        {
                            editor.putString("currentPrayer", "Dhuhr");
                            editor.commit();
                        }

                        // Checking whether the prayer time before 10 AM or not to see if there is 0 at the beginning of the string or not
                        if(asrTime<=9)
                        {
                            if(Double.parseDouble(dhuhrTime.toString().substring(2,4))==0 &&dhuhrTime.toString().length()==4){
                                prayerTime = "0"+ dhuhrTime.toString().charAt(0)+":"+"0"+dhuhrTime.toString().charAt(2);

                            }else{
                                prayerTime = "0"+ dhuhrTime.toString().charAt(0)+":"+dhuhrTime.toString().substring(2,4);

                            }

                        }
                        else
                        {
                            if(Double.parseDouble(dhuhrTime.toString().substring(2,4))==0 &&dhuhrTime.toString().length()==4){
                                prayerTime = dhuhrTime.toString().substring(0, 2)+":"+"0"+dhuhrTime.toString().substring(3);

                            }else{
                                prayerTime = dhuhrTime.toString().substring(0, 2)+":"+dhuhrTime.toString().substring(3, 5);

                            }

                        }
                        tvPrayerName.setText("Dhuhr");
                        prayerTimeSetter(todaysPrayers.getDhuhr());
                    }

                    /*
                     * Checking whether the current time is between dhuhr time and asr time or not
                     */
                    else if (currentTime > dhuhrTime && currentTime <= asrTime) {

                        // Checking whether the current shared preferences value changed or not
                        if(!currentPrayer.equals("Asr")) {
                            editor.putString("currentPrayer", "Asr");
                            editor.commit();
                        }

                        // Checking whether the prayer time before 10 AM or not to see if there is 0 at the beginning of the string or not
                        if(asrTime<=9)
                        {
                            if(Double.parseDouble(asrTime.toString().substring(2,4))==0 &&asrTime.toString().length()==4){
                                prayerTime = "0"+ asrTime.toString().charAt(0)+":"+"0"+asrTime.toString().charAt(2);

                            }else{
                                prayerTime = "0"+ asrTime.toString().charAt(0)+":"+asrTime.toString().substring(2,4);

                            }

                        }
                        else
                        {
                            if(Double.parseDouble(asrTime.toString().substring(2,4))==0 &&asrTime.toString().length()==4){
                                prayerTime = asrTime.toString().substring(0, 2)+":"+"0"+asrTime.toString().substring(3);

                            }else{
                                prayerTime = asrTime.toString().substring(0, 2)+":"+asrTime.toString().substring(3, 5);

                            }

                        }

                        tvPrayerName.setText("Asr");
                        prayerTimeSetter(todaysPrayers.getAsr());
                    }
                    /*
                     * Checking whether the current time is between asr time and maghrib time or not
                     */
                    else if (currentTime > asrTime && currentTime <= maghribTime) {

                        // Checking whether the current shared preferences value changed or not
                        if(!currentPrayer.equals("Maghrib")) {
                            editor.putString("currentPrayer", "Maghrib");
                            editor.commit();

                        }

                        // Checking whether the prayer time before 10 AM or not to see if there is 0 at the beginning of the string or not
                        if(maghribTime<=9)
                        {
                            if(Double.parseDouble(maghribTime.toString().substring(2,4))==0&&maghribTime.toString().length()==4){
                                prayerTime = "0"+ maghribTime.toString().charAt(0)+":"+"0"+maghribTime.toString().charAt(2);

                            }

                            else{

                                prayerTime = "0"+ maghribTime.toString().charAt(0)+":"+maghribTime.toString().substring(2,4);

                            }

                        }
                        else
                        {
                            if(Double.parseDouble(maghribTime.toString().substring(2,4))==0 &&maghribTime.toString().length()==4){
                                prayerTime = maghribTime.toString().substring(0, 2)+":"+"0"+maghribTime.toString().substring(3);

                            }else{
                                prayerTime = maghribTime.toString().substring(0, 2)+":"+maghribTime.toString().substring(3, 5);

                            }

                        }

                        Log.d("hii",prayerTime);

                        tvPrayerName.setText("Maghrib");
                        prayerTimeSetter(todaysPrayers.getMaghrib());
                    }

                    /*
                     * Checking whether the current time is between maghrib time and isha time or not
                     */
                    else if (currentTime > maghribTime && currentTime <= ishaTime) {

                        // Checking whether the current shared preferences value changed or not
                        if(!currentPrayer.equals("Isha")) {
                            editor.putString("currentPrayer", "Isha");
                            editor.commit();
                        }

                        // Checking whether the prayer time before 10 AM or not to see if there is 0 at the beginning of the string or not
                        if(ishaTime<=9)
                        {
                            if(Double.parseDouble(ishaTime.toString().substring(2,4))==0 &&ishaTime.toString().length()==4){
                                prayerTime = "0"+ ishaTime.toString().charAt(0)+":"+"0"+ishaTime.toString().charAt(2);

                            }else{
                                prayerTime = "0"+ ishaTime.toString().charAt(0)+":"+ishaTime.toString().substring(2,4);

                            }

                        }
                        else
                        {
                            if(Double.parseDouble(ishaTime.toString().substring(2,4))==0 &&ishaTime.toString().length()==4){
                                prayerTime = ishaTime.toString().substring(0, 2)+":"+"0"+ishaTime.toString().substring(3);

                            }else{
                                prayerTime = ishaTime.toString().substring(0, 2)+":"+ishaTime.toString().substring(3, 5);

                            }

                        }
                        tvPrayerName.setText("Isha");
                        prayerTimeSetter(todaysPrayers.getIsha());
                    }

                    setCountdown(result_time,prayerTime);
                }

                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);

        /*
         * Handling when user clicks on reset icon to reset sibha counter
         */
        tvResetSibha.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("sibha", 0);
            editor.apply();
            tvSibhaCounter.setText(String.valueOf(0));
        });

        /*
         * Handling when user clicks on + icon to increment sibha counter
         */
        tvIncrementSibha.setOnClickListener(view12 -> {
            int curr_counter = Integer.parseInt(tvSibhaCounter.getText().toString());
            curr_counter++;
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("sibha", curr_counter);
            editor.apply();
            tvSibhaCounter.setText(String.valueOf(curr_counter));
        });

        /*
         * Listen on address (location)
         */
        sharedPreferenceChangeListener = (sharedPreferences, key) -> {

            // If address is changed
            if (key.equals("address")) {
                if(settings.getString("address", "Loading").equals("Loading")){
                    ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("please wait...");
                    dialog.show();
                }
                else{
                    tvLocation.setText(settings.getString("address", "Location not accessible"));
                }
            }

        };

        /*
         * Handling when user clicks on tasbeeh image
         */
        imgTasabeeh.setOnClickListener(view13 -> {
            intent = new Intent(getActivity(), TasabehActivity.class);
            startActivity(intent);
        });

        /*
         * Handling when user clicks on todo image
         */
        imgTodo.setOnClickListener(view14 -> {
            intent = new Intent(getActivity(), TodoActivity.class);
            startActivity(intent);
        });

        /*
         * Handling when user clicks on statistics image
         */
        imgStatistics.setOnClickListener(view15 -> {
            intent = new Intent(getActivity(), PrayerStatisticsActivity.class);
            startActivity(intent);
        });

        /*
         * Handling when user clicks on azkar image
         */
        imgAzkar.setOnClickListener(view16 -> {
            intent = new Intent(getActivity(), AzkarActivity.class);
            startActivity(intent);
        });

        return view;
    }

    /*
     * Here is the function to calculate the time remaining for the upcoming prayer then to set its text view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCountdown(String result_time, String prayerTime) {
        /*
         * Used Duration method to calculate difference between two 24-hour format times
         */
        if(!result_time.isEmpty())
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime lt1 = LocalTime.parse(result_time, dtf);
            LocalTime lt2 = LocalTime.parse(prayerTime, dtf);
            Duration between = Duration.between(lt1, lt2);

            if (lt2.isBefore(lt1))
            {
                between = Duration.ofMinutes(TimeUnit.DAYS.toMinutes(1)).plus(between);
            }
            String finalTime=between.toString().replace("PT","");
            if(finalTime.contains("H")&&finalTime.contains("M")){
                finalTime=finalTime.replace("H"," hrs and ");
                finalTime=finalTime.replace("M"," mins left");
            }
            else if(finalTime.contains("H"))
            {
                finalTime=finalTime.replace("H"," hrs and 0 mins left");

            }
            else if(finalTime.contains("M"))
            {
                finalTime=finalTime.replace("M"," mins left");
            }
            else {
                finalTime="Prayer is now";
            }

            tvPrayerCountdown.setText(finalTime);
        }

    }

    /*
     * Here is the function to generate time in Double format to be used in comparisons easily
     */
    public Double prayerTimeGenerator(String prayerTime)
    {
        // Remove the AM/PM part
        String timePart=prayerTime.substring(0, 5);

        String modifiedTime = timePart.replace(":", ".");
        System.out.println(Double.parseDouble(modifiedTime));
        Log.d("keyy",String.valueOf(Double.parseDouble(modifiedTime)));
        return Double.parseDouble(modifiedTime);
    }

    /*
     * Here is the function to format and set the upcoming prayer time with the 12-hour format
     */
    private void prayerTimeSetter(String prayerTime) {
        String dateInString = prayerTime.substring(0, 5);
        String AoP = prayerTime.substring(6, 8);

        Date date = null;
        try {
            date = secondaryFormatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        String formattedDateString = secondaryFormatter.format(date);

        tvPrayerTime.setText(String.format("%s %s", formattedDateString, AoP));
    }

    /*
     * Overriding function on Resume to register a listener to shared preference and set textview location
     */
    @Override
    public void onResume() {
        super.onResume();
        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        tvLocation.setText(settings.getString("address", "Location not accessible"));
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
