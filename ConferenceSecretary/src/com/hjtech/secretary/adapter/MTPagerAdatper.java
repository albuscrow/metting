package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.activity.BaseActivity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * The Class MTPagerAdatper.
 * 用于展示不同类型的会议
 * @author albuscrow
 */
public class MTPagerAdatper extends PagerAdapter {
	
	/** The data. */
	List<LinearLayout> data;
	
	/** The titles. */
	String[] titles;
	
	/** The adapters. */
	private List<?> adapters;
	
	/**
	 * Inits the.
	 * 
	 * @param activity
	 *            the activity
	 */
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
	
	/**
	 * Instantiates a new MT pager adatper.
	 * 
	 * @param data
	 *            the data
	 * @param titles
	 *            the titles
	 * @param adapters
	 *            the adapters
	 */
	public MTPagerAdatper(List<LinearLayout> data, String[] titles, List<?> adapters) {
		this.data = data;
		this.titles = titles;
		this.adapters = adapters;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup, int, java.lang.Object)
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(data.get(position));
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		if (position < data.size()) {
			return titles[position];
		}
		return null;
	}
	
	/** The container. */
	public ViewGroup container;
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LinearLayout view = data.get(position);
		container.addView(view);
		this.container = container;
		return view;
	}

}
