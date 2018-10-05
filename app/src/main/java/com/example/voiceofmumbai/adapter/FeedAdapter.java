package com.example.voiceofmumbai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.model.FeedItem;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
{
	private Context context;
	private ArrayList<FeedItem> feedItemArrayList;

	public FeedAdapter(Context context, ArrayList<FeedItem> feedItemArrayList)
	{
		this.context = context;
		this.feedItemArrayList = feedItemArrayList;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
	{
		return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.feed_item, viewGroup, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
	{
		viewHolder.title.setText(feedItemArrayList.get(i).getTitle());
		viewHolder.time.setText(feedItemArrayList.get(i).getTimestamp());
		viewHolder.user_name.setText(feedItemArrayList.get(i).getUser_name());
	}

	@Override
	public int getItemCount()
	{
		return feedItemArrayList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView user_name,title,time;

		ViewHolder(@NonNull View itemView)
		{
			super(itemView);
			user_name = itemView.findViewById(R.id.feed_profile_name_text);
			title = itemView.findViewById(R.id.feed_title);
			time = itemView.findViewById(R.id.feed_time);
		}
	}
}
