package com.oscar.proyectoapptodo.Presentations.login.interfaces;

import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;

/**
 * Created by daniel on 13/06/2017.
 */

public interface ILoginPresenter {
    void onCreate();
    void onStart();
    void onStop();
    void onDestroy();

    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void authListener();
    void onEventMainThread(ErrorData event);
    void onEventMainThread(SuccessData event);

    boolean validateEmail(String email);
    boolean validatePassword(String pass);
}
