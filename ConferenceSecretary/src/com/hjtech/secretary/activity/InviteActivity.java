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

public class InviteActivity extends BaseActivity implements OnClickListener, IWeiboHandler.Response {
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
		initUI(R.layout.activity_invite, R.drawable.common_back, R.string.title_activity_invite);
	}
	
	private void initData() {
		metting = (MTMetting) getIntent().getSerializableExtra("metting");
	}

	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		// TODO Auto-generated method stub
		super.initUI(layoutId, iconId, titleId);
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
//			try {
//                // 检查微博客户端环境是否正常，如果未安装微博，弹出对话框询问用户下载微博客户端
//                if (mWeiboShareAPI.checkEnvironment(true)) {                    
//                    sendMessage(true, true, false, false, false, false);
//                }
//            } catch (WeiboShareException e) {
//                e.printStackTrace();
//                MTCommon.ShowToast(e.getMessage());
//            }
			System.out.println("InviteActivity.onClick()");
			Drawable drawable = tdCode.getDrawable();
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			mStatusesAPI.upload("发送一条带本地图片的微博", bitmap, null, null, mListener);
			break;

		default:
			MTCommon.ShowToast("请选择分享途径");
			break;
		}
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
        // 对statusAPI实例化
        mStatusesAPI = new StatusesAPI(mAccessToken);
		
		
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
	
//	/**
//     * 第三方应用发送请求消息到微博，唤起微博分享界面。
//     * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
//     */
//    private void sendMessage(boolean hasText, boolean hasImage, 
//			boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
//        
//        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
//            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
//            if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
//                sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
//            } else {
//                sendSingleMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo/*, hasVoice*/);
//            }
//        } else {
//        	MTCommon.ShowToast(getResources().getString(R.string.weibo_not_support_api_hint));
//        }
//    }

//    /**
//     * 第三方应用发送请求消息到微博，唤起微博分享界面。
//     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
//     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
//     * 
//     * @param hasText    分享的内容是否有文本
//     * @param hasImage   分享的内容是否有图片
//     * @param hasWebpage 分享的内容是否有网页
//     * @param hasMusic   分享的内容是否有音乐
//     * @param hasVideo   分享的内容是否有视频
//     * @param hasVoice   分享的内容是否有声音
//     */
//    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
//            boolean hasMusic, boolean hasVideo, boolean hasVoice) {
//        
//        // 1. 初始化微博的分享消息
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        if (hasText) {
//            weiboMessage.textObject = getTextObj();
//        }
//        
//        if (hasImage) {
//            weiboMessage.imageObject = getImageObj();
//        }
//        
//        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
//        if (hasWebpage) {
//            weiboMessage.mediaObject = getWebpageObj();
//        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        if (hasVoice) {
//            weiboMessage.mediaObject = getWebpageObj();
//        }
//        
//        // 2. 初始化从第三方到微博的消息请求
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//        
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        mWeiboShareAPI.sendRequest(request);
//    }

//    private BaseMediaObject getVideoObj() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private BaseMediaObject getMusicObj() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private BaseMediaObject getWebpageObj() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private ImageObject getImageObj() {
//		ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) tdCode.getDrawable();
//        imageObject.setImageObject(bitmapDrawable.getBitmap());
//        return imageObject;
//	}
//
//	private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = shareContent.getText().toString();
//        return textObject;
//	}
//
//	/**
//     * 第三方应用发送请求消息到微博，唤起微博分享界面。
//     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
//     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
//     * 
//     * @param hasText    分享的内容是否有文本
//     * @param hasImage   分享的内容是否有图片
//     * @param hasWebpage 分享的内容是否有网页
//     * @param hasMusic   分享的内容是否有音乐
//     * @param hasVideo   分享的内容是否有视频
//     */
//    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
//            boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {
//        
//        // 1. 初始化微博的分享消息
//        // 用户可以分享文本、图片、网页、音乐、视频中的一种
//        WeiboMessage weiboMessage = new WeiboMessage();
//        if (hasText) {
//            weiboMessage.mediaObject = getTextObj();
//        }
//        if (hasImage) {
//            weiboMessage.mediaObject = getImageObj();
//        }
//        if (hasWebpage) {
//            weiboMessage.mediaObject = getWebpageObj();
//        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        /*if (hasVoice) {
//            weiboMessage.mediaObject = getVoiceObj();
//        }*/
//        
//        // 2. 初始化从第三方到微博的消息请求
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.message = weiboMessage;
//        
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        mWeiboShareAPI.sendRequest(request);
//    }
    @Override
    protected void onNewIntent(Intent intent) {
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
//        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }
}
