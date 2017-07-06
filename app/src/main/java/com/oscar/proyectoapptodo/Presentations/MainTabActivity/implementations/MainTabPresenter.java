package com.oscar.proyectoapptodo.Presentations.MainTabActivity.implementations;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.oscar.proyectoapptodo.AppController;
import com.oscar.proyectoapptodo.Managers.VolleyManager;
import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.interfaces.IMainTabPresenter;
import com.oscar.proyectoapptodo.Presentations.login.implementations.LoginActivity;
import com.oscar.proyectoapptodo.Utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by daniel on 14/06/2017.
 */

public class MainTabPresenter implements IMainTabPresenter {
    MainTabActivity mainTabActivity;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EventBus eventBus = EventBus.getDefault();


    public MainTabPresenter(MainTabActivity mainTabActivity) {
        this.mainTabActivity = mainTabActivity;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void closeSession() {
        auth.signOut();
        Intent intent = new Intent(mainTabActivity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainTabActivity.startActivity(intent);
    }

    @Override
    public void consumeWebService(String url, int typeResponse) {
        switch (typeResponse){
            case Constants.typeResponseWebService.TYPE_JS0N:
                AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));
                break;
            case Constants.typeResponseWebService.TYPE_XML:
                AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestString(url));

                break;
            default:
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(ErrorData event) {
        switch (event.getType()){
            case Constants.typeResponseWebService.RESPONSE_NOT_OK:
                Log.e("presenter error", event.getMessage());
                break;
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(SuccessData event) {
        switch (event.getType()){
            case Constants.typeResponseWebService.RESPONSE_OK:
                Log.e("presenter", event.getMessage());
                break;
        }
    }
}
