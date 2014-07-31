package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.fragment.RegisterFormFragment;
import com.hjtech.secretary.fragment.RegisterVerifyFragment;

import android.content.Intent;
import android.os.Bundle;

public class RegisterActivity extends BaseActivity{
	RegisterFormFragment formFragment;
	RegisterVerifyFragment verifyFragment;
	private String phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_register, R.string.title_activity_register);
	}
	
	@Override
	protected void initUI(int layoutId, int titleId) {
		super.initUI(layoutId, titleId);
		formFragment = (RegisterFormFragment) getSupportFragmentManager().findFragmentById(R.id.Register_form);
		verifyFragment = (RegisterVerifyFragment) getSupportFragmentManager().findFragmentById(R.id.Register_verify);
	}

	public void next(String phone) {
		this.phone = phone;
		getSupportFragmentManager().beginTransaction().hide(verifyFragment).commit();
	}

	public String getPhone() {
		return phone;
	}

	public void goHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

}