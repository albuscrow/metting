package com.hjtech.secretary.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.MainActivity;
import com.hjtech.secretary.activity.MettingListActivity;
import com.hjtech.secretary.adapter.MTPagerAdatper;
import com.hjtech.secretary.adapter.MettingListAdapter;
import com.hjtech.secretary.adapter.MyMettingAdapter;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.MTMetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MettingListFragment extends BaseFragment {
	
	private List<LinearLayout> mettingLists = new ArrayList<LinearLayout>();
	private List<MyMettingAdapter> adapters = new ArrayList<MyMettingAdapter>();
	private MTPagerAdatper myPagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initActionBar(R.string.homepage, R.string.title_activity_metting_list,0);
		return initUI(inflater);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	public MainActivity getMainActivity(){
		return (MainActivity) activity;
	}
	
	
	protected ViewGroup initUI(LayoutInflater inflater) {
		setbackButton();
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_metting_list, null);
		
		PagerTabStrip mPagerTabStrip=(PagerTabStrip) gv(R.id.pagertab);
        //设置导航条的颜色
        mPagerTabStrip.setTabIndicatorColorResource(R.color.mt_blue);
        
        ViewPager mViewPager = (ViewPager) gv(R.id.my_metting_viewpager);
        //添加数据
        if (myPagerAdapter == null) {
        	String[] titles = getResources().getStringArray(R.array.metting_list_title_strip);
        	List<LinearLayout> data = getPagerViewData();
        	myPagerAdapter = new MTPagerAdatper(data, titles, adapters);
		}else{
			myPagerAdapter.init(getMainActivity());
		}
		mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				MyMettingAdapter adapter = adapters.get(arg0);
				if (adapter.getData() == null) {
					adapter.initData();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
        adapters.get(0).initData();
        
        //set tab
//        getMainActivity().chooseTab(MainActivity.TAB_METTIN_LIST_INDEX);
		return rootView;
	}
	
	public static final int TOTAL_PAGER = 4;
	public static final int TODAY = 0;
	public static final int THIS_WEEK = 1;
	public static final int THIS_MONTH = 2;
	public static final int ALL = 3;
	private static final int[] STATUS_LIST = new int[]{DataProvider.TIME_TODAY, DataProvider.TIME_THIS_WEEK, DataProvider.TIME_MONTH, DataProvider.TIME_ALL};

	private List<LinearLayout> getPagerViewData() {
		mettingLists.clear();
		adapters.clear();
		LayoutInflater inflater = getMainActivity().getLayoutInflater();
		for (int i = 0; i < TOTAL_PAGER; ++i) {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pagerview_item_metting_list, null);
			PullToRefreshListView list = (PullToRefreshListView) layout.findViewById(R.id.my_metting_list);
			final MyMettingAdapter adapter = new MyMettingAdapter(this, STATUS_LIST[i]);
			list.setAdapter(adapter);
			list.setOnPullEventListener(new OnPullEventListener<ListView>() {
				@Override
				public void onPullEvent(PullToRefreshBase<ListView> refreshView,
						State state, Mode direction) {
					adapter.initData();
				}
			});
			
			list.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
	
				@Override
				public void onLastItemVisible() {
					adapter.getMoreData();
				}
	
				
			});
			list.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent();
					intent.putExtra("metting",(MTMetting)parent.getAdapter().getItem(position));
					getMainActivity().switchFragment(MTFragmentFactory.METTING_DETAILS, intent, true);
				}
			});
			mettingLists.add(layout);
			adapters.add(adapter);
		}
		return mettingLists;
	}
}
