package com.hjtech.secretary.activity;


import com.hjtech.secretary.R;
import com.hjtech.secretary.common.Constants;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.utils.MTCommon;
import com.hjtech.secretary.view.NoDefaultSpinner;
import com.hjtech.secretary.weibo.AccessTokenKeeper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InviteActivity extends BaseActivity implements OnClickListener, IWeiboHandler.Response, WeiboAuthListener {
	private static final String TAG = "inviteActivity";
        
	Spinner sharePlatform;
	private TextView shareContent;
	private MTMetting metting;
	private ImageView tdCode;
	private Button confirm;
	private int sharePlatformFlag;
	public static final int NONE = 0;
	public static final int WEIXIN = 0;
	public static final int WEIBO = 1;
	
	
    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用于获取微博信息流等操作的API */
    private StatusesAPI mStatusesAPI;
	/** 微博 Web 授权类，提供登陆等功能  */
	private WeiboAuth mWeiboAuth;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
    
    /**
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {

		@Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                LogUtil.i(TAG, response);
                if (response.startsWith("{\"statuses\"")) {
                    // 调用 StatusList#parse 解析字符串成微博列表对象
                    StatusList statuses = StatusList.parse(response);
                    if (statuses != null && statuses.total_number > 0) {
                        Toast.makeText(InviteActivity.this, 
                                "获取微博信息流成功, 条数: " + statuses.statusList.size(), 
                                Toast.LENGTH_LONG).show();
                    }
                } else if (response.startsWith("{\"created_at\"")) {
                    // 调用 Status#parse 解析字符串成微博对象
                    Status status = Status.parse(response);
                    Toast.makeText(InviteActivity.this, 
                            "发送一送微博成功, id = " + status.id, 
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InviteActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            LogUtil.e(TAG, e.getMessage());
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(InviteActivity.this, info.toString(), Toast.LENGTH_LONG).show();
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initShare();
		initData();
		
		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
		// 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
		// 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
		// 失败返回 false，不调用上述回调
//		if (savedInstanceState != null) {
//			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
//		}	
//		initUI(R.layout.activity_invite, R.drawable.common_back, R.string.title_activity_invite);
	}
	
	private void initData() {
		metting = (MTMetting) getIntent().getSerializableExtra("metting");
	}

//	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		// TODO Auto-generated method stub
//		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		
		shareContent = (TextView) gv(R.id.share_invite_content);
		shareContent.setText(String.format(getResources().getString(R.string.share_content),MTUserManager.getUser().getMuName(), metting.getMmTitle()));
		
		tdCode = (ImageView) gv(R.id.share_td_code);
		ImageLoader.getInstance().displayImage(metting.getMmQr(), tdCode);
		
		sharePlatform = (NoDefaultSpinner) gv(R.id.share_platform);
		sharePlatform.setAdapter(ArrayAdapter.createFromResource(this, R.array.share_platform, android.R.layout.simple_dropdown_item_1line));
		sharePlatform.setOnItemSelectedListener(new OnItemSelectedListener() {


			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				sharePlatformFlag = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		confirm = (Button) gv(R.id.share_confirm);
		confirm.setOnClickListener(this);
	}
	
	private IWXAPI api;
	
		@Override
	public void onClick(View v) {
		String text = shareContent.getText().toString();
		switch (sharePlatformFlag) {
		case WEIXIN:
			
			// 初始化一个WXTextObject对象
			WXTextObject textObj = new WXTextObject();
			textObj.text = text;

			// 用WXTextObject对象初始化一个WXMediaMessage对象
			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = textObj;
			// 发送文本类型的消息时，title字段不起作用
			// msg.title = "Will be ignored";
			msg.description = text;

			// 构造一个Req
			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
			req.message = msg;
			req.scene = SendMessageToWX.Req.WXSceneSession;

			// 调用api接口发送数据到微信
			api.sendReq(req);
			break;
		case WEIBO:

			if (mStatusesAPI != null) {
				shareWithSina();	
			}else{
				MTCommon.ShowToast("您还未绑定新浪微博，正在调转到新浪微博进行绑定");
				mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
				mSsoHandler = new SsoHandler(this, mWeiboAuth);
				mSsoHandler.authorize(this);
			}
			
			break;

		default:
			MTCommon.ShowToast("请选择分享途径");
			break;
		}
	}

		private void shareWithSina() {
			Drawable drawable = tdCode.getDrawable();
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			String content = shareContent.getText().toString();
			mStatusesAPI.upload(content, bitmap, null, null, mListener);
		}
		
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
		
	private void initShare() {
//		// 创建微博分享接口实例
//        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
//        
//        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
//        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
//        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
//        mWeiboShareAPI.registerApp();
//        
//        // 如果未安装微博客户端，设置下载微博对应的回调
//        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
//            mWeiboShareAPI.registerWeiboDownloadListener(new IWeiboDownloadListener() {
//                @Override
//                public void onCancel() {
//                	MTCommon.ShowToast(getResources().getString(R.string.cancel_download_weibo));
//                }
//            });
//        }	
        
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.getToken().length() != 0) {
        	// 对statusAPI实例化
        	mStatusesAPI = new StatusesAPI(mAccessToken);
		}else{
			mAccessToken = null;
			mStatusesAPI = null;
		}
		
		
		regToWx();
	}
	
	private void regToWx(){
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		api.registerApp(Constants.APP_ID);
	}
	
	/**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     * 
     * @param baseRequest 微博请求数据对象
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
        case WBConstants.ErrorCode.ERR_OK:
        	MTCommon.ShowToast(getResources().getString(R.string.share_success));
            break;
        case WBConstants.ErrorCode.ERR_CANCEL:
        	MTCommon.ShowToast(getResources().getString(R.string.share_canceled));
            break;
        case WBConstants.ErrorCode.ERR_FAIL:
        	MTCommon.ShowToast(getString(R.string.share_failed) + "Error Message: " + baseResp.errMsg);
        	System.out.println(baseResp.errCode);
        	
            break;
        }
	}
	
    @Override
    protected void onNewIntent(Intent intent) {
    	setIntent(intent);
    }

	@Override
	public void onCancel() {
		MTCommon.ShowToast(getResources().getString(R.string.auth_canceled));
	}

	@Override
	public void onComplete(Bundle bundle) {
		// 从 Bundle 中解析 Token
		mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
		if (mAccessToken.isSessionValid()) {
			// 保存 Token 到 SharedPreferences
			AccessTokenKeeper.writeAccessToken(this, mAccessToken);
			MTCommon.ShowToast(getResources().getString(R.string.auth_success));
			shareWithSina();
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
	public void onWeiboException(WeiboException e) {
		MTCommon.ShowToast("Auth exception : " + e.getMessage());
	}
}
