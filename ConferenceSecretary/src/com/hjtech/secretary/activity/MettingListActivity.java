package com.hjtech.secretary.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.MettingListAdapter;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class MettingListActivity extends BaseActivity {
	private PullToRefreshListView meetList;
	private MettingListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initUI(R.layout.activity_metting_list, R.drawable.common_back, R.string.title_activity_metting_list);
		
	}
	
	private void initData() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				
			}
			
			@Override
			public void onPostExecute(Object result) {
				if (result == null) {
					Toast.makeText(MettingListActivity.this, "获取会议数据失败！", Toast.LENGTH_LONG).show();
				}else{
					adapter.setData((List<MTMetting>) result);
				}
			}
		}).getMeetList(MTUserManager.getUser().getMuAccount(), 0, null, DataProvider.TYPE_ALL);
	}

	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		
		//init metting list
		meetList = (PullToRefreshListView) findViewById(R.id.my_metting_list);
		adapter = new MettingListAdapter(this);
		meetList.setAdapter(adapter);
		meetList.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				System.out.println("pull");
			}
		});
		meetList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				System.out.println("last");
			}
		});
	}	

}
