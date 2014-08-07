package com.hjtech.secretary.common;

import com.hjtech.secretary.R;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.android.tpush.XGPushManager;

import android.app.Application;
import android.content.Context;

public class MettingApplication extends Application {
	public static Context context;
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		
		initImageLoader();
		MTUserManager.init(context);
		MTCommon.init(context);
    	AppConfig.initConfig(context);
    	
    	XGPushManager.registerPush(context);	
	}
	
	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory()
		.cacheOnDisc()
		.showImageForEmptyUri(R.drawable.common_default_image)
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.build();
		ImageLoader.getInstance().init(config);
	}
}
