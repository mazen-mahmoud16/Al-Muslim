package com.example.elaislami.View.Fragment;

import static android.content.Context.SENSOR_SERVICE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elaislami.R;



/*
 * Here is qibla fragment class that is triggered when we open the third tab in the view pager
 */
public class QiblaFragment extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView imgCompass;
    private ImageView imgCursor;

    // Location text view
    private TextView tvLocation;

    /*
     * Text views to show angle and heading of qibla
     */
    private TextView tvHeading;
    private TextView tvHint;

    // record the compass picture angle turned
    private float currentCompassDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    /*
     * To use shared preference
     */
    public static final String PREFS_NAME = "MyPreferenceFile";
    private SharedPreferences settings;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    /*
     * Record the longitude and latitude of current location
     */
    private double longDouble;
    private double latDouble;


    /*
     * Here is on create view function
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qibla, container, false);

        /*
         * Assign text views and image views
         */
        tvLocation = view.findViewById(R.id.loc);
        imgCompass = view.findViewById(R.id.image_compass);
        imgCursor = view.findViewById(R.id.image_cursor);
        tvHeading = view.findViewById(R.id.heading);
        tvHint = view.findViewById(R.id.hint);

        // Assign shared preference settings
        settings = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        // Assign longitude and latitude
         longDouble = Double.parseDouble(settings.getString("long", "0.0"));
         latDouble = Double.parseDouble(settings.getString("lat", "0.0"));

         /*
          * Listen on address (location) change on shared preference
          */
        sharedPreferenceChangeListener = (sharedPreferences, key) -> {
            if (key.equals("address")) {
                tvLocation.setText(settings.getString("address", "Loading"));
                longDouble = Double.parseDouble(settings.getString("long", "0.0"));
                latDouble = Double.parseDouble(settings.getString("lat", "0.0"));
            }
        };

        // Initialize your android device sensor capabilities
        mSensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);

        return view;
    }

    /*
     * Overriding function on Resume to listen on sensor change and register a listener to shared preference
     */
    @Override
    public void onResume() {
        super.onResume();

        // For the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        // Listen on shared preference
        settings.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        // Update location textview
        tvLocation.setText(settings.getString("address", "Loading"));
    }

    /*
     * Overriding function on Pause to stop listening on sensor change and unregister a listener to shared preference
     */
    @Override
    public void onPause() {
        super.onPause();

        // To stop the listener and save battery
        mSensorManager.unregisterListener(this);
        // Unregister to shared preference
        settings.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }


    /*
     * This function is triggered when readings on sensor changes
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {

        // Get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        // Angle of qibla
        float x =-degree+calculateQibla(latDouble,longDouble);

        // Set headings textview
        tvHeading.setText("Heading: " + x + " degrees");


        // Create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentCompassDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // How long the animation will take place
        ra.setDuration(210);

        // Set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        imgCompass.startAnimation(ra);

        // Set the rtation of the cursor
        imgCursor.setRotation(x);

        // Update current degree
        currentCompassDegree = -degree;

    }

    /*
     * This function is triggered when accuracy changes
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        // If accuracy is bad, change the rounded text background from green to red
        if(SensorManager.SENSOR_STATUS_UNRELIABLE == accuracy || SensorManager.SENSOR_STATUS_ACCURACY_LOW == accuracy){
            tvHint.setBackgroundColor(R.drawable.bg_rounded_error);
        }
    }

    /*
     * This method is used to calculate qibla
     */
    public float calculateQibla(double latitude, double longitude){
        double phiK = 21.4 * Math.PI / 180.0;
        double lambdaK = 39.8 * Math.PI/180.0;
        double phi = latitude*Math.PI/180.0;
        double lambda = longitude*Math.PI/180.0;
        double psi = 180.0/Math.PI * Math.atan2(Math.sin(lambdaK-lambda),Math.cos(phi)*Math.tan(phiK)-Math.sin(phi)*Math.cos(lambdaK-lambda));
        return Math.round(psi);
    }
}