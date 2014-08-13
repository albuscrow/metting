package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class EnrollActivity extends BaseActivity {
	private EditText name;
	private EditText company;
	private EditText email;
	private EditText mobile;
	private MTMetting metting;
	private MTUser user;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		intiData();
		initUI(R.layout.activity_enroll, R.string.title_activity_metting_list, R.string.title_activity_enroll);
	}
	
	private void intiData() {
		this.metting = (MTMetting) getIntent().getSerializableExtra("metting");
		this.user = MTUserManager.getUser();
	}

	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		name =(EditText) gv(R.id.enroll_name_editext);
		company =(EditText) gv(R.id.enroll_unit_editext);
		email =(EditText) gv(R.id.enroll_email);
		mobile = (EditText) gv(R.id.enroll_phone_editext);
		
		name.setText(user.getMuName());
		company.setText(user.getMuUnitName());
		email.setText(user.getMuEmail());
		mobile.setText(user.getMuAccount());
		
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
				
				String postStr = MTCommon.getContent(email);
				if (postStr == null) {
					MTCommon.ShowToast("请输入职务");
					return;
				}
				
				String mobileStr = MTCommon.getContent(mobile);
				if (mobileStr == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (mobileStr.length() != 11 && mobileStr.length() != 13) {
					MTCommon.ShowToast("请输入正确的手机号");
					return;
				}
				
				MTUser user = MTUserManager.getUser();
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

					@Override
					public void onPreExecute() {

					}

					@Override
					public void onPostExecute(Object result) {
						if (result != null && result instanceof Integer) {
							MTCommon.ShowToast("当前网络不可用,请检查网络链接");
							return;
						}	
						MTSimpleResult sr = (MTSimpleResult) result;
						int resultCode = sr.getResult();
						switch (resultCode) {
						case 1:
							MTCommon.ShowToast("报名成功");
							MTUserManager.getUser().addEnroll();
							EnrollActivity.this.finish();
							break;
						case 2:
							MTCommon.ShowToast("请检查参数");
							break;
						case 3:
							MTCommon.ShowToast("会员不存在");
							break;
						case 4:
							MTCommon.ShowToast("您已经报名过此会议");
							EnrollActivity.this.finish();
							break;
						default:
							MTCommon.ShowToast("报名失败，未知错误");
							break;
						}
					}
				}).enroll(metting.getMmId(), user.getMuAccount(), nameStr, mobileStr, companyStr, postStr, user.getMuWeixin());
				System.out.println(metting.getMmId());
			}
		});
		
		((TextView)gv(R.id.metting_name)).setText("你报名的会议是" + metting.getMmTitle());
	}
}
