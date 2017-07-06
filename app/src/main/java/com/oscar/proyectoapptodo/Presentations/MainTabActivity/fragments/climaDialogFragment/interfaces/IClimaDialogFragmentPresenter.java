package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces;

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

    void onEventMainThread(ErrorData event);
    void onEventMainThread(SuccessData event);

    void consumeWebServiceWeather(String longitud, String latitud);
}
