package com.example.alberto.rssreader;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Alberto on 10/12/2017.
 */
public class ArticleWebPage extends AppCompatActivity {

    Toolbar toolbar;
    WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_web_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Senior RSS");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleMarginStart(72);
        setSupportActionBar(toolbar);

        webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getExtras().getString("url"));

    }
}
