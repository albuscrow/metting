package com.hjtech.secretary.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hjtech.secretary.R;

import android.R.string;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class MTPagerAdatper extends PagerAdapter {
	List<PullToRefreshListView> data;
	String[] titles;
	
	public MTPagerAdatper(List<PullToRefreshListView> data, String[] titles) {
		this.data = data;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(data.get(position));
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		PullToRefreshListView view = data.get(position);
		container.addView(view);
		return view;
	}

}
