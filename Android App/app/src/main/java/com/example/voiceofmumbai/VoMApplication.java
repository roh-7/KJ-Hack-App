package com.example.voiceofmumbai;

import android.app.Application;

import com.example.voiceofmumbai.utils.Constants;
import com.mapfit.android.Mapfit;

public class VoMApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Mapfit.getInstance(this, Constants.MAPFIT_API_KEY);

	}
}
