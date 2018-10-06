package com.example.voiceofmumbai.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.utils.Constants;
import com.mapfit.android.geocoder.Geocoder;
import com.mapfit.android.geocoder.GeocoderCallback;
import com.mapfit.android.geocoder.model.Address;
import com.mapfit.android.geometry.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class IssueCreationActivity extends AppCompatActivity
{

	private TextInputEditText title, content, category;
	private Button submitButton;
	private ProgressBar progressBar;
	private ImageView checkLocationButton;
	private TextView locationText;
	private boolean isHit = false;
	private Double loclat, loclong;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_issue_creation);

		title = findViewById(R.id.create_report_title);
		content = findViewById(R.id.create_report_content);
		category = findViewById(R.id.create_report_category);

		submitButton = findViewById(R.id.create_report_submit_button);
		progressBar = findViewById(R.id.create_report_progress);

		checkLocationButton = findViewById(R.id.check_location_button);
		locationText = findViewById(R.id.location_Text);

		if (ContextCompat.checkSelfPermission(IssueCreationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			return;

		LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
			@Override
			public void onLocationChanged(final Location location)
			{
				new Geocoder().reverseGeocode(new LatLng(location.getLatitude(), location.getLongitude()), new GeocoderCallback() {
					@Override
					public void onSuccess(List<Address> list)
					{
						locationText.setText(list.get(0).getNeighborhood());
						loclong = location.getLongitude();
						loclat = location.getLatitude();
						isHit = true;
					}

					@Override
					public void onError(String s, Exception e)
					{
						locationText.setText("Failed to Get location, try again");
						isHit = false;
					}
				});
			}

			@Override
			public void onStatusChanged(String s, int i, Bundle bundle)
			{

			}

			@Override
			public void onProviderEnabled(String s)
			{

			}

			@Override
			public void onProviderDisabled(String s)
			{

			}
		}, Looper.getMainLooper());

		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				if(title.getText().toString().isEmpty()) {
					title.setError("Please enter a valid title");
					return;
				}
				if(title.getText().toString().isEmpty()) {
					title.setError("Please enter a valid title");
					return;
				}
				if(title.getText().toString().isEmpty())
				{
					title.setError("Please enter a valid title");
					return;
				}
				if(!isHit) {
					Toast.makeText(IssueCreationActivity.this, "Could not get location, submission aborted", Toast.LENGTH_LONG).show();
					return;
				}
				progressBar.setVisibility(View.VISIBLE);
				submitButton.setText("Submitting");
				submitButton.setOnClickListener(null);


						JSONObject params = new JSONObject();
				try
				{
					/*
					title,content,content_type,category,ward,location_lat,location_long,user_name,user_id
					 */
					params.put("title", title.getText().toString());
					params.put("content", content.getText().toString());
					params.put("content_type", "text");
					params.put("category", category.getText().toString());
					params.put("ward", "G/N");
					params.put("location_lat", loclat);
					params.put("location_long", loclong);
					params.put("user_name", "Anonymous");
					params.put("user_id", "99");
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}


				JsonObjectRequest request = new JsonObjectRequest(
						Request.Method.GET,
						Constants.API_URL + "newPost",
						params,
						new Response.Listener<JSONObject>()
						{
							@Override
							public void onResponse(JSONObject response)
							{
								Log.d(getClass().getCanonicalName(), response.toString());
								Toast.makeText(IssueCreationActivity.this, "Complaint submitted successfully", Toast.LENGTH_LONG).show();
								finish();
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error)
							{
								error.printStackTrace();
							}
						}

				);

				Volley.newRequestQueue(IssueCreationActivity.this).add(request);
			}
		});
	}
}
