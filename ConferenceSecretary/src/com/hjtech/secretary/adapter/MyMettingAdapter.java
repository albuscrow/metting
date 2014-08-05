package com.hjtech.secretary.adapter;

import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.activity.MainActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.fragment.BaseFragment;
import com.hjtech.secretary.fragment.MyMettingFragment;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MyMettingAdapter extends BaseAdapter implements ListAdapter {
	private BaseActivity activity;
	private int currentPageNum = 0;
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
			
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTMetting metting = (MTMetting) getItem(position);
		
		viewHold.mettingName.setText(metting.getMmTitle());
		viewHold.mettingDuringTime.setText(String.format(activity.getResources().getString(R.string.metting_time),metting.getTime()));
		viewHold.mettingAddress.setText(String.format(activity.getResources().getString(R.string.metting_place),metting.getMmAddress()));
		viewHold.mettingFeeAndRes.setText(String.format(activity.getResources().getString(R.string.metting_fee_and_res),metting.getMmFreeTypeStr(),metting.getMemberRttForDetail()));
		if (metting.getIsEnroll() == MTMetting.ENROLL) {
			viewHold.mettingSignin.setVisibility(View.VISIBLE);
		}else{
//			viewHold.mettingSignin.setVisibility(View.GONE);
			viewHold.mettingSignin.setVisibility(View.VISIBLE);
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
					new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

						@Override
						public void onPreExecute() {
							//				showWaitBar();
						}

						@Override
						public void onPostExecute(Object result) {
							if (result == null) {
								MTCommon.ShowToast("获取会议数据失败！");
							}else{
								setData((List<MTMetting>) result);
							}
							//				hideWaitBar();
							canInit = true;
						}
					}).getMyMeet(MTUserManager.getUser().getMuAccount(), 0, status);				
				}
			}
		}

	}

	public void getMoreData() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				//				showWaitBar();
			}

			@Override
			public void onPostExecute(Object result) {
				if (result == null) {
					MTCommon.ShowToast("获取会议数据失败！");
				}else{
					appendData((List<MTMetting>) result);
				}
				//				hideWaitBar();
			}
		}).getMyMeet(MTUserManager.getUser().getMuAccount(), ++ currentPageNum, status);
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}
}
