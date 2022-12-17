package com.example.elaislami.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {
    private ImageView todo_btn;
    private ImageView statistics_btn;
    private ImageView azkar_btn;
    private TextView loc,salatName,salatTime,test,sibhaCounter;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void run() {



                String json = settings.getString("prayers", "Loading");
                PrayerModel obj = gson.fromJson(json, PrayerModel.class);

                if (obj != null) {
                    currentPrayer = settings.getString("currentPrayer", "Loading");
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

                    if ((current_int <= fajr_int && current_int < isha_int) || (current_int > fajr_int && current_int > isha_int)) {

                        if(!currentPrayer.equals("Fajr")) {
                            editor.putString("currentPrayer", "Fajr");
                            editor.commit();
                        }

                            hour11 = current.substring(0, 2)+":"+current.substring(3, 5);
                            hour22 = fajr.substring(0, 2)+":"+fajr.substring(3, 5);


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

                        if(!currentPrayer.equals("Dhuhr")) {
                            editor.putString("currentPrayer", "Dhuhr");
                            editor.commit();
                        }

                        hour11 = current.substring(0, 2)+":"+current.substring(3, 5);
                        hour22 = dhuhr.substring(0, 2)+":"+dhuhr.substring(3, 5);

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


                        if(!currentPrayer.equals("Asr")) {
                            editor.putString("currentPrayer", "Asr");
                            editor.commit();
                        }

                        hour11 = current.substring(0, 2)+":"+current.substring(3, 5);
                        hour22 = asr.substring(0, 2)+":"+asr.substring(3, 5);


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

                        if(!currentPrayer.equals("Maghrib")) {
                            editor.putString("currentPrayer", "Maghrib");
                            editor.commit();

                        }

                        hour22 = maghrib.substring(0, 2)+":"+maghrib.substring(3, 5);
                        hour11 = current.substring(0, 2)+":"+current.substring(3, 5);


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

                        if(!currentPrayer.equals("Isha")) {
                            editor.putString("currentPrayer", "Isha");
                            editor.commit();
                        }
                        hour22 = isha.substring(0, 2)+":"+isha.substring(3, 5);
                        hour11 = current.substring(0, 2)+":"+current.substring(3, 5);

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

                  if(!hour11.isEmpty()){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime lt1 = LocalTime.parse(hour11, dtf);
                    LocalTime lt2 = LocalTime.parse(hour22, dtf);

                    Duration between = Duration.between(lt1, lt2);

                    if (lt2.isBefore(lt1)) {
                        between = Duration.ofMinutes(TimeUnit.DAYS.toMinutes(1)).plus(between);
                    }
                    String finalTime=between.toString().replace("PT","");
                    if(finalTime.contains("H")&&finalTime.contains("M")){
                        finalTime=finalTime.replace("H"," hrs and ");
                        finalTime=finalTime.replace("M"," mins left");
                    }
                    else if(finalTime.contains("H")){
                        finalTime=finalTime.replace("H"," hrs and 0 mins left");

                    }else{
                        finalTime=finalTime.replace("M"," mins left");
                    }

                    test.setText(finalTime);
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

        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

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