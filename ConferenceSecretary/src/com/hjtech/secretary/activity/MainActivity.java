package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.fragment.BaseFragment;
import com.hjtech.secretary.fragment.MTFragmentFactory;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class MainActivity extends BaseActivity {
	
	private int UIType;
	private FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		UIType = getIntent().getIntExtra("UIType", 0);
		if (UIType == 0) {
			new Exception("UIType can not be 0!!!").printStackTrace();
		}
		
		initUI(R.layout.activity_main);
		
		fragmentManager = getSupportFragmentManager();
		switchFragment(UIType);
	}
	
	BaseFragment currentFragment;
	private void switchFragment(int id) {
		currentFragment = MTFragmentFactory.getFragment(id);
		fragmentManager.beginTransaction().replace(R.id.fragment_content, currentFragment).commit();
//		switch (id) {
//		case MTFragmentFactory.MY_METTING:
//			
//			break;
//
//		default:
//			break;
//		}
	
	}
}
