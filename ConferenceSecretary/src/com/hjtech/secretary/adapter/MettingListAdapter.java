package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.activity.MainActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.fragment.BaseFragment;
import com.hjtech.secretary.fragment.MTFragmentFactory;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MettingListAdapter extends BaseAdapter implements ListAdapter {
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
		public TextView mettingName;
		public TextView mettingStatus;
		public ImageView mettingPicture;
		public TextView mettingTime;
		public TextView mettingIsFree;
		public TextView mettingAddress;
		public TextView mettingRestriction;
		public Button mettingDetails; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_metting_list, null);
			viewHold = new ViewHold();
			viewHold.mettingName = (TextView) convertView.findViewById(R.id.metting_name);
			viewHold.mettingStatus = (TextView) convertView.findViewById(R.id.metting_status);
			viewHold.mettingPicture = (ImageView) convertView.findViewById(R.id.metting_picture);
			viewHold.mettingTime = (TextView) convertView.findViewById(R.id.metting_time);
			viewHold.mettingIsFree = (TextView) convertView.findViewById(R.id.metting_free);
			viewHold.mettingRestriction = (TextView) convertView.findViewById(R.id.metting_restriction);
			viewHold.mettingAddress = (TextView) convertView.findViewById(R.id.metting_address);
			viewHold.mettingDetails = (Button) convertView.findViewById(R.id.metting_detail);
			viewHold.mettingDetails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("metting", (MTMetting)v.getTag());
					((MainActivity)activity).switchFragment(MTFragmentFactory.METTING_DETAILS, intent, true);
				}
			});
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTMetting metting = (MTMetting) getItem(position);
		
		viewHold.mettingTime.setText(metting.getTime());
		viewHold.mettingName.setText(metting.getMmTitle());
		viewHold.mettingStatus.setText(metting.getIsEnrollStr());
		viewHold.mettingIsFree.setText(metting.getMmFreeTypeStr());
		viewHold.mettingRestriction.setText(metting.getMemberRtt());
		viewHold.mettingAddress.setText(metting.getMmAddress());
		ImageLoader.getInstance().displayImage(metting.getMmLogo(), viewHold.mettingPicture);
		viewHold.mettingDetails.setTag(metting);
		
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
					}).getMeetList(MTUserManager.getUser().getMuAccount(), 0, status);				
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
		}).getMeetList(MTUserManager.getUser().getMuAccount(), ++currentPageNum, status);
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}


}
