package com.hjtech.secretary.Receiver;

import android.content.Context;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * The Class MyPushReceiver.
 * 
 * @author albuscrow
 */
public class MyPushReceiver extends XGPushBaseReceiver {

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onDeleteTagResult(android.content.Context, int, java.lang.String)
	 */
	@Override
	public void onDeleteTagResult(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onNotifactionClickedResult(android.content.Context, com.tencent.android.tpush.XGPushClickedResult)
	 */
	@Override
	public void onNotifactionClickedResult(Context arg0,
			XGPushClickedResult arg1) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onNotifactionShowedResult(android.content.Context, com.tencent.android.tpush.XGPushShowedResult)
	 */
	@Override
	public void onNotifactionShowedResult(Context arg0, XGPushShowedResult arg1) {
		
	}

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onRegisterResult(android.content.Context, int, com.tencent.android.tpush.XGPushRegisterResult)
	 */
	@Override
	public void onRegisterResult(Context arg0, int arg1,
			XGPushRegisterResult arg2) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onSetTagResult(android.content.Context, int, java.lang.String)
	 */
	@Override
	public void onSetTagResult(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onTextMessage(android.content.Context, com.tencent.android.tpush.XGPushTextMessage)
	 */
	@Override
	public void onTextMessage(Context arg0, XGPushTextMessage arg1) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tencent.android.tpush.XGPushBaseReceiver#onUnregisterResult(android.content.Context, int)
	 */
	@Override
	public void onUnregisterResult(Context arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
