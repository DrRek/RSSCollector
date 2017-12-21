package com.example.alberto.rssreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alberto.rssreader.ArticleWebPage;
import com.example.alberto.rssreader.Interface.DeleteListener;
import com.example.alberto.rssreader.Interface.ItemClickListener;
import com.example.alberto.rssreader.Model.RSSObject;
import com.example.alberto.rssreader.Model.Site;
import com.example.alberto.rssreader.R;
import com.example.alberto.rssreader.SitePage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alberto on 09/12/2017.
 */

class SiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView nomeTv, urlTv;
    public ImageButton deleteBtn;
    private ItemClickListener itemClickListener;

    public SiteViewHolder(View itemView){
        super(itemView);

        nomeTv = itemView.findViewById(R.id.sito);
        urlTv = itemView.findViewById(R.id.url);
        deleteBtn = itemView.findViewById(R.id.delete);


        //Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        deleteBtn.setOnClickListener(this);
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

public class SiteAdapter extends RecyclerView.Adapter<SiteViewHolder>{
    private List<Site> sites;
    private Context mContext;
    private LayoutInflater inflater;
    private DeleteListener dl;

    public SiteAdapter(List<Site> sites, DeleteListener dl, Context mContext) {
        this.sites = sites;
        this.mContext = mContext;
        this.dl = dl;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sites,parent,false);
        return new SiteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SiteViewHolder holder, int position) {
        holder.nomeTv.setText(sites.get(position).getNome());
        holder.urlTv.setText(sites.get(position).getUrl());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    if(view.getId()== R.id.delete){
                        SharedPreferences preferenze = mContext.getSharedPreferences("com.example.alberto.rssreader", MODE_PRIVATE);
                        Gson gson = new Gson();
                        String json = preferenze.getString("sitiScelti", "");
                        List<Site> sites;
                        if(!json.equals("")) {
                            Type listType = new TypeToken<List<Site>>() {}.getType();
                            sites = gson.fromJson(json, listType);
                            sites.remove(sites.get(position));
                            SharedPreferences.Editor editor = preferenze.edit();
                            System.out.println("Numero di siti aggiunti: " + sites.size());
                            String result = gson.toJson(sites);
                            editor.putString("sitiScelti", result);
                            editor.apply();
                            dl.onDelete();
                        }

                        Toast.makeText(mContext, "Feed rimosso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent();
                        Context c = view.getContext();
                        intent.setClass(c, SitePage.class);
                        intent.putExtra("url", sites.get(position).getUrl());
                        intent.putExtra("nome", sites.get(position).getNome());
                        c.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }
}
