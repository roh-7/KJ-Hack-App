package com.example.voiceofmumbai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.voiceofmumbai.FeedTypeQuery;
import com.example.voiceofmumbai.R;

import javax.annotation.Nonnull;

import okhttp3.OkHttpClient;

public class LoginActivity extends AppCompatActivity
{

	public static final String BASE_URL = "https://kjsce-test.herokuapp.com/v1alpha1/graphql";

	private ApolloClient apolloClient;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.build();

		apolloClient = ApolloClient.builder()
				.okHttpClient(okHttpClient)
				.serverUrl(BASE_URL)
				.build();

		apolloClient.query(
				FeedTypeQuery.builder()
				.limit(5)
				.build()
		).enqueue(new ApolloCall.Callback<FeedTypeQuery.Data>() {
			@Override
			public void onResponse(@Nonnull Response<FeedTypeQuery.Data> response)
			{
				final StringBuffer buffer = new StringBuffer();
				assert response.data()!=null;
				for(FeedTypeQuery.Profile profile : response.data().profiles())
				{
					buffer.append(" id: ");
					buffer.append(profile.id());
					buffer.append(" name: ");
					buffer.append(profile.name());
					buffer.append("\n\n");
					Log.v("response",buffer.toString());
				}
			}

			@Override
			public void onFailure(@Nonnull ApolloException e)
			{
				e.printStackTrace();
				Log.v("error","onFailure");
			}
		});

		// FIXME Login Later, Starting FeedActivity directly
		startActivity(new Intent(this, FeedActivity.class));
	}
}
