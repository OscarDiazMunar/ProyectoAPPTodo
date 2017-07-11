package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.implementations;

import android.app.Dialog;
import android.app.DialogFragment;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oscar.proyectoapptodo.Managers.ImageLoaders.GlideImageLoader;
import com.oscar.proyectoapptodo.Managers.ImageLoaders.ImageLoader;
import com.oscar.proyectoapptodo.Models.Weather;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces.IClimaDialogFragmentView;
import com.oscar.proyectoapptodo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by daniel on 15/06/2017.
 */

public class ClimaDialogFragment extends DialogFragment implements IClimaDialogFragmentView {

    @Bind(R.id.txTitle)
    TextView txTitle;
    @Bind(R.id.txtDescription)
    TextView txtDescription;
    @Bind(R.id.txtTemp)
    TextView txtTemp;
    @Bind(R.id.txtCiudad)
    TextView txtCiudad;
    @Bind(R.id.iconWeather)
    ImageView iconWeather;
    @Bind(R.id.txtCoord)
    TextView txtCoord;
    @Bind(R.id.progressBarClima)
    ProgressBar progressBarClima;

    private EventBus eventBus = EventBus.getDefault();
    private ClimaDialogFragmentPresenter climaDialogFragmentPresenter;

    private ImageLoader imageLoader;
    private double latitude;
    private double longitude;

    public ClimaDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        climaDialogFragmentPresenter = new ClimaDialogFragmentPresenter(this, getContext(), getActivity());
        climaDialogFragmentPresenter.onCreateDialog();
        climaDialogFragmentPresenter.locationStar();

        return createClimaDialog();


    }

    @Override
    public void onStart() {
        super.onStart();
        climaDialogFragmentPresenter.onStart();
        Log.e("onSart", "fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "fragement");
        climaDialogFragmentPresenter.onDestroy();
    }

    public AlertDialog createClimaDialog() {
        AlertDialog.Builder climaDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_clima_dialog, null);
        ButterKnife.bind(this, view);
        climaDialogBuilder.setView(view);

        return climaDialogBuilder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setDataWheater(String message) {
    }

    @Override
    public void setDataWheater(Weather wheaterData) {
        imageLoader = new GlideImageLoader(getContext());
        String urlIcon = "http://openweathermap.org/img/w/" + wheaterData.getIcon() + ".png";

        imageLoader.load(iconWeather, urlIcon);
        txTitle.setText(wheaterData.getMain());
        txtDescription.setText(wheaterData.getDescription());

        txtCiudad.setText(String.format(getResources().getString(R.string.dialogFragment_ciudad), wheaterData.getName()));

        float tempe = (float) (Float.parseFloat(wheaterData.getTemp()) - 273.15);

        String newTemp = String.format("%.2f °C", tempe);
        txtTemp.setText(newTemp);
        String msg = "Longitude:%s \n Latitude:%s";
        msg = String.format(msg, longitude, latitude);
        txtCoord.setText(msg);

    }

    @Override
    public void setLatitudeLongitude(Location location) {
        if (location != null) {
            Log.e("Connected", "aqui estamos");
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            climaDialogFragmentPresenter.consumeWebServiceWeather(longitude, latitude);
        }
    }

    @Override
    public void showProgress() {
        progressBarClima.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarClima.setVisibility(View.GONE);
    }

}
