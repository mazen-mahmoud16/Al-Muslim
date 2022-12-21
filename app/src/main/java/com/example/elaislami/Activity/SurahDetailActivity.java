package com.example.elaislami.Activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.Adapter.AyahListAdapter;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.ViewModel.SurahViewModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 * Here is Surah details activity that triggers when the user clicks to a specific surah
 */
public class SurahDetailActivity extends AppCompatActivity {

    /*
     * Edit texts, image buttons, recycler view and buttons used
     */
    private RecyclerView rvSurahDetail;
    private TextView tvEnglishName, tvArabicName, tvJuz;
    private ImageView back_btn;

    // Get the arabic and english name of surah
    private String englishName,arabicName;

    // Have a reference to view model
    private SurahViewModel surahViewModel;

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
        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);
        ayahAdapter=new AyahListAdapter(ayahList,surahIndex);

        // Observe on the live data change
        surahViewModel.getAllAyahs().observe(this, ayahModels -> {
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
    }

}