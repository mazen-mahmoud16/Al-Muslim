package com.almuslim.elaislami.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.almuslim.elaislami.R;

/*
 * Here is splash screen activity class that is triggered for 3 seconds when we open the application then the main activity is opened
 */
@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /*
         * Hiding the toolbar in the splash screen
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /*
         * Initialize the handler
         */
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}