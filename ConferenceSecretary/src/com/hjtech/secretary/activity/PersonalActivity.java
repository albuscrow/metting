package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.Constants;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.fragment.MTFragmentFactory;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.MTCommon;
import com.hjtech.secretary.weibo.AccessTokenKeeper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
		
		weiboText = (TextView) gv(R.id.personal_weibo_textview);
		weibo = (RelativeLayout) gv(R.id.personal_banding_weibo_button);
		
		Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
		if (token != null && token.isSessionValid()) {
			weiboText.setText("已绑定"); 
			weibo.setEnabled(false);
		}else{
			weiboText.setText("未绑定"); 
			weibo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mSsoHandler.authorize(PersonalActivity.this);
				}
			});
			weibo.setEnabled(true);
		}
		
		Button changeAccountButton = (Button) gv(R.id.change_account_button);
		changeAccountButton.setOnClickListener(new NewActivityListener(this, LoginActivity.class, "from", "person"));
		gv(R.id.personal_enroll_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalActivity.this, MainActivity.class);
				intent.putExtra("UIType", MTFragmentFactory.MY_METTING);
				startActivity(intent);
			}
		});
		
		gv(R.id.personal_collect_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalActivity.this, MainActivity.class);
				intent.putExtra("UIType", MTFragmentFactory.MY_METTING);
				intent.putExtra("currentPager", 2);
				startActivity(intent);
			}
		});
	}


	private void fillData() {
		final ImageView imageView = (ImageView) gv(R.id.personal_imageview);
		Bitmap photo = user.getMuPhotoImage();
		if (photo != null) {
			imageView.setImageBitmap(photo);
		}else{
			ImageLoader.getInstance().loadImage(user.getMuPhoto(), new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {

				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (loadedImage == null) {
						imageView.setImageResource(R.drawable.common_default_image);
					}else{
						imageView.setImageBitmap(loadedImage);
						PersonalActivity.this.user.setMuPhotoImage(loadedImage);
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
				}

			});
		}
		((TextView)gv(R.id.personal_account_textview)).setText(user.getMuAccount());
		((TextView)gv(R.id.personal_unit_textview)).setText(user.getMuUnitName());
		((TextView)gv(R.id.personal_user_name_textview)).setText(user.getMuName());
		((TextView)gv(R.id.personal_phone_textview)).setText(user.getMuAccount());
		((TextView)gv(R.id.personal_email_textview)).setText(user.getMuEmail());
		int coCount = user.getCoCount();
		if (coCount == -1) {
			coCount = 0;
		}
		((TextView)gv(R.id.personal_collect_textview)).setText(String.valueOf(coCount));

		int enCount = user.getEnCount();
		if (enCount == -1) {
			enCount = 0;
		}
		((TextView)gv(R.id.personal_enroll_textview)).setText(String.valueOf(enCount));
	}
	
	
	@Override
	protected void onResume() {
		user = MTUserManager.getUser();
		fillData();
		
		super.onResume();
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
	
}
