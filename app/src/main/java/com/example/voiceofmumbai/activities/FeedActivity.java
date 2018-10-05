package com.example.voiceofmumbai.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.voiceofmumbai.R;
import com.mapfit.android.MapView;
import com.mapfit.android.Mapfit;
import com.mapfit.android.MapfitMap;
import com.mapfit.android.OnMapReadyCallback;

public class FeedActivity extends AppCompatActivity {
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERM_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadMap();
            }
        }
    }

    private void loadMap() {
        Mapfit.getInstance(this, MAPFIT_API_KEY);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapfitMap mapfitMap) {
            }
        });
    }

    public static final String MAPFIT_API_KEY = "591dccc4e499ca0001a4c6a4173e2eced6004e09b218301fc9f257bc";
    private final int LOCATION_PERM_REQUEST_CODE = 129;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mapView = findViewById(R.id.mapView);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERM_REQUEST_CODE);
            }
        } else {
            loadMap();
        }
    }
}
