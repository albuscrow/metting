package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.MettingCommentActivity;
import com.hjtech.secretary.activity.MyMettingActivity;
import com.hjtech.secretary.common.MettingApplication;
import com.hjtech.secretary.data.MTComment;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MettingCommentAdapter extends BaseAdapter implements ListAdapter {
	private MettingCommentActivity activity;
	public void setData(List<MTComment> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	private List<MTComment> data;

	public MettingCommentAdapter(MettingCommentActivity myMeetActivity) {
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
		MTComment comment = (MTComment)getItem(position);
		if (comment != null) {
			return comment.getMcId();
		}else{
			return -1l;
		}
	}
	
	class ViewHold{
		public TextView commentName;
		public TextView commentTime;
		public TextView commentContent;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_metting_comment, null);
			viewHold = new ViewHold();
			viewHold.commentName = (TextView) convertView.findViewById(R.id.metting_comment_name);
			viewHold.commentTime = (TextView) convertView.findViewById(R.id.metting_comment_time);
			viewHold.commentContent = (TextView) convertView.findViewById(R.id.metting_comment_content);
			
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTComment comment = (MTComment) getItem(position);

		viewHold.commentName.setText(comment.getMuName());
		viewHold.commentTime.setText(comment.getMcAddtime());
		viewHold.commentContent.setText(comment.getMcContent());
		return convertView;
	}

}
