package com.hjtech.secretary.activity;

import java.util.List;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class MettingDetailsActivity extends BaseActivity implements OnClickListener{
	
	MTMetting metting;
	private LinearLayout relatedMettingLayout;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		initUI(R.layout.activity_metting_detail, R.drawable.common_back, R.string.title_activity_metting_details);
	}
	
//	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		// TODO Auto-generated method stub
//		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		gv(R.id.detail_collect).setOnClickListener(new OnClickListener() {
			
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
		
		relatedMettingLayout= (LinearLayout) gv(R.id.detail_relate_metting);
		fillData();
	}

	private void initData() {
		metting = (MTMetting) getIntent().getSerializableExtra("metting");
//		long id = getIntent().getLongExtra("id", 0);
//		if (id == 0) {
//			MTCommon.ShowToast("软件内部错误");
//		}
//		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
//			
//			@Override
//			public void onPreExecute() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onPostExecute(Object result) {
//				MTMettingResult mr = (MTMettingResult) result;
//				switch (mr.getResult()) {
//				case -1:
//					MTCommon.ShowToast("验证失败，非法用户");
//					break;
//				case 0:
//					MTCommon.ShowToast("获取失败");
//					break;
//				case 1:
//					metting = mr.getDetails();
//					fillData();
//					break;
//				case 2:
//					MTCommon.ShowToast("参数错误，请重新提交参数");
//					break;
//				default:
//					break;
//				}
//			}
//
//		}).getMettingDetails(id);
		
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
					 fillRelataMetting(((MTMettingListResult) result).getDetails());
				}
			}
		}).getRelatedMetting(metting.getMmId());
	}

	private void fillRelataMetting(List<MTMetting> relateMetting) {
		for (MTMetting metting : relateMetting) {
			TextView textview = (TextView) getLayoutInflater().inflate(R.layout.adapter_item_related_metting, relatedMettingLayout, false);
			textview.setTag(metting);
			textview.setText(metting.getMmTitle());
			textview.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MettingDetailsActivity.this, MettingDetailsActivity.class);
					intent.putExtra("metting", (MTMetting)v.getTag());
					MettingDetailsActivity.this.startActivity(intent);
				}
			});
			relatedMettingLayout.addView(textview);
		}
	}

	private void fillData() {
		gv(R.id.detail_enroll).setOnClickListener(new NewActivityListener(this, EnrollActivity.class, "id", metting.getMmId().toString()));
		gv(R.id.detail_comment).setOnClickListener(new NewActivityListener(this, MettingCommentActivity.class, "id", metting.getMmId().toString()));
		gv(R.id.detail_invite).setOnClickListener(new NewActivityListener(this, InviteActivity.class, "metting", metting));
		((TextView)gv(R.id.detail_time)).setText(metting.getTime());
		((TextView)gv(R.id.detail_charge_type)).setText(metting.getMmFreeTypeStr());
		((TextView)gv(R.id.detail_adress)).setText(metting.getMmAddress());
		((TextView)gv(R.id.detail_limit)).setText(metting.getMemberRttForDetail());
		((TextView)gv(R.id.detail_introdution)).setText(metting.getMmDesp());
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(MettingDetailsActivity.this, MettingDetailsActivity.class);
		long id = (Long) v.getTag();
		intent.putExtra("id", id);
		MettingDetailsActivity.this.startActivity(intent);
	}
}
