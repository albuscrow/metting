package com.hjtech.secretary.adapter;

import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTMettingListResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.fragment.BaseFragment;
import com.hjtech.secretary.fragment.MyMettingFragment;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * The Class MyMettingAdapter.
 * 用于展示我的会议
 * @author albuscrow
 */
public class MyMettingAdapter extends BaseAdapter implements ListAdapter {
	
	/** The activity. */
	private BaseActivity activity;
	
	/** The current page num. */
	private int currentPageNum = 1;
	
	/** The total data num. */
	private int totalDataNum;
	
	/** The fragment. */
	private BaseFragment fragment;
	
	/**
	 * Sets the data.
	 * 
	 * @param data
	 *            the new data
	 */
	public void setData(List<MTMetting> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}
	
	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public List<MTMetting> getData(){
		return data;
	}

	/** The data. */
	private List<MTMetting> data;
	
	/** The status. */
	private int status;

	/**
	 * Instantiates a new my metting adapter.
	 * 
	 * @param fragment
	 *            the fragment
	 * @param status
	 *            the status
	 */
	public MyMettingAdapter(BaseFragment fragment,int status) {
		this.activity = fragment.getBaseActivity();
		this.fragment = fragment;
		this.status = status;
	}
	

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (data != null) {
			return data.get(position);
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		MTMetting metting = (MTMetting)getItem(position);
		if (metting != null) {
			return metting.getMmId();
		}else{
			return -1l;
		}
	}
	
	/**
	 * The Class ViewHold.
	 * 
	 * @author albuscrow
	 */
	class ViewHold{
		
		/** The metting time year. */
		public TextView mettingTimeYear;
		
		/** The metting time month. */
		public TextView mettingTimeMonth;
		
		/** The metting time day. */
		public TextView mettingTimeDay;
		
		/** The metting time week. */
		public TextView mettingTimeWeek;
		
		/** The metting name. */
		public TextView mettingName;
		
		/** The metting during time. */
		public TextView mettingDuringTime;
		
		/** The metting address. */
		public TextView mettingAddress;
		
		/** The metting fee and res. */
		public TextView mettingFeeAndRes;
		
		/** The metting signin. */
		public ImageButton mettingSignin; 
		
		/** The metting status. */
		public ImageView mettingStatus;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_my_metting, null);
			viewHold = new ViewHold();
			viewHold.mettingName = (TextView) convertView.findViewById(R.id.metting_name);
			viewHold.mettingDuringTime = (TextView) convertView.findViewById(R.id.metting_during_time);
			viewHold.mettingAddress = (TextView) convertView.findViewById(R.id.metting_address);
			viewHold.mettingFeeAndRes = (TextView) convertView.findViewById(R.id.metting_free_restriction);
			viewHold.mettingStatus = (ImageView) convertView.findViewById(R.id.metting_status);
			viewHold.mettingSignin = (ImageButton) convertView.findViewById(R.id.metting_signin);
			viewHold.mettingSignin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, CaptureActivity.class);
					fragment.startActivityForResult(intent, MyMettingFragment.SIGNAL);
				}
			});
			
			viewHold.mettingTimeDay = (TextView) convertView.findViewById(R.id.metting_list_day);
			viewHold.mettingTimeMonth = (TextView) convertView.findViewById(R.id.metting_list_month);
			viewHold.mettingTimeWeek = (TextView) convertView.findViewById(R.id.metting_list_week);
			viewHold.mettingTimeYear = (TextView) convertView.findViewById(R.id.metting_list_year);
			
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTMetting metting = (MTMetting) getItem(position);
		metting.initTime();
		
		viewHold.mettingName.setText(metting.getMmTitle());
		viewHold.mettingDuringTime.setText(String.format(activity.getResources().getString(R.string.metting_time),metting.getTime()));
		viewHold.mettingAddress.setText(String.format(activity.getResources().getString(R.string.metting_place),metting.getMmAddress()));
		viewHold.mettingFeeAndRes.setText(String.format(activity.getResources().getString(R.string.metting_fee_and_res),metting.getMmFreeTypeStr(),metting.getMemberRttForDetail()));
		
		viewHold.mettingTimeDay.setText(metting.getDay());
		viewHold.mettingTimeMonth.setText(metting.getMonth());
		viewHold.mettingTimeWeek.setText(metting.getWeek());
		viewHold.mettingTimeYear.setText(metting.getYear());
		
		if (metting.getIsEnroll() == MTMetting.ENROLL && metting.getIsEnroll() != MTMetting.SIGNIN) {
			viewHold.mettingSignin.setVisibility(View.VISIBLE);
//			viewHold.mettingSignin.setVisibility(View.GONE);
		}else{
			viewHold.mettingSignin.setVisibility(View.GONE);
		}
		
		if (metting.getIsStarted() == MTMetting.UNSTART) {
			viewHold.mettingStatus.setImageResource(R.drawable.metting_status_unstart);
		}else if (metting.getIsStarted() == MTMetting.STARTED) {
			viewHold.mettingStatus.setImageResource(R.drawable.metting_status_going);
		}else{
			viewHold.mettingStatus.setImageResource(R.drawable.metting_status_end);
		}
		
		return convertView;
	}


	/**
	 * Append data.
	 * 
	 * @param result
	 *            the result
	 */
	public void appendData(List<MTMetting> result) {
		this.data.addAll(result);
		this.notifyDataSetChanged();
	}
	
	/** The can init. */
	Boolean canInit = true;
	
	/**
	 * Inits the data.
	 */
	public void initData() {
		if (canInit) {
			synchronized (canInit) {
				if (canInit) {
					canInit = false;
					currentPageNum = 1; 
					new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {


						@Override
						public void onPreExecute() {
							activity.showWaitBar();
						}

						@Override
						public void onPostExecute(Object result) {
							activity.hideWaitBar();
							if (result != null && result instanceof Integer) {
								MTCommon.ShowToast("当前网络不可用,请检查网络链接");
								return;
							}	

							if (result == null) {
								MTCommon.ShowToast("获取会议数据失败！");
								return;
							}
							if (result instanceof MTSimpleResult) {
								MTCommon.ShowToast("获取会议数据失败！");
								return;
							}
							MTMettingListResult ms = (MTMettingListResult) result;
							totalDataNum = ms.getTotal();
							setData(ms.getDetails());
							//				hideWaitBar();
							canInit = true;
						}
					}).getMyMetting(MTUserManager.getUser().getMuAccount(), currentPageNum, status);				
				}
			}
		}

	}

	/**
	 * Gets the more data.
	 * 
	 * @return the more data
	 */
	public void getMoreData() {
		if (currentPageNum * 15 > totalDataNum) {
			return;
		}
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
//				MTCommon.ShowToast("正在加载更多数据");
				activity.showWaitBar();
			}

			@Override
			public void onPostExecute(Object result) {
				activity.hideWaitBar();
				if (result != null && result instanceof Integer) {
					MTCommon.ShowToast("当前网络不可用,请检查网络链接");
					return;
				}	

				if (result == null) {
					MTCommon.ShowToast("获取会议数据失败！");
					return;
				}
				if (result instanceof MTSimpleResult) {
					MTCommon.ShowToast("获取会议数据失败！");
					return;
				}
				MTMettingListResult ms = (MTMettingListResult) result;
				appendData(ms.getDetails());
				//				hideWaitBar();
			}
		}).getMyMetting(MTUserManager.getUser().getMuAccount(), ++ currentPageNum, status);
	}

	/**
	 * Sets the activity.
	 * 
	 * @param activity
	 *            the new activity
	 */
	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}
}