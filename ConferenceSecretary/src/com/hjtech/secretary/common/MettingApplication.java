package com.hjtech.secretary.common;

import com.hjtech.secretary.R;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MettingApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader();
		MTUserManager.init(getApplicationContext());
		MTCommon.init(getApplicationContext());
    	AppConfig.initConfig(getApplicationContext());
	}
	
	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory()
		.cacheOnDisc()
		.showImageForEmptyUri(R.drawable.common_logo)
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.build();
		ImageLoader.getInstance().init(config);
	}
}
