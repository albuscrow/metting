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
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyMettingFragment extends BaseFragment {
	
	private List<PullToRefreshListView> mettingLists = new ArrayList<PullToRefreshListView>();
	private List<MyMettingAdapter> adapters = new ArrayList<MyMettingAdapter>();
	private int currentStatus = DataProvider.STATUS_ENROLL;

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
	
	
	protected ViewGroup initUI(LayoutInflater inflater) {
		setbackButton();
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_metting, null);
		
		PagerTabStrip mPagerTabStrip=(PagerTabStrip) gv(R.id.pagertab);
        //设置导航条的颜色
        mPagerTabStrip.setTabIndicatorColorResource(R.color.mt_blue);
        mPagerTabStrip.setDrawFullUnderline(false);
        
        ViewPager mViewPager = (ViewPager) gv(R.id.my_metting_viewpager);
        //添加数据
        String[] titles = getResources().getStringArray(R.array.my_metting_title_strip);
        List<PullToRefreshListView> data = getPagerViewData();
        mViewPager.setAdapter(new MTPagerAdatper(data, titles));
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				adapters.get(arg0).getData();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        adapters.get(0).getData();
		//init metting list
//		mettingList = (PullToRefreshListView) gv(R.id.my_metting_list);
//		adapter = new MyMettingAdapter(this);
//		mettingList.setAdapter(adapter);
//		mettingList.setOnPullEventListener(new OnPullEventListener<ListView>() {
//			@Override
//			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
//					State state, Mode direction) {
//				getData();
//			}
//		});
//		
//		mettingList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//			@Override
//			public void onLastItemVisible() {
//				appendData();
//			}
//
//			
//		});
//		mettingList.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
////				Intent intent = new Intent(getMainActivity(), MettingDetailsActivity.class);
////				intent.putExtra("metting",(MTMetting)parent.getAdapter().getItem(position));
////				MyMettingActivity.this.startActivity(intent);
//			}
//		});
		
		return rootView;
		
		//init button
//		gv(R.id.my_meet_all_button).setOnClickListener(this);
//		View applyButton = gv(R.id.my_meet_apply_button);
//		applyButton.setOnClickListener(this);
//		changeStatus(applyButton, DataProvider.STATUS_ENROLL);
//		gv(R.id.my_meet_signin_button).setOnClickListener(this);
	}
	
	public static final int TOTAL_PAGER = 3;
	private static final int[] STATUS_LIST = new int[]{DataProvider.STATUS_ENROLL, DataProvider.STATUS_SIGNIN, DataProvider.STATUS_ALL};
	private List<PullToRefreshListView> getPagerViewData() {
		LayoutInflater inflater = getMainActivity().getLayoutInflater();
		for (int i = 0; i < TOTAL_PAGER; ++i) {
			PullToRefreshListView list = (PullToRefreshListView) inflater.inflate(R.layout.pagerview_item_metting_list, null);
			final MyMettingAdapter adapter = new MyMettingAdapter(this, STATUS_LIST[i]);
			list.setAdapter(adapter);
			list.setOnPullEventListener(new OnPullEventListener<ListView>() {
				@Override
				public void onPullEvent(PullToRefreshBase<ListView> refreshView,
						State state, Mode direction) {
					adapter.getData();
				}
			});
			
			list.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
	
				@Override
				public void onLastItemVisible() {
					adapter.appendData();
				}
	
				
			});
			list.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
//					Intent intent = new Intent(getMainActivity(), MettingDetailsActivity.class);
//					intent.putExtra("metting",(MTMetting)parent.getAdapter().getItem(position));
//					MyMettingActivity.this.startActivity(intent);
				}
			});
			mettingLists.add(list);
			adapters.add(adapter);
		}
		return mettingLists;
	}
}
