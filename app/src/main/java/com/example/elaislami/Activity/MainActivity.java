package com.example.elaislami.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.elaislami.Listener.BroadCastListener;
import com.example.elaislami.R;
import com.example.elaislami.Adapter.TabsAdapter;
import com.example.elaislami.Receiver.LocationReceiver;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements LocationListener, BroadCastListener {

    int backState;
    LocationManager locationManager;
    public static final String PREFS_NAME = "MyPreferenceFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    LocationReceiver locationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toasty.info(MainActivity.this, "Hello", Toasty.LENGTH_LONG, true).show();

        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        locationReceiver = new LocationReceiver(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        if (isGPSEnabled(MainActivity.this)) {
            getLocation();
        } else {
            Toast.makeText(MainActivity.this, "Please enable your location", Toast.LENGTH_LONG).show();
        }


        backState = getIntent().getIntExtra("back_state", -1);

        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        TabLayout tabs = findViewById(R.id.tab_layout);

        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_baseline_home_24));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_prayer));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_qibla));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_quran));

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = findViewById(R.id.view_pager);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabs.getTabCount());

        viewPager.setAdapter(tabsAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        if (backState != -1) {
            viewPager.setCurrentItem(backState);
            backState = -1;
        }

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private boolean isGPSEnabled(Context mContext) {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 500, MainActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        editor.putString("long", String.valueOf(location.getLongitude()));
        editor.putString("lat", String.valueOf(location.getLatitude()));
        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address_temp = addresses.get(0).getAddressLine(0);

            editor.putString("address", address_temp);

        }catch (Exception e){
            e.printStackTrace();
        }


        editor.commit();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        if(isGPSEnabled(MainActivity.this)){
            getLocation();
        }
        else{
            Toast.makeText(MainActivity.this,"Please enable your location",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        registerReceiver(locationReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(locationReceiver);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void regenerateLocation() {
        Toast.makeText(this, "Getting location", Toast.LENGTH_SHORT).show();
        getLocation();
    }
}