package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces;

import android.location.Location;

import com.oscar.proyectoapptodo.Models.Weather;

/**
 * Created by daniel on 15/06/2017.
 */

public interface IClimaDialogFragmentView {
    void setDataWheater(String message);
    void setDataWheater(Weather wheaterData);
    void setLatitudeLongitude(Location location);

    void showProgress();
    void hideProgress();
}
