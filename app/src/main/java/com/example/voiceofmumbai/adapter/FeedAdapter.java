package com.example.voiceofmumbai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.model.FeedItem;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<FeedItem> feedItemArrayList;

    public FeedAdapter(Context context, ArrayList<FeedItem> feedItemArrayList) {
        this.context = context;
        this.feedItemArrayList = feedItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.feed_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return feedItemArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
