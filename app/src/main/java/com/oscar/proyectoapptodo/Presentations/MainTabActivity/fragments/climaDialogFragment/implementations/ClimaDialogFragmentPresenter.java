package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.implementations;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.oscar.proyectoapptodo.AppController;
import com.oscar.proyectoapptodo.BuildConfig;
import com.oscar.proyectoapptodo.Managers.MyLocationListener;
import com.oscar.proyectoapptodo.Managers.VolleyManager;
import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;
import com.oscar.proyectoapptodo.Models.Weather;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces.IClimaDialogFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daniel on 15/06/2017.
 */

public class ClimaDialogFragmentPresenter implements IClimaDialogFragmentPresenter {
    private ClimaDialogFragment climaDialogFragment;
    private EventBus eventBus = EventBus.getDefault();
    private Weather weather = new Weather();
    private Context context;
    private Activity activity;
    private LocationManager locationManager;

    public ClimaDialogFragmentPresenter(ClimaDialogFragment climaDialogFragment, Context context, Activity activity) {
        this.climaDialogFragment = climaDialogFragment;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onCreateDialog() {
        eventBus.register(this);
    }

    @Override
    public void onStart() {
        climaDialogFragment.showProgress();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        stopUsingGPS();
    }

    @Override
    public void locationStar() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("onConnected", "fragment");

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(activity
                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            return;
        }else{
            try {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                final boolean gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                String provider_info = LocationManager.GPS_PROVIDER;
                if (!gpsEnable) {
                    Intent settingGps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(settingGps);
                }

                if (!provider_info.isEmpty()) {

                    Log.e("provider info", provider_info);

                    if (locationManager != null) {
                        Log.e("location manager", "aqui estamos");

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
                    }
                }

            }catch (Exception e){

            }
        }
    }

    private void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(ErrorData event) {

    }

    @Override
    @Subscribe
    public void onEventMainThread(SuccessData event) {
        weather =parseJsonWeather(event.getMessage());
        //climaDialogFragment.setDataWheater(weather.toString());
        climaDialogFragment.hideProgress();
        climaDialogFragment.setDataWheater(weather);

    }

    @Override
    public void consumeWebServiceWeather(String longitud, String latitud) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid="+BuildConfig.openweathermap_id;

        Log.e("consumeweb", url);
        AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));
    }

    @Override
    public void consumeWebServiceWeather(Double longitud, Double latitud) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid="+BuildConfig.openweathermap_id;
        url = String.format(url, latitud,longitud);
        Log.e("url", url);
        AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));
    }

    private Weather parseJsonWeather(String responseJson){
        Weather weather = new Weather();

        try {
            JSONObject jsonObject = new JSONObject(responseJson);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");

            weather.setMain(jsonArray.getJSONObject(0).getString("main"));
            weather.setDescription(jsonArray.getJSONObject(0).getString("description"));
            weather.setIcon(jsonArray.getJSONObject(0).getString("icon"));
            weather.setTemp(jsonObject.getJSONObject("main").getString("temp"));
            weather.setName(jsonObject.getString("name"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            climaDialogFragment.setLatitudeLongitude(location);
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
    };
}
