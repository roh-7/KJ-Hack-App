package com.example.voiceofmumbai.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.activities.FeedActivity;
import com.example.voiceofmumbai.activities.IssueCreationActivity;
import com.example.voiceofmumbai.adapter.FeedAdapter;
import com.example.voiceofmumbai.model.FeedItem;
import com.example.voiceofmumbai.utils.Constants;
import com.example.voiceofmumbai.utils.UtilityMethods;
import com.mapfit.android.MapView;
import com.mapfit.android.MapfitMap;
import com.mapfit.android.OnMapReadyCallback;
import com.mapfit.android.annotations.Marker;
import com.mapfit.android.annotations.MarkerOptions;
import com.mapfit.android.annotations.callback.OnMarkerAddedCallback;
import com.mapfit.android.geocoder.Geocoder;
import com.mapfit.android.geocoder.GeocoderCallback;
import com.mapfit.android.geocoder.model.Address;
import com.mapfit.android.geometry.LatLng;
import com.mapfit.android.location.LocationPriority;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment
{
	private TextView toolbarExtensionText;
	private TextView toolbarLocalityText;

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == LOCATION_PERM_REQUEST_CODE) {
			if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				mapView.getMapAsync(new OnMapReadyCallback() {
					@Override
					public void onMapReady(MapfitMap mapfitMap) {
						MapFragment.this.mapfitMap = mapfitMap;
						if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
							return;
						mapfitMap.getMapOptions().setUserLocationEnabled(true, LocationPriority.HIGH_ACCURACY);
						mapfitMap.getMapOptions().setZoomControlVisible(false);
						MarkerOptions options = new MarkerOptions().position(mapfitMap.getCenter());
						LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
						manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
							@Override
							public void onLocationChanged(Location location) {
								MapFragment.this.mapfitMap.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
								MapFragment.this.mapfitMap.setZoom(12);
								MapFragment.this.mapfitMap.getMapOptions().setMaxZoom(14);
								MapFragment.this.mapfitMap.getMapOptions().setMinZoom(12);

								// TODO API ke success me ye aayega --v
								FeedAdapter adapter = new FeedAdapter(getContext(), feeds);
								feedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
								feedRecycler.setAdapter(adapter);
							}

							@Override
							public void onStatusChanged(String s, int i, Bundle bundle) {

							}

							@Override
							public void onProviderEnabled(String s) {

							}

							@Override
							public void onProviderDisabled(String s) {

							}
						}, Looper.getMainLooper());
						mapfitMap.addMarker(options);
						progressBar.setVisibility(View.GONE);
						TransitionManager.beginDelayedTransition(rootLayout);
						bottomSheetBehavior.setPeekHeight(200);
					}
				});

			}
		}
	}

	public MapFragment()
	{

	}

	private final int LOCATION_PERM_REQUEST_CODE = 129;
	private BottomSheetBehavior bottomSheetBehavior;
	MapfitMap mapfitMap;
	private ViewGroup rootLayout;
	private ProgressBar progressBar;
	private TextView progressText;
	private MapView mapView;
	private RecyclerView feedRecycler;
	private NestedScrollView nsv;
	private ArrayList<FeedItem> feeds = new ArrayList<>();
	private RequestQueue requestQueue;
	private FloatingActionButton fab;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_map, container, false);
		rootLayout = view.findViewById(android.R.id.content);
		progressBar = view.findViewById(R.id.feed_sheet_progress);
		mapView = view.findViewById(R.id.mapView);
		progressText = view.findViewById(R.id.feed_sheet_progress_text);
		nsv = view.findViewById(R.id.feed_sheet);
		bottomSheetBehavior = BottomSheetBehavior.from(nsv);
		feedRecycler = view.findViewById(R.id.feed_sheet_recycler);
		fab = view.findViewById(R.id.create_report_fab);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				startActivity(new Intent(getContext(), IssueCreationActivity.class));
			}
		});
		requestQueue = Volley.newRequestQueue(getContext());

		if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
				ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERM_REQUEST_CODE);
			}
		} else {
			mapView.getMapAsync(new OnMapReadyCallback() {
				@Override
				public void onMapReady(MapfitMap mapfitMap) {
					MapFragment.this.mapfitMap = mapfitMap;
					if(getContext() == null) return;
					if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
						return;
					mapfitMap.getMapOptions().setUserLocationEnabled(true, LocationPriority.HIGH_ACCURACY);
					mapfitMap.getMapOptions().setZoomControlVisible(false);
					if(getContext() == null) return;
					LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
					manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
						@Override
						public void onLocationChanged(Location location) {
							MapFragment.this.mapfitMap.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
							MapFragment.this.mapfitMap.setZoom(12);
							MapFragment.this.mapfitMap.getMapOptions().setMaxZoom(14);
							MapFragment.this.mapfitMap.getMapOptions().setMinZoom(12);

							MapFragment.this.mapfitMap.addMarker(new MarkerOptions().position(MapFragment.this.mapfitMap.getCenter()));

							LatLng ne = MapFragment.this.mapfitMap.getLatLngBounds().getNorthEast();
							LatLng sw = MapFragment.this.mapfitMap.getLatLngBounds().getSouthWest();

							JSONObject params = new JSONObject();
							try
							{
								params.put("lat_min", sw.getLat());
								params.put("lat_max", ne.getLat());
								params.put("long_min", sw.getLng());
								params.put("long_max", ne.getLng());
							}
							catch (JSONException e)
							{
								e.printStackTrace();
							}

							JsonObjectRequest request = new JsonObjectRequest(
									Request.Method.POST,
									Constants.API_URL + "nearby",
									params,
									new Response.Listener<JSONObject>()
									{
										@Override
										public void onResponse(JSONObject response)
										{
											feeds.clear();
											Log.d("LULZ", response.toString());
											JSONArray postArray = response.optJSONArray("post");
											for(int i = 0; i < postArray.length(); i++) {
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
														postObject.optDouble("location_long")

														);
												Log.d("LULZ", item.getCategory());
												feeds.add(item);
												Log.d("LULZW", item.getLocation_lat() + "-_-" + item.getLocation_long());
												LatLng l1 = new LatLng(item.getLocation_lat(), item.getLocation_long());
												Log.d("LULZ", l1.getLat() + "::" + l1.getLng());
												LatLng l = new LatLng(19.0827, 72.9184);
												MarkerOptions options = new MarkerOptions().position(l);
												MapFragment.this.mapfitMap.addMarker(options, new OnMarkerAddedCallback() {
													@Override
													public void onMarkerAdded(Marker marker)
													{
														Log.d("LULZ", "Marker added re");
													}

													@Override
													public void onError(Exception e)
													{
														Log.d("LULZ", e.getMessage() + "eXceptioon");
														e.printStackTrace();
													}
												});
											}
											onAPISuccess();
											Log.d("LULZ", "Success " + feeds.size());
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

							requestQueue.add(request);

						}

						@Override
						public void onStatusChanged(String s, int i, Bundle bundle) {

						}

						@Override
						public void onProviderEnabled(String s) {

						}

						@Override
						public void onProviderDisabled(String s) {

						}
					}, Looper.getMainLooper());
				}
			});

		}
		return view;
	}

	private void onAPISuccess()
	{
		if(getContext() == null) return;
		bottomSheetBehavior.setPeekHeight((int) UtilityMethods.convertDpToPixel(16.0f, getContext()) + progressBar.getHeight());
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

		new Geocoder().reverseGeocode(MapFragment.this.mapfitMap.getCenter(), new GeocoderCallback() {
			@Override
			public void onSuccess(List<Address> list)
			{
				if(getActivity() == null) return;
				((FeedActivity) getActivity()).showMapElementsOnToolbar();
				((FeedActivity) getActivity()).setLocality(list.get(0).getNeighborhood());
			}

			@Override
			public void onError(String s, Exception e)
			{

			}
		});

		progressBar.setVisibility(View.GONE);
		progressText.setVisibility(View.GONE);
		feedRecycler.setVisibility(View.VISIBLE);

		FeedAdapter adapter = new FeedAdapter(getContext(), feeds);
		feedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
		feedRecycler.setAdapter(adapter);
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		if(getView() != null && getActivity() != null)
			((FeedActivity) getActivity()).showMapElementsOnToolbar();
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		if(getActivity() != null)
			((FeedActivity) getActivity()).hideMapElementsOnToolbar();
	}
}
