package com.oscar.proyectoapptodo.Managers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


/**
 * Created by Usuario on 10/07/2017.
 */

public class MyLocationListener implements LocationListener {
    private Context context;
    private Location location;

    public MyLocationListener(Context context) {
        this.context = context;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
