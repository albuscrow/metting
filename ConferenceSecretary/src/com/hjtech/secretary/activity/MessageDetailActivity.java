package com.hjtech.secretary.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hjtech.secretary.R;
import com.hjtech.secretary.data.MTMessage;

/**
 * The Class MessageDetailActivity.
 * 消息详情
 * @author albuscrow
 */
public class MessageDetailActivity extends BaseActivity {
	
	/** The message. */
	private MTMessage message;
	
	/** The from.
	 * 记录从哪个activity 跳转过来 
	 */
	private String from;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		if (from != null && from.equals("home")) {
			initUI(R.layout.activity_message_detail, R.string.homepage, R.string.title_activity_message_detail);
		}else{
			initUI(R.layout.activity_message_detail, R.string.title_activity_message, R.string.title_activity_message_detail);
		}
	}
	
	/**
	 * Inits the data.
	 */
	private void initData() {
		message = (MTMessage)getIntent().getSerializableExtra("message");
		from = (String) getIntent().getSerializableExtra("from");
	}

	/* (non-Javadoc)
	 * @see com.hjtech.secretary.activity.BaseActivity#initUI(int, int, int)
	 */
	@Override
	protected void initUI(int layoutId, int leftContentId, int titleId) {
		super.initUI(layoutId, leftContentId, titleId);
		setBackButton();
		((TextView)gv(R.id.message_name)).setText(message.getMmTitle());
		((TextView)gv(R.id.message_time)).setText(message.getMmAddtime());
		((TextView)gv(R.id.message_content)).setText("　　 " + message.getMmContent());
	}
}
