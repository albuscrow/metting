package com.hjtech.secretary.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

public class AppConfig {
	public static Integer SCREENWIDTH;
	public static Integer SCREENHEIGHT;
	public static float SCREEN_DENSITY;
	public static boolean INIT_FINISHED;
	public static boolean HAS_WIFI;
	private static Context context;


	public static void initConfig(Context context) {
		AppConfig.context = context;
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		SCREENWIDTH = metric.widthPixels; // 屏幕宽度（像素）
		SCREENHEIGHT = metric.heightPixels; // 屏幕高度（像素）
		SCREEN_DENSITY = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0）
		INIT_FINISHED = true;
	}

	public static boolean isWifiConnect() {  
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);  
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
		HAS_WIFI = mWifi.isConnected();
		return HAS_WIFI; 
	} 

}
