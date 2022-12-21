package com.example.elaislami.View.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.Adapter.AyahListAdapter;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBManager.RoomDBModel.AyahDBModel;
import com.example.elaislami.RoomDBManager.ViewModel.AyahViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/*
 * Here is Surah details activity that triggers when the user clicks to a specific surah
 */
public class SurahDetailActivity extends AppCompatActivity {

    /*
     * Edit texts, image buttons, recycler view , image button and buttons used
     */
    private RecyclerView rvSurahDetail;
    private TextView tvEnglishName, tvArabicName, tvJuz, startTime , totalTime;
    private ImageView back_btn;
    private ImageButton playButton;

    // Get the arabic and english name of surah
    private String englishName,arabicName;

    // Have a reference to view model
    private AyahViewModel ayahViewModel;

    // Declare an empty arraylist to be passed to adapter
    private List<AyahDBModel> ayahList= new ArrayList<>();

    // Get the surah index to get the corresponding ayahs
    private int surahIndex;

    /*
     * To use shared preference
     */
    public static final String PREFS_NAME = "MyPreferenceFile";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    // Adapter
    private AyahListAdapter ayahAdapter;

    // Handler for surah audio
    private Handler handler = new Handler();

    // Surah audio seek bar
    SeekBar seekBar;

    // Media player reference
    MediaPlayer mediaPlayer;

