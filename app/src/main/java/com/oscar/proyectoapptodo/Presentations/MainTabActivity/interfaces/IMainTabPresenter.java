package com.oscar.proyectoapptodo.Presentations.MainTabActivity.interfaces;

import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;

/**
 * Created by daniel on 14/06/2017.
 */

public interface IMainTabPresenter {
    void onCreate();
    void onStart();
    void onStop();
    void onDestroy();

    void onEventMainThread(ErrorData event);
    void onEventMainThread(SuccessData event);

    void closeSession();
    void consumeWebService(String url, int typeResponse);
}
