package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.MettingDetailsActivity;
import com.hjtech.secretary.activity.MettingListActivity;
import com.hjtech.secretary.data.MTMetting;
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
	private MettingListActivity activity;
	public void setData(List<MTMetting> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	private List<MTMetting> data;

	public MettingListAdapter(MettingListActivity myMeetActivity) {
		this.activity = myMeetActivity;
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
					Intent intent = new Intent(MettingListAdapter.this.activity, MettingDetailsActivity.class);
					intent.putExtra("id", (Long)v.getTag());
					MettingListAdapter.this.activity.startActivity(intent);
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
		viewHold.mettingDetails.setTag(metting.getMmId());
		
		return convertView;
	}

}
