package com.almuslim.elaislami.View.Activity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.almuslim.elaislami.BroadcastReceiver.LocationReceiver;
import com.almuslim.elaislami.BuildConfig;
import com.almuslim.elaislami.Listener.LocationListener;
import com.almuslim.elaislami.Adapter.TabsAdapter;
import com.almuslim.elaislami.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
import java.util.Locale;
import es.dmoral.toasty.Toasty;

/*
 * Here is Main activity class that is triggered when we open the application
 */
public class MainActivity extends AppCompatActivity implements android.location.LocationListener, LocationListener {


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

    // static boolean to check whether the user denies location permission for the first time
    static boolean firstDenied = false;

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
        tabs.addTab(tabs.newTab().setText("Home").setIcon(R.drawable.ic_baseline_home));
        tabs.addTab(tabs.newTab().setText("Prayers").setIcon(R.drawable.ic_prayer));
        tabs.addTab(tabs.newTab().setText("Qibla").setIcon(R.drawable.ic_qibla));
        tabs.addTab(tabs.newTab().setText("Quran").setIcon(R.drawable.ic_quran));
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


        /*
         * Checking whether GPS is activated or not
         */
        if (!isGPSEnabled(MainActivity.this)) {
            Toasty.warning(MainActivity.this, "Please open your location", Toasty.LENGTH_LONG, true).show();
        }

        // Check location permission
        checkLocationPermission();


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
     * This function checks whether the application has access to location or not, and if not it requests it
     */
    public void checkLocationPermission() {

        // Permission not granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);

        }

        // Permission granted
        else {
            getLocation();
            editor = settings.edit();
            editor.putString("state", "done");
            editor.commit();
        }
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
     * Here is the function that handles the user response to the location permission
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 99) {

            // Checking permission
            String permission = permissions[0];
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                // User rejected the permission
                boolean showRationale = shouldShowRequestPermissionRationale(permission);

                /*
                 * This condition is triggered when the user chooses the option of "never asks again"
                 */
                if ((!showRationale) && (settings.getString("state", "notyet").equals("notyet"))) {
                    editor = settings.edit();
                    editor.putString("state", "denied");
                    editor.commit();
                    firstDenied = true;

                    new AlertDialog.Builder(this)
                            .setTitle("Permission needed")
                            .setMessage("The location permission is required to get the precise prayer times and Qibla direction.\n\n" +
                                    "It is required only while using the application not in the background in order to preserve you battery life.\n\n" +
                                    "If you still want to deny the location access, you can still access other functionalities. Yet the default location longitude and latitude will be both 0, so the prayers and qibla will not be accurate")
                            .setPositiveButton("Done", (dialogInterface, i) -> {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99);
                            })
                            .create()
                            .show();

                }

                /*
                 * If user has chosen never asks again before, a snack bar will be shown
                 */
                else if (!showRationale){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You have previously declined the location permission.\n" +
                            "You must approve this permission in \"Permissions\" in the app settings on your device to access all application functionalities.", Snackbar.LENGTH_LONG).setAction("Settings", view -> startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID))));
                    snackbar.show();
                }

                /*
                 * This condition is triggered when the user chooses deny without never asks again
                 */
                else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission) && !firstDenied && (settings.getString("state", "notyet").equals("notyet"))) {
                    new AlertDialog.Builder(this)
                            .setTitle("Permission needed")
                            .setMessage("The location permission is required to get the precise prayer times and Qibla direction.\n\n" +
                                    "It is required only while using the application not in the background in order to preserve you battery life.\n\n1" +
                                    "If you still want to deny the location access, you can still access other functionalities. Yet the default location longitude and latitude will be both 0, so the prayers and qibla will not be accurate")
                            .setPositiveButton("Done", (dialogInterface, i) -> {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99);
                            })
                            .create()
                            .show();
                    firstDenied = true;
                }

                /*
                 * This condition triggers when the user chooses to deny for the second time, so a snack bar will be shown
                 */
                else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You have previously declined this permission.\n" +
                            "You must approve this permission in \"Permissions\" in the app settings on your device.", Snackbar.LENGTH_LONG).setAction("Settings", view -> startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID))));
                    snackbar.show();
                }
            }

            /*
             * Permission granted
             */
            else {
                getLocation();
            }

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