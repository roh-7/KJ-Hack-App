package com.example.voiceofmumbai.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.activities.FeedActivity;
import com.mapfit.android.MapView;
import com.mapfit.android.MapfitMap;
import com.mapfit.android.OnMapReadyCallback;
import com.mapfit.android.annotations.MarkerOptions;
import com.mapfit.android.geometry.LatLng;
import com.mapfit.android.location.LocationPriority;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment
{
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == LOCATION_PERM_REQUEST_CODE) {
			if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			}
		}
	}

	public MapFragment()
	{

	}

	private final int LOCATION_PERM_REQUEST_CODE = 129;
	private BottomSheetBehavior bottomSheetBehavior;
	MapfitMap mapfitMap;
	private ConstraintLayout contentLayout;
	private ViewGroup rootLayout;
	private ProgressBar progressBar;
	private MapView mapView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_map, container, false);
		rootLayout = view.findViewById(android.R.id.content);
		progressBar = view.findViewById(R.id.progress_bar);
		mapView = view.findViewById(R.id.mapView);
		contentLayout = view.findViewById(R.id.content_layout);
		bottomSheetBehavior = BottomSheetBehavior.from(contentLayout);

		if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
				ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERM_REQUEST_CODE);
			}
		} else {
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
		return view;
	}
}
