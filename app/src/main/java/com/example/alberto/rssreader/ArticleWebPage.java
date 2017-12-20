package com.example.alberto.rssreader;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by Alberto on 10/12/2017.
 */
public class ArticleWebPage extends AppCompatActivity {
//
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

        /*
        webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getExtras().getString("url"));*/

        TextView titletv = findViewById(R.id.title);
        titletv.setText(Html.fromHtml(getIntent().getExtras().getString("title")));

        TextView datetv = findViewById(R.id.date);
        datetv.setText(Html.fromHtml(getIntent().getExtras().getString("date")));

        TextView contenttv = findViewById(R.id.tv);
        contenttv.setText(Html.fromHtml(getIntent().getExtras().getString("content")));

        TextView urltv = findViewById(R.id.view_source);
        contenttv.setText(Html.fromHtml(getIntent().getExtras().getString("url")));
    }

    public void viewSource(View v){
        TextView tv = (TextView) v;
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tv.getText().toString()));
        startActivity(myIntent);
    }
}
