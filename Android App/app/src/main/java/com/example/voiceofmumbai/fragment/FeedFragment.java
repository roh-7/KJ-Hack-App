package com.example.voiceofmumbai.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voiceofmumbai.R;
import com.example.voiceofmumbai.adapter.ViewPagerAdapter;

public class FeedFragment extends Fragment
{
	public FeedFragment()
	{

	}

	private ViewPager viewPager;
	private TabLayout tabLayout;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_feed,container,false);

		ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
		adapter.addFragment(new TrendingFeed(), "Trending");
		adapter.addFragment(new OfficialFeed(), "Official");

		viewPager = view.findViewById(R.id.viewpager);
		tabLayout = view.findViewById(R.id.tablayout);

		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);

		return view;
	}
}
