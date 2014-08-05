package com.hjtech.secretary.fragment;

import com.hjtech.secretary.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindPasswordChangeFragment extends BaseFragment {

	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return init(inflater);
	}
	
	public ViewGroup init(LayoutInflater inflater){
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_findpassword_change, null);
		return rootView;
	}

}