    /*
     * Here is on create function
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        /*
         * Assign elements in layout
         */
        Toolbar toolbar = findViewById(R.id.tool_bar_surah_detail);
        rvSurahDetail =findViewById(R.id.SurahDetailRV);
        tvJuz =findViewById(R.id.juz);
        tvEnglishName =findViewById(R.id.SurahEnglishName);
        tvArabicName =findViewById(R.id.SurahArabicName);
        back_btn=findViewById(R.id.back_btn);
        playButton = findViewById(R.id.play_button);
        startTime = findViewById(R.id.start_time);
        totalTime = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seekBar);

        // Set toolbar
        setSupportActionBar(toolbar);

        // Get surah index
        surahIndex = getIntent().getIntExtra("surah_number",0);

        // Put surah number in a shared preference
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putInt("surahNumber", surahIndex);
        editor.commit();

        /*
         * Initialize vew model and adapter to pass it to recycler view
         */
        ayahViewModel = new ViewModelProvider(this).get(AyahViewModel.class);
        ayahAdapter=new AyahListAdapter(ayahList,surahIndex);

        // Observe on the live data change
        ayahViewModel.getAllAyahs().observe(this, ayahModels -> {
            // Update the cached copy of the words in the adapter.
            assert ayahModels != null;
            if(!ayahModels.isEmpty()) {

                // Add this text to the first of any surah
                if(!(ayahModels.get(0).getSurahNumber()==1)){
                    ayahModels.add(0,new AyahDBModel(0,"بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیم",0));
                }
            }

            // Pass data to the adapter
            ayahAdapter.setAyahs(ayahModels);
            ayahList=ayahModels;

            // Put guz
            if(!ayahList.isEmpty()&&ayahList.get(0).getSurahNumber()!=1) {
                if(ayahList.get(1).getJuz()==ayahList.get(ayahList.size()-1).getJuz()){
                    tvJuz.setText("Juz " + ayahList.get(1).getJuz());
                }else{
                    tvJuz.setText("From juz " + ayahList.get(1).getJuz()+" to "+ ayahList.get(ayahList.size()-1).getJuz());
                }
            }else if(!ayahList.isEmpty()&&ayahList.get(0).getSurahNumber()==1){
                tvJuz.setText("Juz " + ayahList.get(0).getJuz());

            }
            else {
                tvJuz.setText("Juz");

            }
        });

        // Set adapter
        rvSurahDetail.setLayoutManager(new LinearLayoutManager(this));
        rvSurahDetail.setAdapter(ayahAdapter);

        // Get english and arabic names of surah
        englishName=getIntent().getStringExtra("surah_englishName");
        arabicName=getIntent().getStringExtra("surah_arabicName");

        // Insert names in the text views
        tvEnglishName.setText(englishName);
        tvArabicName.setText(arabicName);

        /*
         * Handle when user clicks to back button
         */
        back_btn.setOnClickListener(view -> {
            Intent i=new Intent(SurahDetailActivity.this,MainActivity.class);
            i.putExtra("back_state",3);
            startActivity(i);

        });

        /*
         * Calling listen audio method to initiate and start playing the surah audio on click
         */
        try {
            listenAudio();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Checking if the device has any network connection
     */
    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return !haveConnectedWifi && !haveConnectedMobile;
    }

    /*
     * listen audio method to initiate and start playing the surah audio on click and to handle on click of pause or play
     */
    @SuppressLint("ClickableViewAccessibility")
    private void listenAudio() throws IOException {

        mediaPlayer = new MediaPlayer();
        seekBar.setMax(100);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    playButton.setImageResource(R.drawable.ic_play_circle);
                }else{
                    if(haveNetworkConnection()){
                        Toasty.error(SurahDetailActivity.this,"Please enable internet connection",Toasty.LENGTH_SHORT,true).show();
                        return;
                    }
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.ic_pause_circle);
                    updateSeekBar();
                }
            }
        });

        preparedMediaPlayer();

        /*
         * Fired when user clicks on seek bar to move forward or backward
         */
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(haveNetworkConnection()){
                    Toasty.error(SurahDetailActivity.this,"Please enable internet connection",Toasty.LENGTH_SHORT,true).show();
                    return false;
                }
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                startTime.setText(timeToMilliSecond(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        /*
         * Fired when user drag the progress bar forward or backward
         */
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });

        /*
         * Fired when the audio is finished
         */
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                seekBar.setProgress(0);
                playButton.setImageResource(R.drawable.ic_play_circle);
                startTime.setText("0:00");
                totalTime.setText("0:00");
                mediaPlayer.reset();
                try {
                    preparedMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
     * Fired to set data source from the website and get the surah audio from it to stream the surah audio got
     */
    private void preparedMediaPlayer() throws IOException {

        if(haveNetworkConnection()){
            Toasty.error(SurahDetailActivity.this,"Please enable internet connection",Toasty.LENGTH_SHORT,true).show();
            return;
        }

        String surahNumber="";
        if(surahIndex<10){
            surahNumber = "00"+surahIndex;
        }else if(surahIndex<100){
            surahNumber = "0"+surahIndex;
        }else if(surahIndex>=100){
            surahNumber = String.valueOf(surahIndex);
        }
        //https://download.quranicaudio.com/quran/abdul_wadood_haneef_rare/001.mp3
        mediaPlayer.setDataSource("https://download.quranicaudio.com/quran/mishaari_raashid_al_3afaasee/"+surahNumber.trim()+".mp3");
        mediaPlayer.prepare();
        totalTime.setText(timeToMilliSecond(mediaPlayer.getDuration()));
    }

    /*
     * Start thread to update progress bar and update the counter text view beside the progress bar
     */
    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            startTime.setText(timeToMilliSecond(currentDuration));
        }
    };

    /*
     * Called when to update progress bar
     */
    private void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) *100));
            handler.postDelayed(updater,1000);
        }
    }

    /*
     * Called to convert time to milli seconds
     */
    private String timeToMilliSecond(long milliSecond){
        String timerString = "";
        String secondString ;

        int hours = (int) (milliSecond /(1000 * 60 * 60));
        int minutes = (int) (milliSecond % (1000 * 60 * 60)) / (1000 * 60);
        int second = (int) ((milliSecond % (1000 * 60 * 60))% (1000 * 60) /1000);

        if(hours>0){
            timerString = hours + ":";
        }
        if(second < 10){
            secondString = "0" + second;
        }else{
            secondString = "" + second;
        }
        timerString = timerString + minutes + ":" + secondString;
        return timerString;
    }

    /*
     * Override on stop to stop playing the audio when user close the surah detail activity or close the application
     */
    @Override
    protected void onStop() {
        if(mediaPlayer.isPlaying()) {
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_play_circle);
        }
        super.onStop();
    }

}
