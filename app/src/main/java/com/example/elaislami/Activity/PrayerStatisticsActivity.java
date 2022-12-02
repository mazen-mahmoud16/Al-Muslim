package com.example.elaislami.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.elaislami.R;

public class PrayerStatisticsActivity extends AppCompatActivity {

    int backState;
    ImageButton back_btn;
    ProgressBar progressBar;
    Button generate;
    TextView numberSalat;

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

        generate = findViewById(R.id.generate);

        progressBar = findViewById(R.id.prog_bar);

        numberSalat = findViewById(R.id.num_salat);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numberSalat.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                numberSalat.setText("20/35");
                ObjectAnimator.ofInt(progressBar, "progress", 20)
                        .setDuration(1000)
                        .start();
            }
        });

    }
}