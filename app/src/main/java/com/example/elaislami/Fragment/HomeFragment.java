package com.example.elaislami.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elaislami.Activity.AzkarActivity;
import com.example.elaislami.Activity.PrayerStatisticsActivity;
import com.example.elaislami.Activity.TodoActivity;
import com.example.elaislami.Model.PrayerModel;
import com.example.elaislami.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {
    private ImageView todo_btn;
    private ImageView statistics_btn;
    private ImageView azkar_btn;
    private TextView loc;
    private TextView salatName;
    private TextView salatTime;
    private TextView test;
    private TextView sibhaCounter;
    private ImageButton reset;
    private ImageButton add;

    String currentPrayer = "";


    Intent intent;

    public static final String PREFS_NAME = "MyPreferenceFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        todo_btn = view.findViewById(R.id.img1);
        statistics_btn = view.findViewById(R.id.img2);
        azkar_btn = view.findViewById(R.id.img3);
        salatName = view.findViewById(R.id.salat);
        salatTime = view.findViewById(R.id.salat_tim);
        test=view.findViewById(R.id.counter);

        sibhaCounter = view.findViewById(R.id.sibha);
        reset = view.findViewById(R.id.reset);
        add = view.findViewById(R.id.add);


        loc=view.findViewById(R.id.loc);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        loc.setText(settings.getString("address", "Loading"));

        Gson gson = new Gson();

        Handler handler = new Handler();
        editor=settings.edit();


        final Runnable r = new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void run() {

                editor = settings.edit();


                String json = settings.getString("prayers", "Loading");
                PrayerModel obj = gson.fromJson(json, PrayerModel.class);

                if (obj != null) {
                    String currentPrayer = settings.getString("currentPrayer", "Loading");
                    String fajr = obj.getFajr().substring(0, 5);
                    String dhuhr = obj.getDhuhr().substring(0, 5);
                    String asr = obj.getAsr().substring(0, 5);
                    String maghrib = obj.getMaghrib().substring(0, 5);
                    String isha = obj.getIsha().substring(0, 5);
                    Date current_date = new Date();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm aa");
                    String result_time = formatTime.format(current_date);
                    String current = result_time.substring(0, 5);
                    String current2 = current.replace(":", ".");
                    Double current_int = Double.parseDouble(current2);

                    String fajr2 = fajr.replace(":", ".");
                    Double fajr_int = Double.parseDouble(fajr2);

                    dhuhr = dhuhr.replace(":", ".");
                    asr = asr.replace(":", ".");
                    maghrib = maghrib.replace(":", ".");
                    isha = isha.replace(":", ".");

                    Double dhuhr_int = Double.parseDouble(dhuhr);
                    Double asr_int = Double.parseDouble(asr);
                    Double maghrib_int = Double.parseDouble(maghrib);
                    Double isha_int = Double.parseDouble(isha);
                    String hour11="";
                    String hour22="";
/*
                    int diffMin;
                    int min1 = 0, min2 = 0;
                    int hour1 = 0, hour2 = 0;*/

                    if ((current_int <= fajr_int && current_int < isha_int) || (current_int > fajr_int && current_int > isha_int)) {

                        if (!currentPrayer.equals("Fajr")) {
                            editor.putString("currentPrayer", "Fajr");
                            editor.commit();
                            editor = settings.edit();
                        }
                        hour11 = current.substring(0, 2)+current.substring(3, 5);
                        hour22 = fajr.substring(0, 2)+fajr.substring(3, 5);

/*
                        min1 = Integer.parseInt(current.substring(3, 5));
                        hour1 = Integer.parseInt(current.substring(0, 2));
                        min2 = Integer.parseInt(fajr.substring(3, 5));
                        hour2 = Integer.parseInt(fajr.substring(0, 2));

*/
                        salatName.setText("Fajr");
                        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

                        String dateInString = obj.getFajr().substring(0, 5);
                        String AoP = obj.getFajr().substring(6, 8);

                        Date date = null;
                        try {
                            date = formatter2.parse(dateInString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDateString = formatter2.format(date);

                        salatTime.setText(formattedDateString + " " + AoP);


                    } else if (current_int > fajr_int && current_int <= dhuhr_int) {

                        if (!currentPrayer.equals("Dhuhr")) {
                            editor.putString("currentPrayer", "Dhuhr");
                            editor.commit();
                            editor = settings.edit();
                        }
                        hour11 = current.substring(0, 2)+current.substring(3, 5);
                        hour22 = dhuhr.substring(0, 2)+dhuhr.substring(3, 5);
                        /*
                        min1 = Integer.parseInt(current.substring(3, 5));
                        hour1 = Integer.parseInt(current.substring(0, 2));
                        min2 = Integer.parseInt(dhuhr.substring(3, 5));
                        hour2 = Integer.parseInt(dhuhr.substring(0, 2));*/

                        salatName.setText("Dhuhr");

                        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

                        String dateInString = obj.getDhuhr().substring(0, 5);
                        String AoP = obj.getDhuhr().substring(6, 8);

                        Date date = null;
                        try {
                            date = formatter2.parse(dateInString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDateString = formatter2.format(date);

                        salatTime.setText(formattedDateString + " " + AoP);


                    } else if (current_int > dhuhr_int && current_int <= asr_int) {

                        if (!currentPrayer.equals("Asr")) {
                            editor.putString("currentPrayer", "Asr");
                            editor.commit();
                            editor = settings.edit();
                        }

                        hour11 = current.substring(0, 2)+current.substring(3, 5);
                        hour22 = asr.substring(0, 2)+asr.substring(3, 5);
                        /*
                        min1 = Integer.parseInt(current.substring(3, 5));
                        hour1 = Integer.parseInt(current.substring(0, 2));
                        min2 = Integer.parseInt(asr.substring(3, 5));
                        hour2 = Integer.parseInt(asr.substring(0, 2));
*/
                        salatName.setText("Asr");
                        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

                        String dateInString = obj.getAsr().substring(0, 5);
                        String AoP = obj.getAsr().substring(6, 8);

                            Date date = null;
                            try {
                                date = formatter2.parse(dateInString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String formattedDateString = formatter2.format(date);

                        salatTime.setText(formattedDateString + " " + AoP);


                    }
                    if (current_int > asr_int && current_int <= maghrib_int) {

                        if (!currentPrayer.equals("Maghrib")) {
                            editor.putString("currentPrayer", "Maghrib");
                            editor.commit();
                            editor = settings.edit();
                        }


                        hour11 = current.substring(0, 2)+current.substring(3, 5);
                        hour22 = maghrib.substring(0, 2)+maghrib.substring(3, 5);

                //        min2 = Integer.parseInt(maghrib.substring(3, 5));
                //        hour2 = Integer.parseInt(maghrib.substring(0, 2));



                        salatName.setText("Maghrib");
                        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
                        String dateInString = obj.getMaghrib().substring(0, 5);
                        String AoP = obj.getMaghrib().substring(6, 8);

                            Date date = null;
                            try {
                                date = formatter2.parse(dateInString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String formattedDateString = formatter2.format(date);

                        salatTime.setText(formattedDateString + " " + AoP);


                    } else if (current_int > maghrib_int && current_int <= isha_int) {

                        if (!currentPrayer.equals("Isha")) {
                            editor.putString("currentPrayer", "Isha");
                            editor.commit();
                            editor = settings.edit();
                        }

                        hour11 = current.substring(0, 2)+current.substring(3, 5);
                        hour22 = isha.substring(0, 2)+isha.substring(3, 5);
                        /*
                        min2 = Integer.parseInt(current.substring(3, 5));
                        hour2 = Integer.parseInt(current.substring(0, 2));
                        min1 = Integer.parseInt(isha.substring(3, 5));
                        hour1 = Integer.parseInt(isha.substring(0, 2));*/

                            salatName.setText("Isha");

                            SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

                        String dateInString = obj.getIsha().substring(0, 5);
                        String AoP = obj.getIsha().substring(6, 8);

                        Date date = null;
                        try {
                            date = formatter2.parse(dateInString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDateString = formatter2.format(date);
                        salatTime.setText(formattedDateString + " " + AoP);

                    }


                    int time1 = Integer.parseInt(hour11);
                    int time2 = Integer.parseInt(hour22);



                    int hourDiff = time2 / 100 - time1 / 100 - 1;

                    // difference between minutes
                    int minDiff = time2 % 100 + (60 - time1 % 100);

                    if (minDiff >= 60) {
                        hourDiff++;
                        minDiff = minDiff - 60;

                    }
                    if(minDiff==0&&hourDiff==0){
                        test.setText("Prayer is now");

                    }else {
                        test.setText(hourDiff + " hrs" + " and " + minDiff + " mins left");

                    }

                }
                        handler.postDelayed(this, 1000);
                    }
                };

                handler.postDelayed(r, 1000);

                sibhaCounter.setText(String.valueOf(settings.getInt("sibha", 0)));

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("sibha", 0);
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
                        editor.putInt("sibha", curr_counter);
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