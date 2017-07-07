package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.implementations;

import com.oscar.proyectoapptodo.AppController;
import com.oscar.proyectoapptodo.Managers.VolleyManager;
import com.oscar.proyectoapptodo.Models.ErrorData;
import com.oscar.proyectoapptodo.Models.SuccessData;
import com.oscar.proyectoapptodo.Models.Weather;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces.IClimaDialogFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daniel on 15/06/2017.
 */

public class ClimaDialogFragmentPresenter implements IClimaDialogFragmentPresenter {
    private ClimaDialogFragment climaDialogFragment;
    private EventBus eventBus = EventBus.getDefault();
    private Weather weather = new Weather();


    public ClimaDialogFragmentPresenter(ClimaDialogFragment climaDialogFragment) {
        this.climaDialogFragment = climaDialogFragment;
    }

    @Override
    public void onCreateDialog() {
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
    @Subscribe
    public void onEventMainThread(ErrorData event) {

    }

    @Override
    @Subscribe
    public void onEventMainThread(SuccessData event) {
        weather =parseJsonWeather(event.getMessage());
        //climaDialogFragment.setDataWheater(weather.toString());
        climaDialogFragment.setDataWheater(weather);

    }

    @Override
    public void consumeWebServiceWeather(String longitud, String latitud) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid=072ede38bdcd58804ca961ad3104bae8";
        AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));
    }

    private Weather parseJsonWeather(String responseJson){
        Weather weather = new Weather();

        try {
            JSONObject jsonObject = new JSONObject(responseJson);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");

            weather.setMain(jsonArray.getJSONObject(0).getString("main"));
            weather.setDescription(jsonArray.getJSONObject(0).getString("description"));
            weather.setIcon(jsonArray.getJSONObject(0).getString("icon"));
            weather.setTemp(jsonObject.getJSONObject("main").getString("temp"));
            weather.setName(jsonObject.getString("name"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
//3222011335
