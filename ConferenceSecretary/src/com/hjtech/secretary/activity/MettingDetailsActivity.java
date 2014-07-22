package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.RelatedMettingAdapter;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.data.MTMettingListResult;
import com.hjtech.secretary.data.MTMettingResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.MTCommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MettingDetailsActivity extends BaseActivity {
	
	MTMetting metting;
	private ListView relatedMetting;
	private RelatedMettingAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		initUI(R.layout.activity_metting_details, R.drawable.common_back, R.string.title_activity_metting_details);
	}
	
	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		// TODO Auto-generated method stub
		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		gv(R.id.detail_colloct).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onPostExecute(Object result) {
						int resultCode = (Integer) result;
						switch (resultCode) {
						case 0:
							MTCommon.ShowToast("收藏失败");
							break;
						case 1:
							MTCommon.ShowToast("收藏成功");
							break;
						case 2:
							MTCommon.ShowToast("参数错误，请检查提交参数");
							break;
						case 3:
							MTCommon.ShowToast("会员不存在");
							break;
						default:
							break;
						}
					}
				}).colloctMetting(metting.getMmId(), MTUserManager.getUser().getMuAccount());
			}
		});
		
		relatedMetting = (ListView) gv(R.id.detail_related_metting);
		adapter = new RelatedMettingAdapter(this);
		relatedMetting.setAdapter(adapter);
		relatedMetting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MettingDetailsActivity.this, MettingDetailsActivity.class);
				intent.putExtra("id", id);
				MettingDetailsActivity.this.startActivity(intent);
			}
		});
	}

	private void initData() {
		long id = getIntent().getLongExtra("id", 0);
		if (id == 0) {
			MTCommon.ShowToast("软件内部错误");
		}
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPostExecute(Object result) {
				MTMettingResult mr = (MTMettingResult) result;
				switch (mr.getResult()) {
				case -1:
					MTCommon.ShowToast("验证失败，非法用户");
					break;
				case 0:
					MTCommon.ShowToast("获取失败");
					break;
				case 1:
					metting = mr.getDetails();
					fillData();
					break;
				case 2:
					MTCommon.ShowToast("参数错误，请重新提交参数");
					break;
				default:
					break;
				}
			}

		}).getMettingDetails(id);
		
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				
			}
			
			@Override
			public void onPostExecute(Object result) {
				if (result == null) {
					MTCommon.ShowToast("获取相关会议失败");
					return;
				}
				if (result instanceof MTSimpleResult) {
					MTCommon.ShowToast("获取相关会议失败");
					return;
				}else{
					MTMettingListResult mr = (MTMettingListResult) result;
					adapter.setMettings(mr.getDetails());
				}
			}
		}).getRelatedMetting(id);
	}

	private void fillData() {
		gv(R.id.detail_enroll).setOnClickListener(new NewActivityListener(MettingDetailsActivity.this, EnrollActivity.class, "id", metting.getMmId().toString()));
		gv(R.id.detail_comment).setOnClickListener(new NewActivityListener(MettingDetailsActivity.this, MettingCommentActivity.class, "id", metting.getMmId().toString()));
	}
}
