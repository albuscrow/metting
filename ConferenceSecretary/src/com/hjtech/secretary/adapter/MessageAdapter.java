package com.hjtech.secretary.adapter;

import java.util.List;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.activity.MessageDetailActivity;
import com.hjtech.secretary.data.MTMessage;
import com.hjtech.secretary.listener.NewActivityListener;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * The Class MessageAdapter.
 * 用于展示消息
 * @author albuscrow
 */
public class MessageAdapter extends BaseAdapter implements ListAdapter {
	
	/** The activity. */
	private BaseActivity activity;
	
	/**
	 * Sets the data.
	 * 
	 * @param data
	 *            the new data
	 */
	public void setData(List<MTMessage> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	/** The data. */
	private List<MTMessage> data;

	/**
	 * Instantiates a new message adapter.
	 * 
	 * @param activity
	 *            the activity
	 */
	public MessageAdapter(BaseActivity activity) {
		this.activity = activity;
	}
	

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}
	

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (data != null) {
			return data.get(position);
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		MTMessage message = (MTMessage)getItem(position);
		if (message != null) {
			return message.getMmId();
		}else{
			return -1l;
		}
	}
	
	/**
	 * The Class ViewHold.
	 * 
	 * @author albuscrow
	 */
	class ViewHold{
		
		/** The message name. */
		public TextView messageName;
		
		/** The message time. */
		public TextView messageTime;
		
		/** The message content. */
		public TextView messageContent;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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
		convertView.setOnClickListener(new NewActivityListener(activity, MessageDetailActivity.class, "message", message));
		return convertView;
	}

}
