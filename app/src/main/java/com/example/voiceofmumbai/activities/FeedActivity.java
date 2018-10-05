package com.example.voiceofmumbai.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.fragment.FeedFragment;
import com.example.voiceofmumbai.fragment.MapFragment;
import com.example.voiceofmumbai.fragment.ProfileFragment;

public class FeedActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private final int LOCATION_PERM_REQUEST_CODE = 129;
//    private MapView mapView;

    private BottomNavigationView bottomBar;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERM_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                loadMap();
            }
        }
    }

//    private void loadMap() {
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(MapfitMap mapfitMap) {
//
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        bottomBar = findViewById(R.id.bottom_bar);
//        mapView = findViewById(R.id.mapView);
        bottomBar.setOnNavigationItemSelectedListener(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERM_REQUEST_CODE);
            }
        } else {

//            loadMap();
        }
    }

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
	{
		Fragment fragment = null;
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch(menuItem.getItemId())
		{
			case R.id.menu_feed:
				fragment = new FeedFragment();
				break;

			case R.id.menu_map:
				fragment = new MapFragment();
				break;

			case R.id.menu_profile:
				fragment = new ProfileFragment();
				break;
		}
		if(fragment!=null)
		{
			getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
		}
		return true;
	}
}
