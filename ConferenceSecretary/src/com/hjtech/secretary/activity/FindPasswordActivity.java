package com.hjtech.secretary.activity;

import android.view.KeyEvent;

import com.hjtech.secretary.R;
import com.hjtech.secretary.fragment.FindPasswordChangeFragment;
import com.hjtech.secretary.fragment.FindPasswordVerifyFragment;

/**
 * The Class FindPasswordActivity.
 * 找回密码
 * @author albuscrow
 */
public class FindPasswordActivity extends BaseActivity {
	
	/** The verify fragment. */
	private FindPasswordVerifyFragment verifyFragment;
	
	/** The change fragment. */
	private FindPasswordChangeFragment changeFragment;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		initUI(R.layout.activity_findpassword, R.string.title_activity_findpassword);
	};
	
	/* (non-Javadoc)
	 * @see com.hjtech.secretary.activity.BaseActivity#initUI(int, int)
	 */
	@Override
	protected void initUI(int layoutId, int titleId) {
		super.initUI(layoutId, titleId);
		verifyFragment = new FindPasswordVerifyFragment();
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		getSupportFragmentManager().beginTransaction().add(R.id.find_password_container, verifyFragment).commit();
		super.onResume();
	}

	/**
	 * Next.
	 * 输入验证码后，切换fragment
	 * @param phone
	 *            the phone
	 * @param code
	 *            the code
	 */
	public void next(String phone, String code) {
		changeFragment = new FindPasswordChangeFragment();
		changeFragment.setData(phone, code);
		getSupportFragmentManager().beginTransaction().replace(R.id.find_password_container, changeFragment).addToBackStack(null).commit();
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && getSupportFragmentManager().getBackStackEntryCount() != 0) {
			getSupportFragmentManager().popBackStack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
