package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class EnrollActivity extends BaseActivity {
	private EditText name;
	private EditText company;
	private EditText post;
	private EditText mobile;
	private long mettingId;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		intiData();
		initUI(R.layout.activity_enroll, R.drawable.common_back, R.string.title_activity_enroll);
	}
	
	private void intiData() {
		String idStr = (String) getIntent().getSerializableExtra("id");
		this.mettingId = Long.parseLong(idStr);
	}

	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		name =(EditText) gv(R.id.enroll_name_editext);
		company =(EditText) gv(R.id.enroll_company_edittext);
		post =(EditText) gv(R.id.enroll_post_edittext);
		mobile = (EditText) gv(R.id.enroll_phone_edittext);
		
		gv(R.id.enroll_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String nameStr = MTCommon.getContent(name);
				if(nameStr == null){
					MTCommon.ShowToast("请输入姓名");
					return;
				}
				
				String companyStr = MTCommon.getContent(company);
				if (companyStr == null) {
					MTCommon.ShowToast("请输入公司名");
					return;
				}
				
				String postStr = MTCommon.getContent(post);
				if (postStr == null) {
					MTCommon.ShowToast("请输入职务");
					return;
				}
				
				String mobileStr = MTCommon.getContent(mobile);
				if (mobileStr == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				
				MTUser user = MTUserManager.getUser();
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						
					}
					
					@Override
					public void onPostExecute(Object result) {
						int resultCode = (Integer) result;
						switch (resultCode) {
						case 1:
							MTCommon.ShowToast("报名成功");
							EnrollActivity.this.finish();
							break;
						case 2:
							MTCommon.ShowToast("请检查参数");
							break;
						case 3:
							MTCommon.ShowToast("会员不存在");
							break;
						default:
							MTCommon.ShowToast("报名失败，未知错误");
							break;
						}
					}
				}).enroll(mettingId, user.getMuAccount(), nameStr, mobileStr, companyStr, postStr, user.getMuWeixin());
			}
		});
	}
}
