package com.example.alberto.rssreader;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alberto.rssreader.Adapter.FeedAdapter;
import com.example.alberto.rssreader.Common.HTTPDataHandler;
import com.example.alberto.rssreader.Model.RSSObject;
import com.google.gson.Gson;

public class SitePage extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;
    TextView site;

    //RSS link
    private String RSS_link;
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_page);

        site = (TextView) findViewById(R.id.sitename);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Senior RSS");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleMarginStart(72);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RSS_link = getIntent().getExtras().getString("url");

        loadRSS();
    }

    private void loadRSS(){
        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String,String,String>(){

            ProgressDialog mDialog = new ProgressDialog(SitePage.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
               String result;
                HTTPDataHandler http= new HTTPDataHandler();
                result= http.getHTTPData(params[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson(). fromJson(s, RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                site.setText(rssObject.getFeed().getTitle());
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);
        loadRSSAsync.execute(url_get_data.toString());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh)
            loadRSS();
        return true;
    }
}
