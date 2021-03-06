package com.hjtech.secretary.fragment;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.FindPasswordActivity;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * The Class FindPasswordVerifyFragment.
 * 
 * @author albuscrow
 */
public class FindPasswordVerifyFragment extends BaseFragment {
	
	/** The phone view. */
	private EditText phoneView;
	
	/** The verify button. */
	private Button verifyButton;
	
	/** The vcode view. */
	private EditText vcodeView;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initUI(inflater);
	}
	
	/** The phone. */
	private String phone;
	
	/**
	 * Inits the ui.
	 * 
	 * @param inflater
	 *            the inflater
	 * @return the view group
	 */
	private ViewGroup initUI(LayoutInflater inflater){
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_findpassword_verify, null);
		
		phoneView = (EditText) gv(R.id.register_tel_edittext);
		vcodeView = (EditText) gv(R.id.register_verify_edittext);
		verifyButton = (Button) gv(R.id.register_verifycode_button);
		verifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String phone = MTCommon.getContent(phoneView);
				if (phone == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (phone.length() != 11 && phone.length() != 13) {
					MTCommon.ShowToast("请输入正确的号码");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						changeButton();
					}
					
					@Override
					public void onPostExecute(Object result) {
						if (result != null && result instanceof Integer) {
					MTCommon.ShowToast("当前网络不可用,请检查网络链接");
					return;
				}	

						if (result == null) {
							MTCommon.ShowToast("网络错误！");
							return;
						}
						int resultCode = ((MTSimpleResult) result).getResult();
						switch (resultCode) {
						case -1:
							MTCommon.ShowToast("验证不通过，非法用户");
							break;
						case 0:
							MTCommon.ShowToast("获取失败，服务器内部错误");
							break;
						case 1:
							MTCommon.ShowToast("已发送验证码到您手机，请注意查收");
							break;
						case 2:
							MTCommon.ShowToast("提交参数错误");
							break;
						case 3:
							MTCommon.ShowToast("该号码已经被注册，请直接用该号码登陆");
							break;

						default:
							break;
						}
					}
					
				}).getVerifyCode(phone, DataProvider.V_TYPE_FORGET);
			}
		});
		

		gv(R.id.register_complete_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phone = MTCommon.getContent(phoneView);
				if (phone == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (phone.length() != 11 && phone.length() != 13) {
					MTCommon.ShowToast("请输入正确的号码");
					return;
				}		
				final String vcode = MTCommon.getContent(vcodeView);
				if (vcode == null) {
					MTCommon.ShowToast("请输入验证码");
					return;
				}

				if (vcode.length() != 5) {
					MTCommon.ShowToast("请输入正确的验证码");
					return;
				}
				getFindPasswordActivity().next(phone, vcode);
//				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
//
//					@Override
//					public void onPreExecute() {
//
//					}
//
//					@Override
//					public void onPostExecute(Object result) {
//
//						int resultCode = (Integer) result;
//						switch (resultCode) {
//						case -1:
//							MTCommon.ShowToast("验证不通过，非法用户");
//							break;
//						case 0:
//							MTCommon.ShowToast("获取失败，服务器内部错误");
//							break;
//						case 1:
//							MTCommon.ShowToast("验证成功");
//							getFindPasswordActivity().next(phone, vcode);
//							break;
//						case 2:
//							MTCommon.ShowToast("提交参数错误");
//							break;
//						case 3:
//							MTCommon.ShowToast("验证码失效，请重新获取");
//							break;
//						case 4:
//							MTCommon.ShowToast("验证码错误");
//							break;
//						default:
//							break;
//						}
//					}
//
//				}).Validation(phone, vcode);
			}
		});

		return rootView;
	}
	
	/**
	 * Gets the find password activity.
	 * 
	 * @return the find password activity
	 */
	private FindPasswordActivity getFindPasswordActivity() {
		return (FindPasswordActivity) getBaseActivity();
	}
	
		/**
		 * Change button.
		 */
		private void changeButton() {
		verifyButton.setEnabled(false);
		new Thread(){
			
			private int time;

			public void run() {
				
				time = 30;
				while (true) {
					if (getBaseActivity() != null) {
						getBaseActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								verifyButton.setText("请等候" + time +"秒...");
							}
						});
						if (--time == 0) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				if (getBaseActivity() != null) {
					getBaseActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							verifyButton.setEnabled(true);
							verifyButton.setText("获取验证码");
						}
					});

				}
			}
		}.start();
	}
}
