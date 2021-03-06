package com.hjtech.secretary.fragment;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.EditUserInfActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.utils.MTCommon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * The Class FindPasswordChangeFragment.
 * 
 * @author albuscrow
 */
public class FindPasswordChangeFragment extends BaseFragment {

	/** The phone. */
	private String phone;
	
	/** The vcode. */
	private String vcode;
	
	/** The password. */
	private EditText password;
	
	/** The password con. */
	private EditText passwordCon;
	
	/** The submit. */
	private Button submit;
	
	/**
	 * Gets the phone.
	 * 
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the data.
	 * 
	 * @param phone
	 *            the phone
	 * @param code
	 *            the code
	 */
	public void setData(String phone, String code) {
		this.phone = phone;
		this.vcode = code;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return init(inflater);
	}
	
	/**
	 * Inits the.
	 * 
	 * @param inflater
	 *            the inflater
	 * @return the view group
	 */
	public ViewGroup init(LayoutInflater inflater){
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_findpassword_change, null);
		password = (EditText)gv(R.id.change_password_editext);
		passwordCon = (EditText)gv(R.id.change_password_confirm_editext);
		submit = (Button)gv(R.id.change_password_submit_button);
		
		submit.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				final String passwordStr = MTCommon.getContent(password);
				if (passwordStr == null) {
					MTCommon.ShowToast("请输入新密码");
					return;
				}
				String passwordConStr = MTCommon.getContent(passwordCon);
				if (passwordConStr == null) {
					MTCommon.ShowToast("请再次输入新密码");
					return;
				}
				
				if (!passwordConStr.equals(passwordStr)) {
					MTCommon.ShowToast("前后两次密码输入不一致");
					return;
				}
				
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
							MTCommon.ShowToast("修改密码失败");
							return;
						}
						
						MTSimpleResult sr = (MTSimpleResult) result;
						switch (sr.getResult()) {
						case 1:
							MTUserManager.getUser().setMuPassword(passwordStr);
							Intent intent = new Intent();
							intent.putExtra("phone", phone);
							getBaseActivity().setResult(Activity.RESULT_OK, intent);
							getBaseActivity().finish();	
							break;
						case 3:
							MTCommon.ShowToast("用户不存在");
							break;

						case 4:
							MTCommon.ShowToast("验证码失效");
							break;
							
						case 5:
							MTCommon.ShowToast("验证码错误");
							break;

						default:

							MTCommon.ShowToast("修改密码失败");
							break;
						}
					}
				}).forgetPassword(phone, passwordStr, vcode);		
				
			}
		});
		return rootView;
	}

}

