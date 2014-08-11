package com.hjtech.secretary.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.hjtech.secretary.common.Constants;
import com.hjtech.secretary.data.DataProvider;
import com.hjtech.secretary.fragment.InviteFragment;
import com.hjtech.secretary.utils.MTCommon;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	public static InviteFragment shareFragment;
	private IWXAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 注册微信sdk
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		api.registerApp(Constants.APP_ID);
		api.handleIntent(getIntent(), this);
		this.finish();
	}
	
	@Override
	public void onReq(BaseReq arg0) {
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
		this.finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			MTCommon.ShowToast("分享成功");
			shareFragment.addShareLog(DataProvider.WEIXIN_SHARE, null, null);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			MTCommon.ShowToast("取消分享");
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			MTCommon.ShowToast("分享失败");
			break;
		default:
			break;
		}
		
	}

}
