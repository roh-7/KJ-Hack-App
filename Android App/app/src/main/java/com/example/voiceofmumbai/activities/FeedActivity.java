package com.example.voiceofmumbai.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.fragment.FeedFragment;
import com.example.voiceofmumbai.fragment.MapFragment;
import com.example.voiceofmumbai.fragment.ProfileFragment;

public class FeedActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomBar;
    private Toolbar toolbar;
    private TextView toolbarLocalityText, toolbarExtensionText;
    private FeedFragment feedFragment;
    private MapFragment mapFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        bottomBar = findViewById(R.id.bottom_bar);
        toolbar = findViewById(R.id.toolbar);
        feedFragment = new FeedFragment();
        mapFragment = new MapFragment();
        profileFragment = new ProfileFragment();
        toolbarExtensionText = toolbar.findViewById(R.id.toolbar_title_extension);
        toolbarLocalityText = toolbar.findViewById(R.id.toolbar_title_locality);
        bottomBar.setOnNavigationItemSelectedListener(this);
	    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FeedFragment()).commit();
    }

    public void setLocality(String locality) {
    	toolbarLocalityText.setText(locality);
    }

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
	{
		Fragment fragment = null;
		switch(menuItem.getItemId())
		{
			case R.id.menu_feed:
				fragment = feedFragment;
				break;

			case R.id.menu_map:
				fragment = mapFragment;
				break;

			case R.id.menu_profile:
				fragment = profileFragment;
				break;
		}
		if(fragment!=null)
		{
			getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
		}
		return true;
	}

	public void showMapElementsOnToolbar() {
		if(toolbarLocalityText != null)
		{
			toolbarExtensionText.setVisibility(View.VISIBLE);
			toolbarLocalityText.setVisibility(View.VISIBLE);
		}

	}

	public void hideMapElementsOnToolbar() {
		if(toolbarLocalityText != null)
		{
			toolbarExtensionText.setVisibility(View.GONE);
			toolbarLocalityText.setVisibility(View.GONE);
		}

	}
}
