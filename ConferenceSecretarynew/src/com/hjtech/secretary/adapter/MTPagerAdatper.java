package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.activity.BaseActivity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MTPagerAdatper extends PagerAdapter {
	List<LinearLayout> data;
	String[] titles;
	private List<?> adapters;
	
	public void init(BaseActivity activity) {
		if (data != null && data.size() != 0) {
			container.removeAllViews();
			for (Object pl : adapters) {
				if (pl instanceof MyMettingAdapter) {
					((MyMettingAdapter)pl).setActivity(activity);
				}else if (pl instanceof MettingListAdapter) {
					((MettingListAdapter)pl).setActivity(activity);
				}
			}
		}
	}
	
	public MTPagerAdatper(List<LinearLayout> data, String[] titles, List<?> adapters) {
		this.data = data;
		this.titles = titles;
		this.adapters = adapters;
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
		if (position < data.size()) {
			return titles[position];
		}
		return null;
	}
	
	public ViewGroup container;
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LinearLayout view = data.get(position);
		container.addView(view);
		this.container = container;
		return view;
	}

}
