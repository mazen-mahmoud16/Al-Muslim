package com.example.elaislami.Fragment;

import static android.content.Context.SENSOR_SERVICE;

import android.content.SharedPreferences;
import android.graphics.Color;
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

public class QiblaFragment extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    private ImageView image2;
    private TextView loc;
    public static final String PREFS_NAME = "MyPreferenceFile";

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;
    TextView hint;

    double longDouble;
    double latDouble;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qibla, container, false);

        loc=view.findViewById(R.id.loc);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        loc.setText(settings.getString("address", "Loading"));

         longDouble = Double.parseDouble(settings.getString("long", "0.0"));
         latDouble = Double.parseDouble(settings.getString("lat", "0.0"));

        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("address")) {
                    loc.setText(settings.getString("address", "Loading"));

                     longDouble = Double.parseDouble(settings.getString("long", "0.0"));
                     latDouble = Double.parseDouble(settings.getString("lat", "0.0"));
                }
            }
        };

        // Inflate the layout for this fragment

        image = (ImageView) view.findViewById(R.id.image_compass);
        image2 = (ImageView) view.findViewById(R.id.image_cursor);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) view.findViewById(R.id.heading);


        hint = (TextView) view.findViewById(R.id.hint);



        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        float x =-degree+calculateQibla(latDouble,longDouble);

        tvHeading.setText("Heading: " + Float.toString(x) + " degrees");


        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);// create a rotation animation (reverse turn degree degrees)

        // Start the animation
        image.startAnimation(ra);

        image2.setRotation(x);


        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(mSensorManager.SENSOR_STATUS_UNRELIABLE == accuracy || mSensorManager.SENSOR_STATUS_ACCURACY_LOW == accuracy){
            hint.setBackgroundColor(R.drawable.rounded_btn_error);
        }
    }

    public float calculateQibla(double latitude, double longitude){
        double phiK = 21.4 * Math.PI / 180.0;
        double lambdaK = 39.8 * Math.PI/180.0;
        double phi = latitude*Math.PI/180.0;
        double lambda = longitude*Math.PI/180.0;
        double psi = 180.0/Math.PI * Math.atan2(Math.sin(lambdaK-lambda),Math.cos(phi)*Math.tan(phiK)-Math.sin(phi)*Math.cos(lambdaK-lambda));
        return Math.round(psi);
    }
}