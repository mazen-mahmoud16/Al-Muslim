package com.example.elaislami.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.elaislami.Adapter.AzkarAdapter;
import com.example.elaislami.Utils.Utils;
import com.example.elaislami.Model.AzkarModel;
import com.example.elaislami.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class AzkarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] azkar = {"Morning Azkar", "Night Azkar"};


    ImageButton back_btn;
    RecyclerView azkarRV;
    String jsonFileString;
    Gson gson;
    Type listAzkarType;
    List<AzkarModel> azkarModelList;
    AzkarAdapter azkarAdapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar);

        Spinner spin = (Spinner) findViewById(R.id.azkar_spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter azkarAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, azkar);
        azkarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(azkarAdapter);

        Toolbar toolbar = findViewById(R.id.tool_bar_azkar);

        setSupportActionBar(toolbar);

        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AzkarActivity.this, MainActivity.class);
                i.putExtra("back_state", 0);
                startActivity(i);

            }
        });

        azkarRV =findViewById(R.id.AzkarRV);
        azkarRV.setLayoutManager(new LinearLayoutManager(AzkarActivity.this));
        gson = new Gson();
        listAzkarType = new TypeToken<List<AzkarModel>>() { }.getType();


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Morning
        if(position == 0){
            jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "morningazkar.json");
            azkarModelList = gson.fromJson(jsonFileString, listAzkarType);
            azkarAdapterList=new AzkarAdapter(azkarModelList);
            azkarRV.setAdapter(azkarAdapterList);
        }
        else if(position==1){
            jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "nightazkar.json");
            azkarModelList = gson.fromJson(jsonFileString, listAzkarType);
            azkarAdapterList=new AzkarAdapter(azkarModelList);
            azkarRV.setAdapter(azkarAdapterList);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}