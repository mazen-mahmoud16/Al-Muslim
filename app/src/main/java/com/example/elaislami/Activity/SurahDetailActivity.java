package com.example.elaislami.Activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elaislami.APIHolders.JsonPlaceHolderAPI;
import com.example.elaislami.Adapter.AyahListAdapter;
import com.example.elaislami.Model.AyahFirstResponse;
import com.example.elaislami.Model.AyahModel;
import com.example.elaislami.R;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.ViewModel.SurahViewModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        ayahAdapter=new AyahListAdapter(surahIndex);

        surahDetailRV.setLayoutManager(new LinearLayoutManager(this));

        surahDetailRV.setAdapter(ayahAdapter);

        surahViewModel.getAllAyahs().observe(this, new Observer<List<AyahDBModel>>() {
            @Override
            public void onChanged(@Nullable final List<AyahDBModel> ayahModels) {
                // Update the cached copy of the words in the adapter.
                ayahAdapter=new AyahListAdapter(surahIndex);
                /*for (AyahDBModel ayahDBModel:ayahModels) {
                    Log.d("xxxx",ayahDBModel.getText());
                    Log.d("xxxx",String.valueOf(ayahDBModel.getSurahNumber()));
                    Log.d("xxxx",String.valueOf(ayahDBModel.getNumberInSurah()));
                }*/
                ayahAdapter.setAyahs(ayahModels);
                surahDetailRV.setAdapter(ayahAdapter);

            }
        });





/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.alquran.cloud/v1/surah/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<AyahFirstResponse> call = jsonPlaceHolderAPI.getAyas(surahIndex);
        Log.d("Response", call.toString());


        call.enqueue(new Callback<AyahFirstResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<AyahFirstResponse> call, Response<AyahFirstResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {

                    AyahFirstResponse mAllPosts = response.body();

                    surahDetailRV =findViewById(R.id.SurahDetailRV);

                    List<AyahDBModel> ayahlist=mAllPosts.getData().getAyahs();

                    AyahListAdapter ayahAdapter=new AyahListAdapter(ayahlist,surahIndex);
                    juz=findViewById(R.id.juz);
                    //juz.setText("Juz"+" "+ ayahlist.get(surahIndex).getJuz());
                    surahDetailRV.setAdapter(ayahAdapter);
                    surahDetailRV.setLayoutManager(new LinearLayoutManager(SurahDetailActivity.this));

                }
            }

            @Override
            public void onFailure(Call<AyahFirstResponse> call, Throwable t) {
                Log.d("MVVMX", "--- FAILED " + t.getMessage());

            }


        });


 */
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