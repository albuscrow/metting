package com.hjtech.secretary.adapter;

import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.MyMettingActivity;
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

public class MyMettingAdapter extends BaseAdapter implements ListAdapter {
	private MyMettingActivity activity;
	public void setData(List<MTMetting> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	private List<MTMetting> data;

	public MyMettingAdapter(MyMettingActivity myMeetActivity) {
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
		public TextView mettingIntroduction;
		public Button mettingSignin; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_my_metting, null);
			viewHold = new ViewHold();
			viewHold.mettingName = (TextView) convertView.findViewById(R.id.metting_name);
			viewHold.mettingStatus = (TextView) convertView.findViewById(R.id.metting_status);
			viewHold.mettingPicture = (ImageView) convertView.findViewById(R.id.metting_picture);
			viewHold.mettingIntroduction = (TextView) convertView.findViewById(R.id.metting_introduction);
			viewHold.mettingSignin = (Button) convertView.findViewById(R.id.metting_signin);
			viewHold.mettingSignin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, CaptureActivity.class);
					activity.startActivityForResult(intent, MyMettingActivity.SIGNIN);
				}
			});
			
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTMetting metting = (MTMetting) getItem(position);
		
		viewHold.mettingName.setText(metting.getMmTitle());
		viewHold.mettingStatus.setText(metting.getIsEnrollStr());
		viewHold.mettingIntroduction.setText(metting.getMmDesp());
		ImageLoader.getInstance().displayImage(metting.getMmLogo(), viewHold.mettingPicture);
		if (metting.getIsEnroll() == MTMetting.ENROLL) {
			viewHold.mettingSignin.setVisibility(View.VISIBLE);
		}else{
			viewHold.mettingSignin.setVisibility(View.VISIBLE);
//			viewHold.mettingSignin.setVisibility(View.GONE);
		}
		return convertView;
	}

}
