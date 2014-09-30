package com.hjtech.secretary.fragment;

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
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessageFragment extends BaseFragment {

	private PullToRefreshListView listView;
	private MessageAdapter adapter;
	private List<MTMessage> data;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initActionBar(R.string.homepage, R.string.title_activity_message, 0);
		initUI(inflater);
		initData();
		return rootView;
	}

	private View initUI(LayoutInflater inflater) {
		setbackButton();
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_message, null);
		listView = (PullToRefreshListView) gv(R.id.message_list);
		adapter = new MessageAdapter(getBaseActivity());
		listView.setAdapter(adapter);
		
		return rootView;
	}

	private void initData() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				getBaseActivity().showWaitBar();
			}

			@Override
			public void onPostExecute(Object result) {
				getBaseActivity().hideWaitBar();
				if (result != null && result instanceof Integer) {
					MTCommon.ShowToast("当前网络不可用,请检查网络链接");
					return;
				}

				if (result == null) {
					MTCommon.ShowToast("获取消息失败");
					return;
				}
				if (result instanceof MTSimpleResult) {
					MTCommon.ShowToast("获取消息失败");
					return;
				}else{
					data = ((MTMessageListResult) result).getDetails();
					adapter.setData(data);
				}
			}
		}).getMessage(MTUserManager.getUser().getMuAccount(), 0);
	}
}
