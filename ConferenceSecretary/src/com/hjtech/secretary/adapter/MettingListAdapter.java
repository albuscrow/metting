package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTMettingListResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.fragment.BaseFragment;
import com.hjtech.secretary.utils.MTCommon;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MettingListAdapter extends BaseAdapter implements ListAdapter {
	private BaseActivity activity;
	private int currentPageNum = 1;
	public void setData(List<MTMetting> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}
	
	public List<MTMetting> getData(){
		return data;
	}

	private List<MTMetting> data;
	private int status;

	public MettingListAdapter(BaseFragment fragment,int status) {
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
		
		public ImageButton isEnroll; 
		public ImageView mettingStatus;
		
		public ImageView star;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_metting_list, null);
			viewHold = new ViewHold();
			viewHold.mettingName = (TextView) convertView.findViewById(R.id.metting_name);
			viewHold.mettingDuringTime = (TextView) convertView.findViewById(R.id.metting_during_time);
			viewHold.mettingAddress = (TextView) convertView.findViewById(R.id.metting_address);
			viewHold.mettingFeeAndRes = (TextView) convertView.findViewById(R.id.metting_free_restriction);
			viewHold.mettingStatus = (ImageView) convertView.findViewById(R.id.metting_status);
			viewHold.isEnroll = (ImageButton) convertView.findViewById(R.id.metting_signin);
			
			viewHold.mettingTimeDay = (TextView) convertView.findViewById(R.id.metting_list_day);
			viewHold.mettingTimeMonth = (TextView) convertView.findViewById(R.id.metting_list_month);
			viewHold.mettingTimeWeek = (TextView) convertView.findViewById(R.id.metting_list_week);
			viewHold.mettingTimeYear = (TextView) convertView.findViewById(R.id.metting_list_year);
			
			viewHold.star = (ImageView) convertView.findViewById(R.id.metting_star);
			
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
		
		if (metting.getIsStarted() == MTMetting.UNSTART) {
			viewHold.mettingStatus.setImageResource(R.drawable.metting_status_unstart);
		}else if (metting.getIsStarted() == MTMetting.STARTED) {
			viewHold.mettingStatus.setImageResource(R.drawable.metting_status_going);
		}else{
			viewHold.mettingStatus.setImageResource(R.drawable.metting_status_end);
		}
		
		if (metting.getIsEnroll() != MTMetting.UNENROLL) {
			viewHold.isEnroll.setVisibility(View.VISIBLE);
		}else{
			viewHold.isEnroll.setVisibility(View.GONE);
		}
		
		viewHold.star.setImageResource(metting.getEnCountPictureId());
		
		return convertView;
	}


	public void appendData(List<MTMetting> result) {
		this.data.addAll(result);
		this.notifyDataSetChanged();
	}
	
	Boolean canInit = true;
	private int totalDataNum;
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
							MTMettingListResult mr = (MTMettingListResult) result;
							totalDataNum = mr.getTotal();
							setData(mr.getDetails());
							canInit = true;
						}
					}).getMettingList(MTUserManager.getUser().getMuAccount(), currentPageNum, status);				
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
				MTMettingListResult mr = (MTMettingListResult) result;
					appendData(mr.getDetails());
				//				hideWaitBar();
			}
		}).getMettingList(MTUserManager.getUser().getMuAccount(), ++ currentPageNum, status);
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}
}
