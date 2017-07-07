package com.oscar.proyectoapptodo.Presentations.login.implementations;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oscar.proyectoapptodo.AppController;
import com.oscar.proyectoapptodo.Managers.VolleyManager;
import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;
import com.oscar.proyectoapptodo.Presentations.login.interfaces.ILoginPresenter;
import com.oscar.proyectoapptodo.R;
import com.oscar.proyectoapptodo.Utils.Constants;
import com.oscar.proyectoapptodo.Utils.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daniel on 13/06/2017.
 */

public class LoginPresenter implements ILoginPresenter {
    private LoginActivity loginActivity;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ErrorData errorData = new ErrorData();
    private SuccessData successData = new SuccessData();
    private EventBus eventBus = EventBus.getDefault();

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onStart() {
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);

    }

    @Override
    public void validateLogin(String email, String password) {
        loginActivity.disableInputs();
        loginActivity.showProgress();

        String url = "https://uphill-leg.000webhostapp.com/proyectoapptodo/usuarios/login";
        AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJsonPOST(url, construirJsonObject(email, password)));
        /*
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    successData.setType(Constants.successType.SUCCES_LOGIN_CONNECTION_USER);
                    successData.setMessage(Constants.succesMessage.successLoginConnectionUser);
                    eventBus.post(successData);
                }else {
                    errorData.setType(Constants.errorType.ERROR_LOGIN_CONNECTION_USER);
                    //errorData.setMessage(loginActivity.getResources().getString(R.string.userError));
                    errorData.setMessage(task.getException().getMessage());
                    Log.e("task execption", task.getException().getMessage());
                    eventBus.post(errorData);
                }
            }
        });*/
    }

    @Override
    public void registerNewUser(String email, String password) {
        loginActivity.disableInputs();
        loginActivity.showProgress();

        String url = "https://uphill-leg.000webhostapp.com/proyectoapptodo/usuarios/registro";
        AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJsonPOST(url, construirJsonObject(email, password)));

        /*
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    successData.setType(Constants.successType.SUCCES_LOGIN_CREATE_USER);
                    successData.setMessage(Constants.succesMessage.successLoginCreateUser);
                    eventBus.post(successData);
                }else {
                    errorData.setType(Constants.errorType.ERROR_LOGIN_CREATE_USER);
                    errorData.setMessage(loginActivity.getResources().getString(R.string.userExist));
                    eventBus.post(errorData);
                }

            }
        });*/
    }

    @Override
    public void authListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                /*if (user != null){
                    successData.setType(Constants.successType.SUCCES_LOGIN_CREATE_USER);
                    successData.setMessage(Constants.succesMessage.SUCCES_LOGIN_CREATE_USER);
                    eventBus.post(successData);
                }else {
                    errorData.setType(Constants.errorType.ERROR_LOGIN_CLOSE_SESSION);
                    errorData.setMessage(loginActivity.getResources().getString(R.string.userExist));
                    eventBus.post(errorData);
                }*/
            }
        };

    }

    @Override
    @Subscribe
    public void onEventMainThread(ErrorData event) {
        switch (event.getType()){
            case Constants.errorType.ERROR_LOGIN_CREATE_USER:
                loginActivity.enableInputs();
                loginActivity.hideProgress();
                loginActivity.newUserError(event.getMessage());
                Log.e("Sesion 1 error", event.getMessage());
                break;
            case Constants.errorType.ERROR_LOGIN_CONNECTION_USER:
                loginActivity.enableInputs();
                loginActivity.hideProgress();
                loginActivity.loginError(event.getMessage());
                Log.e("Sesion 2 error", event.getMessage());
                break;
            case Constants.errorType.ERROR_LOGIN_EMAIL:
                loginActivity.showErrorEmail(event.getMessage());
                break;
            case Constants.errorType.ERROR_LOGIN_PASS:
                loginActivity.showErrorPass(event.getMessage());
            default:
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(SuccessData event) {
        switch (event.getType()){
            case Constants.successType.SUCCES_LOGIN_CONNECTION_USER:
                Log.e("Sesion 1 success", event.getMessage());
                loginActivity.navigateToMainScreen();
                break;
            case Constants.successType.SUCCES_LOGIN_CREATE_USER:
                loginActivity.enableInputs();
                loginActivity.hideProgress();
                loginActivity.navigateToMainScreen();
                Log.e("Sesion 2 success", event.getMessage());
                break;
            default:
        }
    }

    @Override
    public boolean validateEmail(String email) {
        boolean isValid = true;
        if (email.isEmpty()){
            errorData.setMessage(loginActivity.getResources().getString(R.string.is_Empty));
            errorData.setType(Constants.errorType.ERROR_LOGIN_EMAIL);
            eventBus.post(errorData);
            isValid = false;
        }
        else if (!Validator.isValidEmail(email)){
            errorData.setMessage(loginActivity.getResources().getString(R.string.email_notvalid));
            errorData.setType(Constants.errorType.ERROR_LOGIN_EMAIL);
            eventBus.post(errorData);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validatePassword(String pass) {

        boolean isValid = true;
        if (pass.isEmpty()){
            errorData.setMessage(loginActivity.getResources().getString(R.string.is_Empty));
            errorData.setType(Constants.errorType.ERROR_LOGIN_PASS);
            eventBus.post(errorData);
            isValid = false;
        }
        else if (!Validator.isMoreSix(pass)){
            errorData.setMessage(loginActivity.getResources().getString(R.string.more_six));
            errorData.setType(Constants.errorType.ERROR_LOGIN_PASS);
            eventBus.post(errorData);
            isValid = false;
        }
        return isValid;
    }

    private JSONObject construirJsonObject(String user, String pass){
        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("nombre", user);
            jsonData.put("contrasena", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("eldatajson", jsonData.toString());
        return  jsonData;

    }
}
