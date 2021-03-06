package com.example.alberto.rssreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

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
        setContentView(R.layout.article);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("RSS Collector");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*
        webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getExtras().getString("url"));*/

        TextView titletv = findViewById(R.id.title);
        titletv.setText(getIntent().getExtras().getString("title"));

        TextView datetv = findViewById(R.id.date);
        datetv.setText(getIntent().getExtras().getString("date"));

        TextView contenttv = findViewById(R.id.tv);
        contenttv.setText(Html.fromHtml(getIntent().getExtras().getString("content")));
    }

    public void viewSource(View v){
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getExtras().getString("url")));
        startActivity(myIntent);
    }

}
