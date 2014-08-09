package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_about, R.string.home_about);
	}

	protected void initUI(int layoutId, int titleId) {
		super.initUI(layoutId, titleId);
	}

}
