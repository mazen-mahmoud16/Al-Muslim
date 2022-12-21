package com.example.elaislami.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.example.elaislami.Listener.BroadCastListener;

/*
 * Here is a broadcast receiver that is triggered by the change of the location service of the android phone
 */
public class LocationReceiver extends BroadcastReceiver {

    /*
     * Here are some attributes that will be used
     */
    private static int no_of_times = 0;
    private final BroadCastListener listener;

    // Here is the constructor
    public LocationReceiver(BroadCastListener listener){
        this.listener = listener;
    }

    // Overriding function on receive
    @Override
    public void onReceive(Context context, Intent intent) {

        // If location changed
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {

            // get system services
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // Check whether both the GPS and network is activated
            if (isGpsEnabled && isNetworkEnabled) {

                // As we will go to this receiver 3 times in the same location changed, so make a condition to only enter it once in a 3 times
                no_of_times++;
                if(no_of_times%3==0){
                    // call a function to re-compute the location
                    listener.regenerateLocation();
                }
            }
        }
    }
}