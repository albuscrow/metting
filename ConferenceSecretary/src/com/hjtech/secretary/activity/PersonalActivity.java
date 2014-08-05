package com.hjtech.secretary.activity;


import com.hjtech.secretary.R;
import com.hjtech.secretary.common.Constants;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.utils.MTCommon;
import com.hjtech.secretary.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonalActivity extends BaseActivity implements WeiboAuthListener {
	private MTUser user;

	/** 微博 Web 授权类，提供登陆等功能  */
	private WeiboAuth mWeiboAuth;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
	private Oauth2AccessToken mAccessToken;

	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	
	
	private IWXAPI api;

	private RelativeLayout weibo;

	private TextView weiboText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initAuth();
		initData();
		initUI(R.layout.activity_personal_center, R.string.back, R.string.title_activity_personal, R.string.title_activity_user_edit);
	}

	
	private void initAuth() {
		mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(this, mWeiboAuth);
		regToWx();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		if (mSsoHandler != null) { 
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data); 
		} 
	}

	private void initData() {
		this.user = MTUserManager.getUser();
		
	}

	@Override
	protected void initUI(int layoutId, int iconId, int titleId, int rightId) {
		super.initUI(layoutId, iconId, titleId, rightId);
		setRightClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalActivity.this, EditUserInfActivity.class);
				PersonalActivity.this.startActivity(intent);
			}
		});
		setbackButton();
		((TextView)gv(R.id.personal_user_name_textview)).setText(user.getMuName());
		((TextView)gv(R.id.personal_collect_textview)).setText("2未完");
		((TextView)gv(R.id.personal_enroll_textview)).setText("3未完");
		
		weiboText = (TextView) gv(R.id.personal_weibo_textview);
		weibo = (RelativeLayout) gv(R.id.personal_banding_weibo_button);
		weibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSsoHandler.authorize(PersonalActivity.this);
			}
		});
		
		Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
		if (token != null && token.isSessionValid()) {
			weiboText.setText("已绑定"); 
			weibo.setEnabled(false);
		}
		
	}
	
	@Override
	public void onWeiboException(WeiboException e) {
		MTCommon.ShowToast("Auth exception : " + e.getMessage());
	}
	
	@Override
	public void onComplete(Bundle bundle) {
		// 从 Bundle 中解析 Token
		mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
		if (mAccessToken.isSessionValid()) {
			// 显示 Token
			//updateTokenView(false);

			// 保存 Token 到 SharedPreferences
			AccessTokenKeeper.writeAccessToken(this, mAccessToken);
			MTCommon.ShowToast(getResources().getString(R.string.auth_success));
			weiboText.setText("已绑定"); 
			weibo.setEnabled(false);
		} else {
			// 以下几种情况，您会收到 Code：
			// 1. 当您未在平台上注册的应用程序的包名与签名时；
			// 2. 当您注册的应用程序包名与签名不正确时；
			// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
			String code = bundle.getString("code");
			String message = getString(R.string.auth_failed);
			if (!TextUtils.isEmpty(code)) {
				message = message + "\nObtained the code: " + code;
			}
			MTCommon.ShowToast(message);
		}
	}

	@Override
	public void onCancel() {
		MTCommon.ShowToast(getResources().getString(R.string.auth_canceled));
	}
	
	private void regToWx(){
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		api.registerApp(Constants.APP_ID);
	}
}
