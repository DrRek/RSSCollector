package com.example.alberto.rssreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alberto.rssreader.Adapter.FeedAdapter;
import com.example.alberto.rssreader.Adapter.SiteAdapter;
import com.example.alberto.rssreader.Interface.DeleteListener;
import com.example.alberto.rssreader.Model.RSSObject;
import com.example.alberto.rssreader.Model.Site;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 12/12/2017.
 */

public class SitesList extends AppCompatActivity implements DeleteListener{

    private List<Site> sites;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sites_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("RSS Collector");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume(){
        super.onResume();

        System.out.println("onResume");

        sites = new ArrayList<>();

        SharedPreferences preferenze = getApplicationContext().getSharedPreferences("com.example.alberto.rssreader", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferenze.getString("sitiScelti", "");
        if(null!=json && !json.equals("")) {
            Type listType = new TypeToken<List<Site>>() {
            }.getType();
            sites = gson.fromJson(json, listType);
            recyclerView = findViewById(R.id.recyclerView3);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            loadSites();
        }
    }

    private void loadSites(){
        SiteAdapter adapter = new SiteAdapter(sites, this,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void pickSites(View v){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext() ,SitesPicker.class);
        startActivity(intent);
    }

    @Override
    public void onDelete() {

        onResume();
    }
}
