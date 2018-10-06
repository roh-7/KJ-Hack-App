package com.example.voiceofmumbai.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class OfficialFeed extends Fragment
{

	ArrayList<FeedItem> items = new ArrayList<>();
	RecyclerView recyclerView;
	FeedAdapter adapter;

	ProgressDialog dialog;

	public OfficialFeed()
	{
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_official_feed,container,false);
		recyclerView = view.findViewById(R.id.official_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new FeedAdapter(getActivity(),items);
		dialog = new ProgressDialog(getActivity());
//		dialog.setCancelable(false);
//		dialog.setTitle("Loading");
//		dialog.setMessage("Please wait");

		JsonObjectRequest request = new JsonObjectRequest(
				Request.Method.POST,
				Constants.API_URL + "official",
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
							items.add(item);
						}
						dialog.dismiss();
						adapter.notifyDataSetChanged();
						Log.d("LULZ", "Success " + items.size());

					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						error.printStackTrace();
						dialog.dismiss();
					}
				}
		);
		Volley.newRequestQueue(getActivity()).add(request);

		if(dialog!=null)
		{
			dialog.dismiss();
		}

		recyclerView.setAdapter(adapter);

		return view;
	}

}
