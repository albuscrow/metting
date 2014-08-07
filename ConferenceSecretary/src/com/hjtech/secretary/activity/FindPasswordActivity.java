package com.hjtech.secretary.activity;

import android.view.KeyEvent;

import com.hjtech.secretary.R;
import com.hjtech.secretary.fragment.FindPasswordChangeFragment;
import com.hjtech.secretary.fragment.FindPasswordVerifyFragment;

public class FindPasswordActivity extends BaseActivity {
	
	private FindPasswordVerifyFragment verifyFragment;
	private FindPasswordChangeFragment changeFragment;
	
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		initUI(R.layout.activity_findpassword, R.string.title_activity_findpassword);
	};
	
	@Override
	protected void initUI(int layoutId, int titleId) {
		super.initUI(layoutId, titleId);
		verifyFragment = new FindPasswordVerifyFragment();
	}
	
	@Override
	protected void onResume() {
		getSupportFragmentManager().beginTransaction().add(R.id.find_password_container, verifyFragment).commit();
		super.onResume();
	}

	public void next(String phone, String code) {
		changeFragment = new FindPasswordChangeFragment();
		changeFragment.setData(phone, code);
		getSupportFragmentManager().beginTransaction().replace(R.id.find_password_container, changeFragment).addToBackStack(null).commit();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && getSupportFragmentManager().getBackStackEntryCount() != 0) {
			getSupportFragmentManager().popBackStack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
