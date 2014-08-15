package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_about, R.string.homepage, R.string.home_about);
	}

	protected void initUI(int layoutId, int left,int titleId) {
		super.initUI(layoutId,R.string.homepage, titleId);
		setbackButton();

		gv(R.id.about_phone_num).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("tel:4006775050");    
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);       
				startActivity(intent);
				
			}
		});
	}

}
