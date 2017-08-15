package com.oscar.proyectoapptodo.Presentations.WebYoutube.Implementations;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oscar.proyectoapptodo.Presentations.WebYoutube.Interfaces.IWebYoutubePresenter;
import com.oscar.proyectoapptodo.Presentations.WebYoutube.Interfaces.IWebYoutubeView;

/**
 * Created by Usuario on 15/08/2017.
 */

public class WebYoutubePresenter implements IWebYoutubePresenter{
    private WebYoutubeActivity webYoutubeActivity;
    private IWebYoutubeView iWebYoutubeView;
    private WebSettings webSettings;
    private WebViewClient webViewClient;

    public WebYoutubePresenter(WebYoutubeActivity webYoutubeActivity) {
        this.webYoutubeActivity = webYoutubeActivity;
        this.iWebYoutubeView = webYoutubeActivity;
    }


    @Override
    public void showwebViewYoutube() {
        iWebYoutubeView.showUrlinWebView();
    }

    @Override
    public void onDestroy() {
        webYoutubeActivity.onDestroy();
    }
}
