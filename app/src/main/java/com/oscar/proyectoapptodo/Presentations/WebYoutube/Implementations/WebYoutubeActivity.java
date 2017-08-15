package com.oscar.proyectoapptodo.Presentations.WebYoutube.Implementations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oscar.proyectoapptodo.Presentations.WebYoutube.Interfaces.IWebYoutubeView;
import com.oscar.proyectoapptodo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebYoutubeActivity extends AppCompatActivity implements IWebYoutubeView{

    @Bind(R.id.webviewYoutube)
    WebView webviewYoutube;

    private WebYoutubePresenter presenter;
    private WebSettings webSettings;
    private WebViewClient webViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_youtube);
        ButterKnife.bind(this);

        presenter = new WebYoutubePresenter(this);
        presenter.showwebViewYoutube();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webviewYoutube.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //webviewYoutube.destroy();
    }

    @Override
    public void showUrlinWebView() {
        webViewClient = new WebViewClient();
        webSettings = webviewYoutube.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webviewYoutube.loadUrl(getResources().getString(R.string.link_video));
        webviewYoutube.setWebViewClient(webViewClient);
    }
}
