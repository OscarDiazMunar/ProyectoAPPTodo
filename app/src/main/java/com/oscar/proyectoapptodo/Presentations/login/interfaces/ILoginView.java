package com.oscar.proyectoapptodo.Presentations.login.interfaces;

/**
 * Created by daniel on 13/06/2017.
 */

public interface ILoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);

    void showErrorEmail(String message);
    void showErrorPass(String message);
}
