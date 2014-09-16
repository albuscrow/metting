package com.hjtech.secretary.fragment;

import java.util.HashSet;
import java.util.Set;

import com.hjtech.secretary.R;
import com.hjtech.secretary.R.string;
import com.hjtech.secretary.activity.MainActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.utils.MTCommon;
import com.hjtech.secretary.weibo.AccessTokenKeeper;
import com.hjtech.secretary.wxapi.WXEntryActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.mid.util.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Class InviteFragment.
 * 
 * @author albuscrow
 */
public class InviteFragment extends BaseFragment implements OnClickListener, IWeiboHandler.Response, WeiboAuthListener {

	/** The Constant TAG. */
	private static final String TAG = "inviteActivity";

	/** The share platform. */
	Spinner sharePlatform;
	
	/** The share content. */
	private TextView shareContent;
	
	/** The metting. */
	private MTMetting metting;
	
	/** The td code. */
	private ImageView tdCode;
	
	/** The confirm. */
	private TextView confirm;
	
	/** The share platform flag. */
	private int sharePlatformFlag;
	
	/** The Constant NONE. */
	public static final int NONE = -1;
	
	/** The Constant WEIXIN_CIRLE. */
	public static final int WEIXIN_CIRLE = 0;
	
	/** The Constant WEIXIN_FRIEND. */
	public static final int WEIXIN_FRIEND = 1;
	
	/** The Constant WEIBO. */
	public static final int WEIBO = 2;
	
	/** The Constant MESSAGE. */
	public static final int MESSAGE = 3;


