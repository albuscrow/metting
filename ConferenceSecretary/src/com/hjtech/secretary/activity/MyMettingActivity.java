package com.hjtech.secretary.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.MyMettingAdapter;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.utils.MTCommon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MyMettingActivity extends BaseActivity implements OnClickListener {
	
	public static final int SIGNIN = 10;
	private PullToRefreshListView meetList;
	private MyMettingAdapter adapter;
	
	private int currentPageNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_my_metting, R.drawable.common_back, R.string.title_activity_my_metting);
		currentPageNum = 0;
	}
	
	private void appendData() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				showWaitBar();
			}
			
			@Override
			public void onPostExecute(Object result) {
				if (result == null) {
					Toast.makeText(MyMettingActivity.this, "获取会议数据失败！", Toast.LENGTH_LONG).show();
				}else{
					adapter.setData((List<MTMetting>) result);
					adapter.appendData((List<MTMetting>) result);
				}
				hideWaitBar();
			}
		}).getMyMeet(MTUserManager.getUser().getMuAccount(), ++ currentPageNum, currentStatus);
	}

	private void getData() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				showWaitBar();
			}
			
			@Override
			public void onPostExecute(Object result) {
				if (result == null) {
					Toast.makeText(MyMettingActivity.this, "获取会议数据失败！", Toast.LENGTH_LONG).show();
				}else{
					adapter.setData((List<MTMetting>) result);
				}
				hideWaitBar();
			}
		}).getMyMeet(MTUserManager.getUser().getMuAccount(), 0, currentStatus);
	}


	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		
		//init metting list
		meetList = (PullToRefreshListView) findViewById(R.id.my_metting_list);
		adapter = new MyMettingAdapter(this);
		meetList.setAdapter(adapter);
		meetList.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				getData();
			}
		});
		
		meetList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				appendData();
			}

			
		});
		meetList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MyMettingActivity.this, MettingDetailsActivity.class);
				intent.putExtra("metting",(MTMetting)parent.getAdapter().getItem(position));
				MyMettingActivity.this.startActivity(intent);
			}
		});
		
		//init button
		findViewById(R.id.my_meet_all_button).setOnClickListener(this);
		View applyButton = findViewById(R.id.my_meet_apply_button);
		applyButton.setOnClickListener(this);
		changeStatus(applyButton, DataProvider.STATUS_ENROLL);
		findViewById(R.id.my_meet_signin_button).setOnClickListener(this);
	}

	int currentStatus = DataProvider.STATUS_ALL;
	View currentView;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_meet_all_button:
			if (currentStatus != DataProvider.STATUS_ALL) {
				changeStatus(v, DataProvider.STATUS_ALL);
			}
			break;
		case R.id.my_meet_apply_button:
			if (currentStatus != DataProvider.STATUS_ENROLL) {
				changeStatus(v,DataProvider.STATUS_ENROLL);
			}
			break;
		case R.id.my_meet_signin_button:
			if (currentStatus != DataProvider.STATUS_SIGNIN){
				changeStatus(v,DataProvider.STATUS_SIGNIN);
			}
			break;

		default:
			break;
		}
	}

	private void changeStatus(View v, int status) {
		currentStatus = status;
		v.setBackgroundResource(R.drawable.title_gray);
		if (currentView != null) {
			currentView.setBackgroundResource(R.drawable.my_metting_button_bg);
		}
		currentView = v;
		getData();
	}

	@Override
	protected void onActivityResult(int request, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			String result = data.getStringExtra("result");
			System.out.println(result);
			//TODO temp
			result = "1";
			long id = Long.parseLong(result);
			new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

				@Override
				public void onPreExecute() {
				}

				@Override
				public void onPostExecute(Object result) {
					if (result == null) {
						MTCommon.ShowToast("签到失败");
						return;
					}
					MTSimpleResult sr = (MTSimpleResult) result;
					switch (sr.getResult()) {
					case 1:
						MTCommon.ShowToast("签到成功");
						break;
					case 4:
						MTCommon.ShowToast("末报名,不能签到");
						break;
					default:
						MTCommon.ShowToast("签到失败");
						break;
					}
				}
			}).singIn(id, MTUserManager.getUser().getMuAccount());
		}
	}
}
