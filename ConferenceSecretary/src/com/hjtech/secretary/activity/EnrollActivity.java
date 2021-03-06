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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * The Class EnrollActivity.
 * 报名页面
 * @author albuscrow
 */
public class EnrollActivity extends BaseActivity {
	
	/** The name. */
	private EditText name;
	
	/** The company. */
	private EditText company;
	
	/** The email. */
	private EditText email;
	
	/** The mobile. */
	private EditText mobile;
	
	/** The metting. 
	 * 	要报名的会议
	 */
	private MTMetting metting;
	
	/** The user. 
	 * 	当前的用户
	 */
	private MTUser user;

	private int typeFlag = 3;

	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		intiData();
		initUI(R.layout.activity_enroll, R.string.title_activity_metting_list, R.string.title_activity_enroll);
	}
	
	/**
	 * Inti data.
	 */
	private void intiData() {
		this.metting = (MTMetting) getIntent().getSerializableExtra("metting");
		this.user = MTUserManager.getUser();
	}

	/* (non-Javadoc)
	 * @see com.hjtech.secretary.activity.BaseActivity#initUI(int, int, int)
	 */
	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		setBackButton();
		name =(EditText) gv(R.id.enroll_name_editext);
		company =(EditText) gv(R.id.enroll_unit_editext);
		email =(EditText) gv(R.id.enroll_email_editext);
		mobile = (EditText) gv(R.id.enroll_phone_editext);
		
		String muName = user.getMuName();
		name.setText(muName);
		if (muName.length() != 0) {
			MTCommon.moveSelectionToLast(name);
		}
		String muUnitName = user.getMuUnitName();
		company.setText(muUnitName);
		if (muUnitName.length() != 0) {
			MTCommon.moveSelectionToLast(company);
		}
		String muEmail = user.getMuEmail();
		email.setText(muEmail);
		if (muEmail.length() != 0) {
			MTCommon.moveSelectionToLast(email);
		}
		String muAccount = user.getMuAccount();
		mobile.setText(muAccount);
		
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
				
				String emailStr = MTCommon.getContent(email);
				if (emailStr == null) {
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
						if (result == null) {
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
				}).enroll(metting.getMmId(), user.getMuAccount(), nameStr, 
						mobileStr, companyStr, emailStr, user.getMuWeixin(), typeFlag);
			}
		});
		
		((TextView)gv(R.id.metting_name)).setText("你报名的会议是" + metting.getMmTitle());
		
		
		Spinner sharePlatform = (Spinner) gv(R.id.enroll_type_editext);
		sharePlatform.setAdapter(ArrayAdapter.createFromResource(this, R.array.enroll_type, R.layout.enroll_type_spinner));
		sharePlatform.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					typeFlag = 3;
					break;
				case 1:
					typeFlag = 1;
					break;
				case 2:
					typeFlag = 2;
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
	}
}
