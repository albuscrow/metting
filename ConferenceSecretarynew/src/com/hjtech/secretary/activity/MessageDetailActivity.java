package com.hjtech.secretary.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hjtech.secretary.R;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMessage;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

public class MessageDetailActivity extends BaseActivity {
	private MTMessage message;
	private String from;
	private XGPushClickedResult clicked;

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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		clicked = XGPushManager.onActivityStarted(this);
		if (clicked == null) {
			return;
		}
		if (clicked.getActionType() == 1) {
			finish();
			return;
		}
		String content = clicked.getContent();
		String title = clicked.getTitle();
		clicked.getCustomContent();
		((TextView)gv(R.id.message_name)).setText(title);
		String customContent = clicked.getCustomContent();
		JsonObject jo = new JsonParser().parse(customContent).getAsJsonObject();
		JsonElement jsonElement = jo.get("messageId");
		if (jsonElement != null && !jsonElement.isJsonNull()) {
//			((TextView)gv(R.id.message_time)).setText(jsonElement.getAsString());
			new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
				
				@Override
				public void onPreExecute() {
					
				}
				
				@Override
				public void onPostExecute(Object result) {
					JsonObject jo = new JsonParser().parse(result.toString()).getAsJsonObject();
					message = new Gson().fromJson(jo.get("result"), MTMessage.class);
					((TextView)gv(R.id.message_name)).setText(message.getMmTitle());
					((TextView)gv(R.id.message_time)).setText(message.getMmAddtime());
					((TextView)gv(R.id.message_content)).setText("　　 " + message.getMmContent());
				}
			}).getMessage(jsonElement.getAsString());;
		}
//		((TextView)gv(R.id.message_content)).setText("　　 " + content);
	}
	
	@Override
	protected void onPause() {
		XGPushManager.onActivityStoped(this);
		super.onPause();
	}
	
	private void initData() {
		message = (MTMessage)getIntent().getSerializableExtra("message");
		from = (String) getIntent().getSerializableExtra("from");
	}

	@Override
	protected void initUI(int layoutId, int leftContentId, int titleId) {
		super.initUI(layoutId, leftContentId, titleId);
		setbackButton();
		if (message != null) {
			((TextView)gv(R.id.message_name)).setText(message.getMmTitle());
			((TextView)gv(R.id.message_time)).setText(message.getMmAddtime());
			((TextView)gv(R.id.message_content)).setText("　　 " + message.getMmContent());
		}
	}
}
