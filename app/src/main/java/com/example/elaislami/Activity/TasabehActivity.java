package com.example.elaislami.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;

import com.example.elaislami.Adapter.AzkarAdapter;
import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;
import com.example.elaislami.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TasabehActivity extends AppCompatActivity {

    ImageButton back_btn;
    RecyclerView tasabehRV;
    String jsonFileString;
    Gson gson;
    Type listAzkarType;
    List<AzkarModel> tasabeehModelList;
    AzkarAdapter tasabeehAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasabeh);

        Toolbar toolbar = findViewById(R.id.tool_bar_tasabeh);

        setSupportActionBar(toolbar);

        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TasabehActivity.this, MainActivity.class);
                i.putExtra("back_state", 0);
                startActivity(i);

            }
        });

        tasabehRV = findViewById(R.id.TasabehRV);
        tasabehRV.setLayoutManager(new LinearLayoutManager(TasabehActivity.this));
        gson = new Gson();
        listAzkarType = new TypeToken<List<AzkarModel>>() {
        }.getType();

        jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "tasabeeh.json");
        tasabeehModelList = gson.fromJson(jsonFileString, listAzkarType);
        tasabeehAdapter = new AzkarAdapter(tasabeehModelList);
        tasabehRV.setAdapter(tasabeehAdapter);

    }

}