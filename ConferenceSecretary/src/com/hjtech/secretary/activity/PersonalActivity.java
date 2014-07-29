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
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PersonalActivity extends BaseActivity implements WeiboAuthListener {
	private MTUser user;

	/** 显示认证后的信息，如 AccessToken */
	private TextView mTokenText;

	/** 微博 Web 授权类，提供登陆等功能  */
	private WeiboAuth mWeiboAuth;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
	private Oauth2AccessToken mAccessToken;

	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	
	
	private IWXAPI api;

	private Button weibo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initAuth();
		initData();
		initUI(R.layout.activity_personal_center, R.drawable.common_back, R.string.title_activity_personal);
	}

	
	private void initAuth() {
		mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(this, mWeiboAuth);
		regToWx();
	}
	
	private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		if (mSsoHandler != null) { 
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data); 
		} 
				switch (requestCode) {

		case 0x101: {
//			final WXAppExtendObject appdata = new WXAppExtendObject();
//			final String path = CameraUtil.getResultPhotoPath(this, data, SDCARD_ROOT + "/tencent/");
//			appdata.filePath = path;
//			appdata.extInfo = "this is ext info";
//
//			final WXMediaMessage msg = new WXMediaMessage();
//			msg.setThumbImage(WeixinUtil.extractThumbNail(path, 150, 150, true));
//			msg.title = "this is title";
//			msg.description = "this is description";
//			msg.mediaObject = appdata;
//
//			SendMessageToWX.Req req = new SendMessageToWX.Req();
//			req.transaction = buildTransaction("appdata");
//			req.message = msg;
//			req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//			api.sendReq(req);

//			finish();
			break;
		}
		default:
			break;
		}
	}

	private void initData() {
		this.user = MTUserManager.getUser();
		
	}

	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		Button edit = (Button)gv(R.id.rightView);
		edit.setVisibility(View.VISIBLE);
		edit.setText("编辑");
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalActivity.this, EditUserInfActivity.class);
				PersonalActivity.this.startActivity(intent);
			}
		});
		setbackButton();
		((TextView)gv(R.id.personal_user_name_textview)).setText(user.getMuName());
		((TextView)gv(R.id.personal_base_info_textview)).setText("简介未完");
		((TextView)gv(R.id.personal_collect_metting_textview)).setText("2未完");
		((TextView)gv(R.id.personal_received_message_textview)).setText("1未完");
		((TextView)gv(R.id.personal_enroll_textview)).setText("3未完");
		
		weibo = (Button) gv(R.id.personal_banding_weibo_button);
		weibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSsoHandler.authorize(PersonalActivity.this);
			}
		});
		
		Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
		if (token != null && token.isSessionValid()) {
			weibo.setText("微博已绑定"); 
			weibo.setEnabled(false);
		}
		
		gv(R.id.personal_banding_weixin_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				final SendAuth.Req req = new SendAuth.Req();
//				req.scope = "post_timeline";
//				req.state = "none";
//				api.sendReq(req);
//				
//				final SendAuth.Req req = new SendAuth.Req();
//				req.scope = "snsapi_userinfo";
//				req.state = "wechat_sdk_demo_test";
//				api.sendReq(req);		
				WXTextObject textObj = new WXTextObject();
						textObj.text = "test";

						// ��WXTextObject�����ʼ��һ��WXMediaMessage����
						WXMediaMessage msg = new WXMediaMessage();
						msg.mediaObject = textObj;
						// �����ı����͵���Ϣʱ��title�ֶβ�������
						// msg.title = "Will be ignored";
						msg.description = "test";

						// ����һ��Req
						SendMessageToWX.Req req = new SendMessageToWX.Req();
						req.transaction = buildTransaction("text"); // transaction�ֶ�����Ψһ��ʶһ������
						req.message = msg;
						req.scene =  SendMessageToWX.Req.WXSceneTimeline; 
						
						// ����api�ӿڷ�����ݵ�΢��
						api.sendReq(req);
			}
		});
		
	}
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
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
                weibo.setText("微博已绑定"); 
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
