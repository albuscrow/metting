package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;

import android.os.Bundle;

public class ModifyPasswordActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initUI(R.layout.activity_modify_password, R.string.title_activity_user_edit, R.string.title_activity_modify_password);
	}
	
	@Override
	protected void initUI(int layoutId, int titleId) {
		super.initUI(layoutId, titleId);
		
	}
}
