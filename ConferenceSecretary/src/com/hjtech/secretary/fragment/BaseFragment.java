package com.hjtech.secretary.fragment;

import com.hjtech.secretary.activity.BaseActivity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	
	protected BaseActivity activity;
	protected ViewGroup rootView;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity =  (BaseActivity) activity;
	}

	protected void initActionBar(int leftId, int titleId, int rightId) {
		if (titleId != 0) {
			activity.changeTitle(titleId);
		}
		
		if (leftId != 0) {
			activity.changeActionLeft(leftId);
		}
		
		if (rightId != 0) {
			activity.changeActionRight(rightId);
		}
	}
	
	protected void setbackButton(){
		activity.setbackButtonForFragment();
	}
	
	protected View gv(int id){
		return rootView.findViewById(id);
	}
}