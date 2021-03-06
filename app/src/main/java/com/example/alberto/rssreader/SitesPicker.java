package com.example.alberto.rssreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.alberto.rssreader.Adapter.SiteAdapter;
import com.example.alberto.rssreader.Adapter.SiteToAddAdapter;
import com.example.alberto.rssreader.Model.Site;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 12/12/2017.
 */

public class SitesPicker extends AppCompatActivity {

    private List<Site> sites;
    private RecyclerView recyclerView;
    SiteToAddAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sites_picker);

        Toolbar toolbar = findViewById(R.id.toolbar2);
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

        recyclerView = findViewById(R.id.recyclerView2);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadSites();

    }

    private void loadSites(){
        adapter = new SiteToAddAdapter(getBaseContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void updateSitesList(View v){
        adapter.saveChanges();
        finish();
    }
}
