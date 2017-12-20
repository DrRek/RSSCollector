package com.example.alberto.rssreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alberto.rssreader.ArticleWebPage;
import com.example.alberto.rssreader.Interface.ItemClickListener;
import com.example.alberto.rssreader.Model.RSSObject;
import com.example.alberto.rssreader.Model.Site;
import com.example.alberto.rssreader.R;
import com.example.alberto.rssreader.SitePage;

import java.util.List;

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

    public SiteAdapter(List<Site> sites, Context mContext) {
        this.sites = sites;
        this.mContext = mContext;
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
                    Intent intent = new Intent();
                    Context c = view.getContext();
                    intent.setClass(c,SitePage.class);
                    intent.putExtra("url", sites.get(position).getUrl());
                    c.startActivity(intent);


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }
}
