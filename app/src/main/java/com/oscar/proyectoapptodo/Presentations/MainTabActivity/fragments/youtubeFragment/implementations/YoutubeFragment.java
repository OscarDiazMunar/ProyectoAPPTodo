package com.oscar.proyectoapptodo.Presentations.MainTabActivity.fragments.youtubeFragment.implementations;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oscar.proyectoapptodo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragment extends Fragment {


    @Bind(R.id.webYoutube)
    WebView webYoutube;

    private WebSettings webSettings;
    private WebViewClient webViewClient;

    public YoutubeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        ButterKnife.bind(this, view);

        webViewClient = new WebViewClient();
        webSettings = webYoutube.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webYoutube.loadUrl("https://www.youtube.com/watch?v=qWV2kM1laIc");
        //webYoutube.loadUrl("https://developer.android.com/training/basics/fragments/communicating.html");
        webYoutube.setWebViewClient(webViewClient);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.e("destroyview", "fragmetn");
        webViewClient.onPageFinished(webYoutube, "https://www.youtube.com/watch?v=qWV2kM1laIc");

        //webYoutube.destroy();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "fragmetn");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("ondetach", "fragmetn");
    }
}
