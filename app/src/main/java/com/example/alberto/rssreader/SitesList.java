package com.example.alberto.rssreader;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.alberto.rssreader.Adapter.FeedAdapter;
import com.example.alberto.rssreader.Adapter.SiteAdapter;
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

public class SitesList extends AppCompatActivity{

    private List<Site> sites;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sites_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Senior RSS");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleMarginStart(72);
        setSupportActionBar(toolbar);

        sites = new ArrayList<>();

        sites.add(new Site("ansa", "http://www.ansa.it/sito/ansait_rss.xml"));
        sites.add(new Site("blogo", "http://www.blogo.it/rss"));
        /*
        SharedPreferences preferenze = getApplicationContext().getSharedPreferences("com.example.alberto.rssreader", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferenze.getString("sitiScelti", "");
        Type listType = new TypeToken<List<Site>>() {}.getType();
        sites = gson.fromJson(json, listType);*/

        recyclerView = findViewById(R.id.recyclerView3);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadSites();

    }

    private void loadSites(){
        SiteAdapter adapter = new SiteAdapter(sites,getBaseContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
