package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.implementations;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oscar.proyectoapptodo.AppController;
import com.oscar.proyectoapptodo.Managers.VolleyManager;
import com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.climaDialogFragment.interfaces.IClimaDialogFragmentView;
import com.oscar.proyectoapptodo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by daniel on 15/06/2017.
 */

public class ClimaDialogFragment extends DialogFragment implements IClimaDialogFragmentView{

    private EventBus eventBus = EventBus.getDefault();
    private ClimaDialogFragmentPresenter climaDialogFragmentPresenter;

    @Bind(R.id.textView)
    TextView textView;

    //private String url = "http://api.openweathermap.org/data/2.5/weather?q=medellin&appid=072ede38bdcd58804ca961ad3104bae8";

    public ClimaDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        climaDialogFragmentPresenter = new ClimaDialogFragmentPresenter(this);
        climaDialogFragmentPresenter.onCreateDialog();
        return createClimaDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy","fragement");
        climaDialogFragmentPresenter.onDestroy();
    }

    public AlertDialog createClimaDialog() {
        AlertDialog.Builder climaDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_clima_dialog, null);
        ButterKnife.bind(this, view);
        climaDialogBuilder.setView(view);
        climaDialogFragmentPresenter.consumeWebServiceWeather("-74.23","4.71");

        //AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));

        return climaDialogBuilder.create();
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        //AppController.getInstance().addToRequestQueue(VolleyManager.makeRequestJson(url));
        return rootView;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setDataWheater(String message) {
        textView.setText(message);
    }
}
