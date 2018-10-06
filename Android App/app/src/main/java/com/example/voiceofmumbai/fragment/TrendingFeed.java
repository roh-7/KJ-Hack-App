package com.example.voiceofmumbai.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.adapter.FeedAdapter;
import com.example.voiceofmumbai.model.FeedItem;
import com.example.voiceofmumbai.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrendingFeed extends Fragment
{
	private ArrayList<FeedItem> feedItems = new ArrayList<>();
	private RecyclerView feedRecycler;

	private FeedAdapter adapter;

	ProgressDialog dialog;

	public TrendingFeed()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_trending_feed, container, false);
		feedRecycler = view.findViewById(R.id.feed_sheet_recycler);
		dialog = new ProgressDialog(getActivity());
		adapter = new FeedAdapter(getActivity(),feedItems);
		JsonObjectRequest request = new JsonObjectRequest(
				Request.Method.POST,
				Constants.API_URL + "trending",
				null,
				new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						Log.d("LULZ", response.toString());
						JSONArray postArray = response.optJSONArray("post");
						for (int i = 0; i < postArray.length(); i++)
						{
							JSONObject postObject = postArray.optJSONObject(i);
							FeedItem item = new FeedItem(
									postObject.optString("id"),
									postObject.optString("title"),
									postObject.optString("content"),
									postObject.optString("content_type"),
									postObject.optString("category"),
									postObject.optString("ward"),
									postObject.optString("status"),
									postObject.optString("status_note"),
									postObject.optString("timestamp"),
									postObject.optString("user_name"),
									postObject.optDouble("location_lat"),
									postObject.optDouble("location_log")

							);
							Log.d("LULZ", item.getCategory());
							feedItems.add(item);
						}
						adapter.notifyDataSetChanged();
						Log.d("LULZ", "Success " + feedItems.size());

					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						error.printStackTrace();
					}
				}
		);
		Volley.newRequestQueue(getActivity()).add(request);
		feedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		feedRecycler.setAdapter(adapter);
		return view;
	}

}
