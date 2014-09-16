package com.hjtech.secretary.common;

import com.hjtech.secretary.R;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.android.tpush.XGPushManager;

import android.app.Application;
import android.content.Context;

/**
 * The Class MettingApplication.
 * 初始化一些全局的东西
 * @author albuscrow
 */
public class MettingApplication extends Application {
	
	/** The context. */
	public static Context context;
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
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
	
	/**
	 * Inits the image loader.
	 */
	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//		.cacheInMemory()
//		.cacheOnDisc()
		.showImageForEmptyUri(R.drawable.common_default_image)
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.build();
		ImageLoader.getInstance().init(config);
	}
}
