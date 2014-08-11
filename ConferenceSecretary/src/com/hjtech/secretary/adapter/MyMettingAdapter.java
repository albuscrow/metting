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

public class MyMettingAdapter extends BaseAdapter implements ListAdapter {
	private BaseActivity activity;
	private int currentPageNum = 1;
	private int totalDataNum;
	public void setData(List<MTMetting> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}
	
	public List<MTMetting> getData(){
		return data;
	}

	private List<MTMetting> data;
	private int status;

	public MyMettingAdapter(BaseFragment fragment,int status) {
		this.activity = fragment.getBaseActivity();
		this.status = status;
	}
	

	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (data != null) {
			return data.get(position);
		}else{
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		MTMetting metting = (MTMetting)getItem(position);
		if (metting != null) {
			return metting.getMmId();
		}else{
			return -1l;
		}
	}
	
	class ViewHold{
		public TextView mettingTimeYear;
		public TextView mettingTimeMonth;
		public TextView mettingTimeDay;
		public TextView mettingTimeWeek;
		
		public TextView mettingName;
		public TextView mettingDuringTime;
		public TextView mettingAddress;
		public TextView mettingFeeAndRes;
		
		public ImageButton mettingSignin; 
		public ImageView mettingStatus;
	}

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
					activity.startActivityForResult(intent, MyMettingFragment.SIGNAL);
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


	public void appendData(List<MTMetting> result) {
		this.data.addAll(result);
		this.notifyDataSetChanged();
	}
	
	Boolean canInit = true;
	public void initData() {
		if (canInit) {
			synchronized (canInit) {
				if (canInit) {
					canInit = false;
					currentPageNum = 1; 
					new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {


						@Override
						public void onPreExecute() {
							//				showWaitBar();
						}

						@Override
						public void onPostExecute(Object result) {
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

	public void getMoreData() {
		if (currentPageNum * 15 > totalDataNum) {
			return;
		}
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
//				MTCommon.ShowToast("正在加载更多数据");
			}

			@Override
			public void onPostExecute(Object result) {
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

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}
}