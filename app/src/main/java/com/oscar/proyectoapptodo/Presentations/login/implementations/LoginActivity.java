package com.oscar.proyectoapptodo.Presentations.login.implementations;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.implementations.MainTabActivity;
import com.oscar.proyectoapptodo.Presentations.login.interfaces.ILoginView;
import com.oscar.proyectoapptodo.R;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    @Bind(R.id.txt_Login_Email)
    EditText txtLoginEmail;
    @Bind(R.id.txt_Login_Pass)
    EditText txtLoginPass;
    @Bind(R.id.btn_iniciar_sesion)
    Button btnIniciarSesion;
    @Bind(R.id.btn_regitrar_usuario)
    Button btnRegitrarUsuario;
    @Bind(R.id.progressBarLogin)
    ProgressBar progressBarLogin;
    @Bind(R.id.loginFacebook_button)
    LoginButton loginFacebookButton;

    private LoginPresenter loginPresenter;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnRegitrarUsuario.setOnClickListener(this);
        btnIniciarSesion.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.onCreate();

        callbackManager = CallbackManager.Factory.create();
        loginFacebookButton.setPublishPermissions(Arrays.asList("publish_actions"));
        loginFacebookInit();
    }

    private void loginFacebookInit() {
        loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("facebook", "onSucces");
                navigateToMainScreen();
            }

            @Override
            public void onCancel() {
                Log.e("facebook", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("facebook", "onError");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_iniciar_sesion:
                if (loginPresenter.validateEmail(txtLoginEmail.getText().toString()) && loginPresenter.validatePassword(txtLoginPass.getText().toString())) {
                    loginPresenter.validateLogin(txtLoginEmail.getText().toString(), txtLoginPass.getText().toString());
                }

                break;
            case R.id.btn_regitrar_usuario:
                Log.e("boton registrar", "aqui o");
                if (loginPresenter.validateEmail(txtLoginEmail.getText().toString()) && loginPresenter.validatePassword(txtLoginPass.getText().toString())) {
                    loginPresenter.registerNewUser(txtLoginEmail.getText().toString(), txtLoginPass.getText().toString());
                }
                break;
            default:
        }
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainTabActivity.class));
    }

    @Override
    public void loginError(String error) {
        createDialogAlert(error);
    }

    @Override
    public void newUserSuccess() {

    }

    @Override
    public void newUserError(String error) {
        createDialogAlert(error);
    }

    @Override
    public void showErrorEmail(String message) {
        txtLoginEmail.setError(message);
        txtLoginEmail.clearFocus();
    }

    @Override
    public void showErrorPass(String message) {
        txtLoginPass.setError(message);
        txtLoginEmail.clearFocus();
    }

    private void setInputs(Boolean enable) {
        txtLoginEmail.setEnabled(enable);
        txtLoginPass.setEnabled(enable);
        btnIniciarSesion.setEnabled(enable);
        btnRegitrarUsuario.setEnabled(enable);
    }

    void createDialogAlert(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(getResources().getString(R.string.btnNext), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
