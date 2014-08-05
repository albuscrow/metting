package com.hjtech.secretary.activity;

import java.util.Stack;


import com.hjtech.secretary.R;
import com.hjtech.secretary.fragment.BaseFragment;
import com.hjtech.secretary.fragment.InviteFragment;
import com.hjtech.secretary.fragment.MTFragmentFactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
	}
	
	private BaseFragment currentFragment;
	private RadioButton tabMyMetting;
	private RadioButton tabHomepage;
	private RadioButton tabMettingList;
	private RadioButton tabMessage;
	private RadioButton currentCheckedButton;
	@Override
	protected void initUI(int layoutId) {
		super.initUI(layoutId);
		fragmentManager = getSupportFragmentManager();
		
		//init radioGroup
		tabHomepage = (RadioButton) gv(R.id.tab_homepage);
		tabHomepage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MainActivity.this.finish();
				}
			}
		});
		tabMettingList = (RadioButton) gv(R.id.tab_metting_list);
		tabMettingList.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					switchFragment(MTFragmentFactory.METTING_LIST, null, false);
				}
			}
		});
		tabMyMetting =(RadioButton) gv(R.id.tab_my_metting);
		tabMyMetting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					switchFragment(MTFragmentFactory.MY_METTING, null, false);
				}
			}
		});
		tabMessage =(RadioButton) gv(R.id.tab_message);
		tabMessage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					switchFragment(MTFragmentFactory.MESSAGE, null, false);
				}
			}
		});
				
		initTab();
	}
	
	private void initTab() {
		
		switch (UIType) {
		case MTFragmentFactory.MESSAGE:
			chooseTab(TAB_MESSAGE_INDEX);
			break;

		case MTFragmentFactory.METTING_LIST:
			chooseTab(TAB_METTIN_LIST_INDEX);
			break;

		case MTFragmentFactory.MY_METTING:
			chooseTab(TAB_MY_METTIN_INDEX);
			break;
		default:
			chooseTab(TAB_CLEAR);
			break;
		}

	}

	public void switchFragment(int id, Intent intent, boolean needToPush) {
		BaseFragment tempFragment = MTFragmentFactory.getFragment(id);
		tempFragment.setIntent(intent);
		if (needToPush && currentFragment != null) {
			backStack.add(currentFragment);
		}
		if (currentFragment != null) {
			fragmentManager.beginTransaction().remove(currentFragment).add(R.id.fragment_content, tempFragment).commit();
		}else{
			fragmentManager.beginTransaction().add(R.id.fragment_content, tempFragment).commit();
		}
		currentFragment = tempFragment;
	}
	
//	@Override
//	protected void onStop() {
//		super.onStop();
//		if (currentFragment != null) {
//			fragmentManager.beginTransaction().remove(currentFragment).commit();
//		}
//	}
//	
	
	public static final int TAB_MY_METTIN_INDEX = 3;
	public static final int TAB_METTIN_LIST_INDEX = 2;
	public static final int TAB_HOME_PAGE_INDEX = 1;
	public static final int TAB_MESSAGE_INDEX = 4;
	public static final int TAB_CLEAR = -1;
	
	private void chooseTab(int index){
		if (currentCheckedButton != null) {
			currentCheckedButton.setChecked(false);
		}
		switch (index) {
		case TAB_HOME_PAGE_INDEX:
			tabHomepage.setChecked(true);
			currentCheckedButton = tabHomepage;
			break;

		case TAB_METTIN_LIST_INDEX:
			tabMettingList.setChecked(true);
			currentCheckedButton = tabMettingList;
			break;
			
		case TAB_MY_METTIN_INDEX:
			tabMyMetting.setChecked(true);
			currentCheckedButton = tabMyMetting;
			break;
			
		case TAB_MESSAGE_INDEX:
			tabMessage.setChecked(true);
			currentCheckedButton = tabMessage;
			break;
			
		default:
			break;
		}
	}
	
	Stack<BaseFragment> backStack = new Stack<BaseFragment>();
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && backStack.size() > 0) {
			BaseFragment tempFragment = backStack.pop();
			if (currentFragment != null) {
				fragmentManager.beginTransaction().remove(currentFragment).add(R.id.fragment_content, tempFragment).commit();
			}else{
				fragmentManager.beginTransaction().add(R.id.fragment_content, tempFragment).commit();
			}
			currentFragment = tempFragment;
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		if (currentFragment instanceof InviteFragment) {
			((InviteFragment) currentFragment).onNewIntent(intent);
		}
		super.onNewIntent(intent);
	}
	
	public Stack<BaseFragment> getBackStack() {
		return backStack;
	}
}
