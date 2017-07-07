package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.implementations;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.oscar.proyectoapptodo.Managers.ImageLoaders.GlideImageLoader;
import com.oscar.proyectoapptodo.Managers.ImageLoaders.ImageLoader;
import com.oscar.proyectoapptodo.Models.Weather;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces.IClimaDialogFragmentView;
import com.oscar.proyectoapptodo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by daniel on 15/06/2017.
 */

public class ClimaDialogFragment extends DialogFragment implements IClimaDialogFragmentView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.txTitle)
    TextView txTitle;
    @Bind(R.id.txtDescription)
    TextView txtDescription;
    @Bind(R.id.txtTemp)
    TextView txtTemp;
    @Bind(R.id.txtCiudad)
    TextView txtCiudad;
    @Bind(R.id.iconWeather)
    ImageView iconWeather;

    private EventBus eventBus = EventBus.getDefault();
    private ClimaDialogFragmentPresenter climaDialogFragmentPresenter;

    private ImageLoader imageLoader;
    private GoogleApiClient googleApiClient;
    private Location location;
    private double latitude;
    private double longitude;

    //private String url = "http://api.openweathermap.org/data/2.5/weather?q=medellin&appid=072ede38bdcd58804ca961ad3104bae8";

    public ClimaDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        climaDialogFragmentPresenter = new ClimaDialogFragmentPresenter(this);
        climaDialogFragmentPresenter.onCreateDialog();
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        return createClimaDialog();


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onSart", "fragment");
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "fragement");
        climaDialogFragmentPresenter.onDestroy();
    }

    public AlertDialog createClimaDialog() {
        AlertDialog.Builder climaDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_clima_dialog, null);
        ButterKnife.bind(this, view);
        climaDialogBuilder.setView(view);
        //climaDialogFragmentPresenter.consumeWebServiceWeather("-74.23", "4.71");
        climaDialogFragmentPresenter.consumeWebServiceWeather(Double.toString(longitude), Double.toString(latitude));

        //AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));

        return climaDialogBuilder.create();
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        //AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));
        return rootView;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setDataWheater(String message) {
        //textView.setText(message);
    }

    @Override
    public void setDataWheater(Weather wheaterData) {
        imageLoader = new GlideImageLoader(getContext());
        String urlIcon = "http://openweathermap.org/img/w/" + wheaterData.getIcon() + ".png";

        imageLoader.load(iconWeather, urlIcon);
        txTitle.setText(wheaterData.getMain());
        txtDescription.setText(wheaterData.getDescription());

        txtCiudad.setText(String.format(getResources().getString(R.string.dialogFragment_ciudad), wheaterData.getName()));
        Float aux = Float.parseFloat(wheaterData.getTemp());
        String aux1 = String.format("Tempe %1$.2f °C", aux);
        Log.e("format", aux1);

        float tempe = (float) (Float.parseFloat(wheaterData.getTemp()) - 273.15);
        //int tempe = Integer.parseInt(wheaterData.getTemp())-273;
        String newTemp = String.format("%.2f °C", tempe);
        txtTemp.setText(newTemp);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("onConnected", "fragment");

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(getActivity()
                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }
            return;
        }else{
            Log.e("Connected priemero", "aqui estamos");
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null){
                Log.e("Connected", "aqui estamos");
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Log.e("latitude", Double.toString(latitude));
                Log.e("longitud", Double.toString(longitude));
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }*/
}
