package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.MessageActivity;
import com.hjtech.secretary.activity.MettingCommentActivity;
import com.hjtech.secretary.activity.MyMettingActivity;
import com.hjtech.secretary.common.MettingApplication;
import com.hjtech.secretary.data.MTComment;
import com.hjtech.secretary.data.MTMessage;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter implements ListAdapter {
	private MessageActivity activity;
	public void setData(List<MTMessage> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	private List<MTMessage> data;

	public MessageAdapter(MessageActivity activity) {
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
		MTMessage message = (MTMessage)getItem(position);
		if (message != null) {
			return message.getMmId();
		}else{
			return -1l;
		}
	}
	
	class ViewHold{
		public TextView messageName;
		public TextView messageTime;
		public TextView messageContent;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_message, null);
			viewHold = new ViewHold();
			viewHold.messageName = (TextView) convertView.findViewById(R.id.message_name);
			viewHold.messageTime = (TextView) convertView.findViewById(R.id.message_time);
			viewHold.messageContent = (TextView) convertView.findViewById(R.id.message_content);
			
			convertView.setTag(viewHold);
		}else{
			viewHold = (ViewHold) convertView.getTag();
		}
		
		MTMessage message = (MTMessage) getItem(position);

		viewHold.messageName.setText(message.getMmTitle());
		viewHold.messageTime.setText(message.getMmAddtime());
		viewHold.messageContent.setText(message.getMmContent());
		return convertView;
	}

}
