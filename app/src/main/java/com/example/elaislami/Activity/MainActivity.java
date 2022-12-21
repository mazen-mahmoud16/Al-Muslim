package com.example.elaislami.Activity;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.example.elaislami.Adapter.TabsAdapter;
import com.example.elaislami.Listener.BroadCastListener;
import com.example.elaislami.R;
import com.example.elaislami.Receiver.LocationReceiver;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
import java.util.Locale;
import es.dmoral.toasty.Toasty;

/*
 * Here is Main activity class that is triggered when we open the application
 */
public class MainActivity extends AppCompatActivity implements LocationListener, BroadCastListener {

    // To handle when the user clicks on back button
    private int backState;

    /*
     * To access location services
     */
    private LocationManager locationManager;
    private LocationReceiver locationReceiver;

    /*
     * To use shared preference
     */
    public static final String PREFS_NAME = "MyPreferenceFile";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    /*
     * To create the tabs of the view pager
     */
    private TabLayout tabs;
    private TabsAdapter tabsAdapter;


    /*
     * Here is on create function
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign shared preference settings
        settings = getSharedPreferences(PREFS_NAME, 0);

        // Assign editor
        editor = settings.edit();

        /*
         * To test prayer statistics activity that the new week (sunday) came to delete data in ROOM DB
         */

        /*
        editor.putString("date_week",null);
        editor.putInt("surahNumber",-1);
        editor.commit();
        editor = settings.edit();
        */

        /*
         * Initialize and create the toolbar
         */
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        /*
         * Initialize and create tabs that will be used in the main activity view pager
         */
        tabs = findViewById(R.id.tab_layout);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_baseline_home));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_prayer));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_qibla));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_quran));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initialize tabs adapter
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabs.getTabCount());

        /*
         * Initialize and create the main activity view pager with its tabs
         */
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        /*
         * Triggered when choosing any tab in the view pager
         */
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

        // Getting the value of the back state from shared preferences
        backState = getIntent().getIntExtra("back_state", -1);

        /*
         * Used in the case of going to new activity then going back to the main activity at any fragment or current item in the view pager
         * Decide ans Set the view pager current item with the last item that it was on before moving to new activity when going back to the main activity
         * Then setting back the back state value with -1 as long as main activity is opened
         */
        if (backState != -1) {
            viewPager.setCurrentItem(backState);
            backState = -1;
        }

        /*
         * Making sure that location access permission is enabled
         */
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        if (isGPSEnabled(MainActivity.this)) {
            getLocation();
        }
        else{
            Toasty.warning(MainActivity.this, "Please open your location", Toasty.LENGTH_LONG, true).show();
        }

        // Assigning the location broadcast receiver to listen and regenerate location when location is enabled and detected
        locationReceiver = new LocationReceiver(this);
    }

    // Checking that GPS is enabled
    private boolean isGPSEnabled(Context mContext) {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /*
     * Requesting location updates
     */
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

    /*
     * Fired when the location changed
     */
    @Override
    public void onLocationChanged(Location location) {

        // Storing the current longitude and latitude in shared preferences
        editor.putString("long", String.valueOf(location.getLongitude()));
        editor.putString("lat", String.valueOf(location.getLatitude()));

        /*
         * Getting the current address of the current longitude and latitude
         */
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

    /*
     * Checking the current location status
     */
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
            Toasty.warning(MainActivity.this, "Please open your location", Toasty.LENGTH_LONG, true).show();
        }
    }

    /*
     * Register the broadcast receiver to detect GPS is enabled
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        registerReceiver(locationReceiver, filter);
    }
    /*
     * Unregister the broadcast receiver
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(locationReceiver);
    }

    // When the GPS is turned off
    @Override
    public void onProviderDisabled(String provider) {
        Toasty.warning(MainActivity.this, "Please open your location", Toasty.LENGTH_LONG, true).show();
    }

    // To regenerate location back when GPS is enabled and there is internet connection
    @Override
    public void regenerateLocation() {
        Toasty.info(MainActivity.this, "Getting your location", Toasty.LENGTH_LONG, true).show();
        getLocation();
    }
}