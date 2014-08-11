package com.hjtech.secretary.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hjtech.secretary.R;
import com.hjtech.secretary.data.MTMessage;

public class MessageDetailActivity extends BaseActivity {
	private MTMessage message;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		initUI(R.layout.activity_message_detail, R.string.title_activity_message, R.string.title_activity_message_detail);
	}
	
	private void initData() {
		message = (MTMessage)getIntent().getSerializableExtra("message");
	}

	@Override
	protected void initUI(int layoutId, int leftContentId, int titleId) {
		super.initUI(layoutId, leftContentId, titleId);
		setbackButton();
		((TextView)gv(R.id.message_name)).setText(message.getMmTitle());
		((TextView)gv(R.id.message_time)).setText(message.getMmAddtime());
		((TextView)gv(R.id.message_content)).setText("　　 " + message.getMmContent());
	}
}
