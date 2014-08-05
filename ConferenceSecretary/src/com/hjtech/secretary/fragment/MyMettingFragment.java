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
import com.hjtech.secretary.adapter.MTPagerAdatper;
import com.hjtech.secretary.adapter.MyMettingAdapter;
import com.hjtech.secretary.common.AppConfig;
import com.hjtech.secretary.common.MettingApplication;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.MTMetting;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

public class MyMettingFragment extends BaseFragment {
	public static final int SIGNAL = 10;
	
	private List<LinearLayout> mettingLists = new ArrayList<LinearLayout>();
	private List<MyMettingAdapter> adapters = new ArrayList<MyMettingAdapter>();
	private MTPagerAdatper myPagerAdapter;

	private View stripe;

	private int currentPage = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initActionBar(R.string.homepage, R.string.title_activity_my_metting,0);
		return initUI(inflater);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	public MainActivity getMainActivity(){
		return (MainActivity) activity;
	}
	
	@SuppressLint("NewApi")
	protected ViewGroup initUI(LayoutInflater inflater) {
		setbackButton();
		currentPage = 0;
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_metting, null);
		
		
//		PagerTabStrip mPagerTabStrip=(PagerTabStrip) gv(R.id.pagertab);
        //设置导航条的颜色
//        mPagerTabStrip.setTabIndicatorColorResource(R.color.mt_blue_press);
        
        ViewPager mViewPager = (ViewPager) gv(R.id.my_metting_viewpager);
        //添加数据
        if (myPagerAdapter == null) {
        	String[] titles = getResources().getStringArray(R.array.my_metting_title_strip);
        	List<LinearLayout> data = getPagerViewData();
        	myPagerAdapter = new MTPagerAdatper(data, titles, adapters);
		}else{
			myPagerAdapter.init(getMainActivity());
		}
        
        stripe = gv(R.id.stripe);
        stripe.post(new Runnable() {
			public void run() {
				int startPosition = AppConfig.SCREENWIDTH/6 - stripe.getWidth() / 2;
				stripe.setTag(startPosition);
				stripe.setX(startPosition + currentPage * AppConfig.SCREENWIDTH/3);
			}
		});
		mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				MyMettingAdapter adapter = adapters.get(arg0);
				if (adapter.getData() == null) {
					adapter.initData();
				}
				changeStripText(arg0);
				currentPage = arg0;
			}
			
			@SuppressLint("NewApi")
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				float x = (arg0 + arg1)*(AppConfig.SCREENWIDTH/3*2) / 2 + (Integer) stripe.getTag();
				stripe.setX(x);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
        adapters.get(0).initData();
        changeStripText(currentPage);
        
        
        //set tab
//        getMainActivity().chooseTab(MainActivity.TAB_MY_METTIN_INDEX);
		return rootView;
	}
	
	int[] stripeText = new int[]{R.id.stripe1,R.id.stripe2,R.id.stripe3};
	
	protected void changeStripText(int arg0) {
		for (int id : stripeText) {
			((TextView)gv(id)).setTextColor(getResources().getColor(R.color.mt_text_6));
		}
		((TextView)gv(stripeText[arg0])).setTextColor(getResources().getColor(R.color.mt_black));
	}
	
	public static final int TOTAL_PAGER = 3;
	private static final int[] STATUS_LIST = new int[]{DataProvider.STATUS_ENROLL, DataProvider.STATUS_SIGNIN, DataProvider.STATUS_ALL};
	private List<LinearLayout> getPagerViewData() {
		mettingLists.clear();
		adapters.clear();
		LayoutInflater inflater = getMainActivity().getLayoutInflater();
		for (int i = 0; i < TOTAL_PAGER; ++i) {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pagerview_item_metting_list, null);
			final MyMettingAdapter adapter = new MyMettingAdapter(this, STATUS_LIST[i]);
			PullToRefreshListView list = (PullToRefreshListView) layout.findViewById(R.id.my_metting_list);
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
