package com.example.voiceofmumbai.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.voiceofmumbai.R;
import com.mapfit.android.MapView;
import com.mapfit.android.MapfitMap;
import com.mapfit.android.OnMapReadyCallback;
import com.mapfit.android.annotations.Marker;
import com.mapfit.android.annotations.MarkerOptions;
import com.mapfit.android.annotations.callback.OnMarkerAddedCallback;
import com.mapfit.android.geometry.LatLng;
import com.mapfit.android.location.LocationPriority;

public class FeedActivity extends AppCompatActivity {

    private final int LOCATION_PERM_REQUEST_CODE = 129;
    private MapView mapView;
    public MapfitMap mapfitMap;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERM_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(FeedActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    return;
            }
        }
    }

    private ConstraintLayout contentLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    private ViewGroup rootLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        rootLayout = findViewById(android.R.id.content);
        progressBar = findViewById(R.id.progress_bar);
        mapView = findViewById(R.id.mapView);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERM_REQUEST_CODE);
            }
        } else {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapfitMap mapfitMap) {
                    FeedActivity.this.mapfitMap = mapfitMap;
                    if(ContextCompat.checkSelfPermission(FeedActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        return;
                    mapfitMap.getMapOptions().setUserLocationEnabled(true, LocationPriority.HIGH_ACCURACY);
                    mapfitMap.getMapOptions().setZoomControlVisible(false);
                    MarkerOptions options = new MarkerOptions().position(mapfitMap.getCenter());
                    LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d("LULZ", location.getLatitude() + ", " + location.getLongitude());
                            FeedActivity.this.mapfitMap.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
                            FeedActivity.this.mapfitMap.setZoom(12);
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
        contentLayout = findViewById(R.id.content_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(contentLayout);
    }
}
