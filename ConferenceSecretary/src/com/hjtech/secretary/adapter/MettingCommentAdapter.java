package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.MettingCommentActivity;
import com.hjtech.secretary.data.MTComment;
import com.hjtech.secretary.utils.MTCommon;

import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
		public TextView commentMore;
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
			viewHold.commentMore = (TextView) convertView.findViewById(R.id.comment_more);
			viewHold.commentMore.setTag(viewHold.commentContent);
			viewHold.commentMore.setOnClickListener(new OnClickListener() {
				boolean flag = true;
				@Override
				public void onClick(View v) {
					TextView view = (TextView) v.getTag();
					TextView button = (TextView) v;
					if (flag) {
						view.setEllipsize(null);
						view.setSingleLine(false);
						button.setText("点击收起");
						button.setCompoundDrawablesWithIntrinsicBounds(null, null, activity.getResources().getDrawable(R.drawable.comment_less), null);
						flag = false;
					}else{
						view.setEllipsize(TruncateAt.END);
						view.setLines(2);
						button.setText("显示全部");
						button.setCompoundDrawablesWithIntrinsicBounds(null, null, activity.getResources().getDrawable(R.drawable.comment_more), null);
						flag = true;
					}
				}
			});
			
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTComment comment = (MTComment) getItem(position);

		viewHold.commentName.setText(comment.getMuName());
		viewHold.commentTime.setText(comment.getMcAddtime());
		final TextView commentContent = viewHold.commentContent;
		commentContent.setTag(viewHold.commentMore);
		commentContent.setText(comment.getMcContent());
		commentContent.post(new Runnable() {
			public void run() {
				TextView view = (TextView) commentContent.getTag();
				if (MTCommon.isEllipsis(commentContent)) {
					view.setVisibility(View.VISIBLE);
				}else{
					view.setVisibility(View.GONE);
				}
			}
		});
		if (position % 2 == 0) {
			convertView.setBackgroundColor(activity.getResources().getColor(R.color.comment_light));
		}else{
			convertView.setBackgroundColor(activity.getResources().getColor(R.color.comment_dark));
		}
		
		return convertView;
	}

}
