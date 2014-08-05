package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity
{
	private final int SPLASH_DISPLAY_LENGHT = 2000;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_splash);
    	new Handler().postDelayed(new Runnable() {
    		public void run() {
    			Intent mainIntent = null;
    			mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
    			mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    			SplashActivity.this.startActivity(mainIntent);
    			SplashActivity.this.finish();
    		}
    		
    	}, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return false;
    }

}
