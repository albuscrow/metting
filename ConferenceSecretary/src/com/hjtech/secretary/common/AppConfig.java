package com.hjtech.secretary.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

/**
 * The Class AppConfig.
 * 储存手机的一些信息
 * @author albuscrow
 */
public class AppConfig {
	
	/** The screenwidth. */
	public static Integer SCREENWIDTH;
	
	/** The screenheight. */
	public static Integer SCREENHEIGHT;
	
	/** The screen density. */
	public static float SCREEN_DENSITY;
	
	/** The init finished. */
	public static boolean INIT_FINISHED;
	
	/** The context. */
	private static Context context;


	/**
	 * Inits the config.
	 * 
	 * @param context
	 *            the context
	 */
	public static void initConfig(Context context) {
		AppConfig.context = context;
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		SCREENWIDTH = metric.widthPixels; // 屏幕宽度（像素）
		SCREENHEIGHT = metric.heightPixels; // 屏幕高度（像素）
		SCREEN_DENSITY = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0）
		INIT_FINISHED = true;
	}

	/**
	 * Checks if is net connect.
	 * 
	 * @return true, if is net connect
	 */
	public static boolean isNetConnect() {  
		ConnectivityManager cManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo(); 
		if (info != null && info.isAvailable()){ 
			//能联网 
			return true; 
		}else{ 
			//不能联网 
			return false; 
		} 
	} 

}
