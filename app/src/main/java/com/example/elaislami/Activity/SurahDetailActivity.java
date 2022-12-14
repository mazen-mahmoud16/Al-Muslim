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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SurahDetailActivity extends AppCompatActivity {

    RecyclerView surahDetailRV;
    TextView englishNameTv,arabicNameTv;
    String englishName,arabicName;
    ImageView back_btn;
    private SurahViewModel surahViewModel;
    List<AyahDBModel> ayahList;
    int surahIndex;
    public static final String PREFS_NAME = "MyPreferenceFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    AyahListAdapter ayahAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);


        Toolbar toolbar = findViewById(R.id.tool_bar_surah_detail);

        setSupportActionBar(toolbar);

        surahIndex = getIntent().getIntExtra("surah_number",0);

        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putInt("surahNumber", surahIndex);
        editor.commit();


        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);

        surahDetailRV =findViewById(R.id.SurahDetailRV);

        ayahAdapter=new AyahListAdapter(ayahList,surahIndex);



        surahViewModel.getAllAyahs().observe(this, new Observer<List<AyahDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<AyahDBModel> ayahModels) {
                // Update the cached copy of the words in the adapter.
                ayahAdapter.setAyahs(ayahModels);

            }
        });
        surahDetailRV.setLayoutManager(new LinearLayoutManager(this));

        surahDetailRV.setAdapter(ayahAdapter);
        englishNameTv=findViewById(R.id.SurahEnglishName);
        arabicNameTv=findViewById(R.id.SurahArabicName);
        back_btn=findViewById(R.id.back_btn);
        englishName=getIntent().getStringExtra("surah_englishName");
        arabicName=getIntent().getStringExtra("surah_arabicName");


        englishNameTv.setText(englishName);
        arabicNameTv.setText(arabicName);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SurahDetailActivity.this,MainActivity.class);
                i.putExtra("back_state",3);
                startActivity(i);

            }
        });
    }

}