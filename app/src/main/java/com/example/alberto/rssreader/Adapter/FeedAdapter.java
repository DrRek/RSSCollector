package com.example.alberto.rssreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alberto.rssreader.ArticleWebPage;
import com.example.alberto.rssreader.Interface.ItemClickListener;
import com.example.alberto.rssreader.Model.RSSObject;
import com.example.alberto.rssreader.R;

/**
 * Created by Alberto on 09/12/2017.
 */

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle, txtPubDate, txtContent;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView){
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView) itemView.findViewById(R.id.txtPubDate);
        txtContent = (TextView) itemView.findViewById(R.id.txtContent);

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
public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{
    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    Intent intent = new Intent();
                    Context c = view.getContext();
                    intent.setClass(c,ArticleWebPage.class);
                    intent.putExtra("url", Uri.parse(rssObject.getItems().get(position).getLink()).toString());
                    intent.putExtra("content", rssObject.getItems().get(position).getContent());
                    //Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                   //  mContext.startActivity(browserIntent);
                    c.startActivity(intent);


               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.getItems().size();
    }
}
