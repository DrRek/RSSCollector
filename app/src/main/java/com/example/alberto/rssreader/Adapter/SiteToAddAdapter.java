package com.example.alberto.rssreader.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alberto.rssreader.Interface.ItemClickListener;
import com.example.alberto.rssreader.Model.Site;
import com.example.alberto.rssreader.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alberto on 09/12/2017.
 */

class SiteToAddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView nomeTv, urlTv;
    public Switch aSwitch;
    private ItemClickListener itemClickListener;

    public SiteToAddViewHolder(View itemView){
        super(itemView);

        nomeTv = itemView.findViewById(R.id.sito1);
        urlTv = itemView.findViewById(R.id.url1);
        aSwitch = itemView.findViewById(R.id.select);


        //Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v){
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class SiteToAddAdapter extends RecyclerView.Adapter<SiteToAddViewHolder>{
    private Context mContext;
    private LayoutInflater inflater;
    private List<Site> defaultSites;
    private List<Site> chosenSites;

    public SiteToAddAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        defaultSites = new ArrayList<>();
        defaultSites.add(new Site("Ansa.it", "http://www.ansa.it/sito/ansait_rss.xml"));
        defaultSites.add(new Site("Blogo.it", "http://www.blogo.it/rss"));
        defaultSites.add(new Site("Corriere.it", "http://xml2.corriereobjects.it/rss/homepage.xml"));
        defaultSites.add(new Site("NY Times", "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"));

        SharedPreferences preferenze = mContext.getSharedPreferences("com.example.alberto.rssreader", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferenze.getString("sitiScelti", "");

        if(null!=json&&!json.equals("")) {
            Type listType = new TypeToken<List<Site>>() {}.getType();
            List<Site> sites = gson.fromJson(json, listType);

            for(Site s : sites){
                if(defaultSites.contains(s)){
                    defaultSites.remove(s);
                }
            }
        }
        chosenSites = new ArrayList<>();
    }

    @Override
    public SiteToAddViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.site_to_add,parent,false);
        return new SiteToAddViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SiteToAddViewHolder holder, int position) {
        holder.nomeTv.setText(defaultSites.get(position).getNome());
        holder.urlTv.setText(defaultSites.get(position).getUrl());
        holder.aSwitch.setChecked(false);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    holder.aSwitch.toggle();
                    if(chosenSites.contains(defaultSites.get(position))){
                        System.out.println("Rimuovo dai temp: " + defaultSites.get(position).getNome());
                        chosenSites.remove(defaultSites.get(position));
                    } else {
                        System.out.println("Aggiungo dai temp: " + defaultSites.get(position).getNome());
                        chosenSites.add(defaultSites.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return defaultSites.size();
    }

    public void saveChanges(){
        SharedPreferences preferenze = mContext.getSharedPreferences("com.example.alberto.rssreader", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferenze.getString("sitiScelti", "");
        List<Site> sites = new ArrayList<>();
        if(!json.equals("")) {
            Type listType = new TypeToken<List<Site>>() {}.getType();
            sites = gson.fromJson(json, listType);
        }
        SharedPreferences.Editor editor = preferenze.edit();
        sites.addAll(chosenSites);
        System.out.println("Numero di siti aggiunti: " + sites.size());
        String result = gson.toJson(sites);
        editor.putString("sitiScelti", result);
        editor.apply();

        Toast.makeText(mContext, "Feed aggiunti", Toast.LENGTH_SHORT).show();
    }
}
