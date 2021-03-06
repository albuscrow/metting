package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.data.MTUserResult;
import com.hjtech.secretary.utils.MTCommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The Class RegisterActivity.
 * 注册
 * @author albuscrow
 */
public class RegisterActivity extends BaseActivity{
	
	/** The phone view. */
	private EditText phoneView;
	
	/** The verify button. */
	private Button verifyButton;
	
	/** The vcode view. */
	private EditText vcodeView;
	
	/** The name text view. */
	private EditText nameTextView;
	
	/** The password view. */
	private EditText passwordView;
	
	/** The password con view. */
	private EditText passwordConView;
	
	/** The email view. */
	private EditText emailView;
	
	/** The phone. */
	private String phone;
	
	/** The unit view. */
	private EditText unitView;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_register, R.string.title_activity_register);
	}
	
	/* (non-Javadoc)
	 * @see com.hjtech.secretary.activity.BaseActivity#initUI(int, int)
	 */
	@Override
	protected void initUI(int layoutId, int titleId) {
		super.initUI(layoutId, titleId);
		
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
							Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
							intent.putExtra("account", phone);
							startActivity(intent);
							break;

						default:
							break;
						}
					}

					
				}).getVerifyCode(phone, DataProvider.V_TYPE_REGISTER);
			}
		});
		
		
		nameTextView = (EditText) gv(R.id.register_name_edittext);
		passwordView = (EditText)gv(R.id.register_password_editext);
		passwordConView = (EditText)gv(R.id.register_password_confirm_editext);
		emailView = (EditText)gv(R.id.register_email_edittext);	
		unitView = (EditText) gv(R.id.register_unit_edittext);
		
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
				String vcode = MTCommon.getContent(vcodeView);
				if (vcode == null) {
					MTCommon.ShowToast("请输入验证码");
					return;
				}
				
				if (vcode.length() != 5) {
					MTCommon.ShowToast("请输入正确的验证码");
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
							MTCommon.ShowToast("验证失败");
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
							register();
							break;
						case 2:
							MTCommon.ShowToast("提交参数错误");
							break;
						case 3:
							MTCommon.ShowToast("验证码失效，请重新获取");
							break;
						case 4:
							MTCommon.ShowToast("验证码错误");
							break;
						default:
							break;
						}
					}
				}).Validation(phone, vcode);
			}
		});

	}
	
	/**
	 * Change button.
	 * 改变获取验证码显示内容
	 */
	private void changeButton() {
		verifyButton.setEnabled(false);
		new Thread(){
			
			private int time;

			public void run() {
				
				time = 30;
				while (true) {
					if (RegisterActivity.this != null) {
						RegisterActivity.this.runOnUiThread(new Runnable() {
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

				if (RegisterActivity.this != null) {
					RegisterActivity.this.runOnUiThread(new Runnable() {

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
	
	/**
	 * Register.
	 */
	private void register(){
		
		final String name = MTCommon.getContent(nameTextView);
		if(name == null){
			MTCommon.ShowToast("请输入名称");
			return;
		}

		final String password = MTCommon.getContent(passwordView);
		if(password == null){
			MTCommon.ShowToast("请输入密码");
			return;
		}

		String passwordConfirm = MTCommon.getContent(passwordConView);
		if(passwordConfirm == null){
			MTCommon.ShowToast("请再次输入密码");
			return;
		}
		if (!password.equals(passwordConfirm)) {
			MTCommon.ShowToast("密码不一致");
			return;
		}
		final String email = MTCommon.getContent(emailView);
		if (email == null) {
			MTCommon.ShowToast("请输入邮箱地址");
			return;
		}
		if (!MTCommon.isEmail(email)) {
			MTCommon.ShowToast("请输入正确的邮箱地址");
			return;
		}
		final String unit = MTCommon.getContent(unitView);
		if (unit == null) {
			MTCommon.ShowToast("请输入公司名称");
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

				if (result == null || result instanceof MTSimpleResult) {
					MTCommon.ShowToast("注册失败");
					return;
				}
				MTUserResult ur = (MTUserResult) result;
				MTCommon.ShowToast("注册成功");
				MTUserManager.save((MTUser)ur.getDetails());
				startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
			}
		}).register(phone,name,password,email,unit);
	}

}