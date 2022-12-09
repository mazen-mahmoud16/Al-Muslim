package com.example.elaislami.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import com.example.elaislami.Listener.BroadCastListener;

public class LocationReceiver extends BroadcastReceiver {

    private final static String TAG = "LocationProviderChanged";
    static int no_of_times = 0;

    boolean isGpsEnabled;
    boolean isNetworkEnabled;

    private final BroadCastListener listener;

    public LocationReceiver(BroadCastListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Log.i(TAG, "Location Providers changed");

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGpsEnabled && isNetworkEnabled) {
                no_of_times++;
                if(no_of_times%3==0){
                    listener.regenerateLocation();
                }
            }
        }
    }
}