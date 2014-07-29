package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.data.MTUserResult;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.MTCommon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	private EditText phoneNum;
	private EditText passWord;
	private CheckBox remPwd;
	private View loginButton;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initUI(R.layout.activity_login, -1, R.string.title_activity_login);
	}
	
	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		
		gv(R.id.login_register_button).setOnClickListener(new NewActivityListener(this, RegisterActivity.class));
		SharedPreferences preferences = getPreferences(LoginActivity.MODE_PRIVATE);

		phoneNum = ((EditText)gv(R.id.login_phonenum));
		passWord = ((EditText)gv(R.id.login_password));
		
		String account = getIntent().getStringExtra("account");
		if (account != null) {
			phoneNum.setText(account);
			MTCommon.moveSelectionToLast(passWord);
		}else{
			phoneNum.setText(preferences.getString("acc", ""));
		}
		remPwd = ((CheckBox)gv(R.id.login_remember_password));
		if (preferences.getBoolean("rempwd", false)) {
			passWord.setText(preferences.getString("pwd", ""));
			MTCommon.moveSelectionToLast(passWord);
			remPwd.setChecked(true);
		}
		loginButton = gv(R.id.login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String account = MTCommon.getContent(phoneNum);
				if (account == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (account.length() != 11 && account.length() != 13) {
					MTCommon.ShowToast("请输入正确的号码");
					return;
				}
				
				final String pwd = MTCommon.getContent(passWord);
				if (pwd == null) {
					MTCommon.ShowToast("请输入密码");
					return;
				}
				
				String pwdMd5 = Encryption.md5(pwd);
				
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						MTCommon.ShowToast("正在登陆...");
						showWaitBar();
						loginButton.setEnabled(false);
					}
					
					@Override
					public void onPostExecute(Object result) {
						hideWaitBar();
						loginButton.setEnabled(true);
						if (result == null) {
							MTCommon.ShowToast("网络出错啦");
							return;
						}
						
						if (result instanceof MTSimpleResult) {
							MTCommon.ShowToast("用户名或密码错误");
							return;
						}
						
						if (result instanceof MTUserResult) {
							MTCommon.ShowToast("登陆成功");
							MTUserResult ur = (MTUserResult) result;
							MTUserManager.save((MTUser)ur.getDetails());
							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							Editor edit = LoginActivity.this.getPreferences(LoginActivity.MODE_PRIVATE).edit();
							if(remPwd.isChecked()){
								edit.putBoolean("rempwd", true);
								edit.putString("pwd", pwd);
							}
							edit.putString("acc", account);
							edit.commit();
							LoginActivity.this.startActivity(intent);
						}
						
					}
				}).login(account,pwdMd5);
			}
		});
		
	}


}
