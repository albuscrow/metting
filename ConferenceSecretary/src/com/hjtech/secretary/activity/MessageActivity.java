package com.hjtech.secretary.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.MessageAdapter;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMessage;
import com.hjtech.secretary.data.MTMessageListResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;

public class MessageActivity extends BaseActivity {
	private PullToRefreshListView listView;
	private MessageAdapter adapter;
	private List<MTMessage> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_message, R.drawable.common_back,R.string.title_activity_message);
		initData();
	}

	private void initData() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				
			}
			
			@Override
			public void onPostExecute(Object result) {
				if (result == null) {
					MTCommon.ShowToast("获取评论失败");
					return;
				}
				if (result instanceof MTSimpleResult) {
					MTCommon.ShowToast("获取评论失败");
					return;
				}else{
					MTMessageListResult mr = (MTMessageListResult) result;
					adapter.setData(mr.getDetails());
				}
			}
		}).getMessage(MTUserManager.getUser().getMuAccount(), 0);
	}

//	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		// TODO Auto-generated method stub
//		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		
		listView = (PullToRefreshListView) gv(R.id.message_list);
		adapter = new MessageAdapter(this);
		listView.setAdapter(adapter);
		adapter.setData(data);
	}
}
