package com.example.elaislami.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.elaislami.R;

public class PrayerStatisticsActivity extends AppCompatActivity {

    int backState;
    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_statistics);

        backState=getIntent().getIntExtra("back_state",-1);

        Toolbar toolbar = findViewById(R.id.tool_bar_prayer);

        setSupportActionBar(toolbar);

        back_btn=findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PrayerStatisticsActivity.this,MainActivity.class);
                i.putExtra("back_state",0);
                startActivity(i);

            }
        });
    }
}