package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces;

import android.location.Location;
import android.location.LocationManager;

import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;

/**
 * Created by daniel on 15/06/2017.
 */

public interface IClimaDialogFragmentPresenter {

    void onCreateDialog();
    void onStart();
    void onStop();
    void onDestroy();
    void locationStar();

    void onEventMainThread(ErrorData event);
    void onEventMainThread(SuccessData event);

    void consumeWebServiceWeather(String longitud, String latitud);
    void consumeWebServiceWeather(Double longitud, Double latitud);
}