	/** 微博 OpenAPI 回调接口。. */
	private RequestListener mListener = new RequestListener() {

		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				LogUtil.i(TAG, response);
				if (response.startsWith("{\"statuses\"")) {
					// 调用 StatusList#parse 解析字符串成微博列表对象
					StatusList statuses = StatusList.parse(response);
					if (statuses != null && statuses.total_number > 0) {
						MTCommon.ShowToast("获取微博信息流成功, 条数: " + statuses.statusList.size());
					}
				} else if (response.startsWith("{\"created_at\"")) {
					// 调用 Status#parse 解析字符串成微博对象
					addShareLog(DataProvider.WEIBO_SHARE, null, null);
					confirm.setEnabled(true);
					MTCommon.ShowToast("分享成功");
				} else {
					MTCommon.ShowToast(response);
				}
			}
		}
		

		@Override
		public void onWeiboException(WeiboException e) {
			LogUtil.e(TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			MTCommon.ShowToast(info.toString());
		}
	};
	

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initActionBar(R.string.title_activity_metting_details, R.string.title_activity_invite, 0);

		initShare();
		initData();
		WXEntryActivity.shareFragment = this;
		return initUI(inflater);
	}

	/**
	 * Inits the share.
	 */
	private void initShare() {

	}

	/**
	 * Inits the data.
	 */
	private void initData() {
		metting = (MTMetting) getIntent().getSerializableExtra("metting");
	}

	/**
	 * Inits the ui.
	 * 
	 * @param inflater
	 *            the inflater
	 * @return the view group
	 */
	private ViewGroup initUI(LayoutInflater inflater) {
		setbackButton();
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_invite, null);

		shareContent = (TextView) gv(R.id.share_invite_content);
		shareContent.setText(String.format(getResources().getString(R.string.share_content),MTUserManager.getUser().getMuName(), metting.getMmTitle()));

		tdCode = (ImageView) gv(R.id.share_td_code);
		
		ImageLoader.getInstance().loadImage(metting.getMmEnqr(), new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (loadedImage == null) {
						tdCode.setImageResource(R.drawable.common_default_image);
					}else{
						tdCode.setImageBitmap(loadedImage);
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
				}

			});

		sharePlatform = (Spinner) gv(R.id.share_platform);
		sharePlatform.setAdapter(ArrayAdapter.createFromResource(getBaseActivity(), R.array.share_platform, R.layout.share_spinner));
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

		confirm = (TextView) gv(R.id.share_confirm);
		confirm.setOnClickListener(this);
		
		
				return rootView;
	}


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		String text = shareContent.getText().toString();
		switch (sharePlatformFlag) {
		case WEIXIN_CIRLE:
			WXWebpageObject webpage = new WXWebpageObject();  
			webpage.webpageUrl = metting.getMmEnpage();
			System.out.println(metting.getMmEnpage());
			WXMediaMessage msg = new WXMediaMessage(webpage);  
			msg.title = "会小秘";  
			msg.description = text;

			try{  
				Bitmap bmp = MTCommon.getImageFromView(tdCode);
				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);  
				bmp.recycle();  
				msg.setThumbImage(thumbBmp);  
			}catch(Exception e){  
				e.printStackTrace();  
			}
			SendMessageToWX.Req req = new SendMessageToWX.Req();  
			req.transaction = String.valueOf(System.currentTimeMillis());  
			req.message = msg;  
			req.scene = SendMessageToWX.Req.WXSceneTimeline;  
			getMainActivity().api.sendReq(req);
			break;
			
		case WEIXIN_FRIEND:
			WXWebpageObject webpage2 = new WXWebpageObject();  
			webpage2.webpageUrl = metting.getMmEnpage();
			WXMediaMessage msg2 = new WXMediaMessage(webpage2);  
			msg2.title = "会小秘";  
			msg2.description = text;

			try{  
				Bitmap bmp = MTCommon.getImageFromView(tdCode);
				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);  
				bmp.recycle();  
				msg2.setThumbImage(thumbBmp);  
			}catch(Exception e){  
				e.printStackTrace();  
			}
			SendMessageToWX.Req req2 = new SendMessageToWX.Req();  
			req2.transaction = String.valueOf(System.currentTimeMillis());  
			req2.message = msg2;  
			req2.scene = SendMessageToWX.Req.WXSceneSession;  
			getMainActivity().api.sendReq(req2);
			break;
		case WEIBO:
			if (getMainActivity().mStatusesAPI != null) {
				shareWithSina();	
			}else{
				MTCommon.ShowToast("您还未绑定新浪微博，正在调转到新浪微博进行绑定");
				getMainActivity().mSsoHandler.authorize(this);
			}
			break;
		case MESSAGE:
			Uri smsToUri = Uri.parse("smsto");  
			Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);  
			String message = "我是"+MTUserManager.getUser().getMuName()+",有个\"" + metting.getMmTitle() + "\"不错，我邀请你参加，下面是这个会议的网址，点进去就可以报名了:\n"+metting.getMmEnpage()+"\n（wps·会小秘）";
			sendIntent.putExtra("sms_body", message);
			sendIntent.putExtra("exit_on_sent", true);
			sendIntent.setType("vnd.android-dir/mms-sms");
			startActivityForResult(sendIntent,MESSAGE_CODE); 
			addShareLog(DataProvider.MESSAGE_SHARE, null, null);
			break;
			
		default:
			MTCommon.ShowToast("请选择分享途径");
			break;
		}
	}
	
	/** The message code. */
	static public int MESSAGE_CODE = 1002;

	/**
	 * Gets the main activity.
	 * 
	 * @return the main activity
	 */
	private MainActivity getMainActivity() {
		return ((MainActivity) getBaseActivity());
	}

	/**
	 * Adds the share log.
	 * 
	 * @param type
	 *            the type
	 * @param phone
	 *            the phone
	 * @param content
	 *            the content
	 */
	public void addShareLog(int type, String phone, String content) {
		new GetDataAnsycTask().addShareLog(MTUserManager.getUser().getMuAccount(), metting.getMmId(), type, phone, content);
	}
	

	/**
	 * Share with sina.
	 */
	private void shareWithSina() {
		confirm.setEnabled(false);
		MTCommon.ShowToast("正在分享到微博，请稍后...");
		Bitmap bitmap = MTCommon.getImageFromView(tdCode);
		String content = shareContent.getText().toString();
		getMainActivity().mStatusesAPI.upload(content, bitmap, null, null, mListener);
	}

	/**
	 * 接收微客户端博请求的数据。 当微博客户端唤起当前应用并进行分享时，该方法被调用。.
	 * 
	 * @param baseResp
	 *            the base resp
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

	/* (non-Javadoc)
	 * @see com.sina.weibo.sdk.auth.WeiboAuthListener#onCancel()
	 */
	@Override
	public void onCancel() {
		MTCommon.ShowToast(getResources().getString(R.string.auth_canceled));
	}

	/* (non-Javadoc)
	 * @see com.sina.weibo.sdk.auth.WeiboAuthListener#onComplete(android.os.Bundle)
	 */
	@Override
	public void onComplete(Bundle bundle) {
		// 从 Bundle 中解析 Token
		getMainActivity().mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
		if (getMainActivity().mAccessToken.isSessionValid()) {
			// 保存 Token 到 SharedPreferences
			AccessTokenKeeper.writeAccessToken(getBaseActivity(), getMainActivity().mAccessToken);
			MTCommon.ShowToast(getResources().getString(R.string.auth_success));
			getMainActivity().mStatusesAPI = new StatusesAPI(getMainActivity().mAccessToken);
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

	/* (non-Javadoc)
	 * @see com.sina.weibo.sdk.auth.WeiboAuthListener#onWeiboException(com.sina.weibo.sdk.exception.WeiboException)
	 */
	@Override
	public void onWeiboException(WeiboException e) {
		MTCommon.ShowToast("Auth exception : " + e.getMessage());
	}
}
