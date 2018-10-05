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
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.view.MenuItem;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.fragment.FeedFragment;
import com.example.voiceofmumbai.fragment.MapFragment;
import com.example.voiceofmumbai.fragment.ProfileFragment;

public class FeedActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setOnNavigationItemSelectedListener(this);
    }

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
	{
		Fragment fragment = null;
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
