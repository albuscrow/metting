package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.data.MTMetting;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RelatedMettingAdapter extends BaseAdapter {

	private BaseActivity activity;

	private List<MTMetting> data;
	
	public void setMettings(List<MTMetting> mettings){
		this.data = mettings;
		this.notifyDataSetChanged();
	}
	
	public RelatedMettingAdapter(BaseActivity activity) {
		this.activity = activity;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_related_metting, null);
			convertView.setTag(convertView.findViewById(R.id.related_metting_name));
		}
		((TextView)convertView.getTag()).setText(((MTMetting)getItem(position)).getMmTitle());
		return convertView;
	}

}
