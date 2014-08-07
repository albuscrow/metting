package com.hjtech.secretary.data;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;


import com.hjtech.secretary.R.string;
import com.hjtech.secretary.activity.MettingListActivity;
import com.hjtech.secretary.common.AppConfig;
import com.hjtech.secretary.utils.JsonTarget;
import com.hjtech.secretary.utils.MTCommon;

import android.R.integer;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

public class GetDataAnsycTask extends AsyncTask<Object, Void, Object> {

	private static final String TYPE_NOT_SUPPORTED = "type is not supported";
	private static final String TAG = "GetDataAnsycTask";

	public interface OnDataAnsyTaskListener{
		/**
		 * 失败传入null
		 * @param result
		 */
		void onPostExecute(Object result);
		void onPreExecute();
	}

	public GetDataAnsycTask setOnDataAnsyTaskListener(OnDataAnsyTaskListener onDataAnsyTaskListener){
		this.onDataAnsyTaskListener = onDataAnsyTaskListener;
		return this;
	}

	private OnDataAnsyTaskListener onDataAnsyTaskListener;
	private JsonTarget jsonTarget;


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (onDataAnsyTaskListener != null) {
			onDataAnsyTaskListener.onPreExecute();
		}
	}

	/**
	 * 第一个参数为请求数据的类型，后面为请求的url参数
	 */
	@Override
	protected Object doInBackground(Object... params) {
		jsonTarget = (JsonTarget) params[0];
		Type type;
		Object result = null;
		if (AppConfig.isNetConnect()) {
			switch (jsonTarget) {
			case MEET_LIST:
				type = new TypeToken<MTMettingListResult>(){}.getType();
				result = DataProvider.getMettingList(type, (String)params[1],(Integer)params[2], (Integer) params[3]);
				break;
			case MY_MEET:
				type = new TypeToken<MTMettingListResult>(){}.getType();
				result = DataProvider.getMyMeet(type, (String)params[1],(Integer)params[2], (Integer) params[3]);
				break;
			case VERIFY_CODE:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.getVerifyCode(type,(String)params[1], (Integer)params[2]);
				break;
			case VALIDATION:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.validation(type,(String)params[1],(String) params[2]);
				break;
			case REGISTER:
				type = new TypeToken<MTUserResult>(){}.getType();
				result = DataProvider.register(type,(String)params[1],(String) params[2],(String) params[3],(String) params[4],(String) params[5]);
				break;
			case LOGIN:
				type = new TypeToken<MTUserResult>(){}.getType();
				result = DataProvider.login(type,(String)params[1],(String) params[2]);
				break;
			case METTING_DETAILS:
				type = new TypeToken<MTMettingResult>(){}.getType();
				result = DataProvider.getMettingDetails(type,(Long)params[1]);
				break;
			case COLLECT:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.collectMetting(type,(Long)params[1],(String)params[2], (Integer) params[3]);
				break;
			case ENROLL:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.enrollMetting(type,(Long)params[1],(String)params[2],
						(String)params[3],(String)params[4],(String)params[5],
						(String)params[6],(String)params[7]);
				break;
				
			case COMMENT:
				type = new TypeToken<MTCommentResult>(){}.getType();
				result = DataProvider.getComment(type, (Long) params[1], (Integer) params[2]);
				break;
				
			case ADD_COMMENT:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.addComment(type, (Long) params[1],(String) params[2], (String) params[3]);
				break;
				
			case RELATED_METTING:
				type = new TypeToken<MTMettingListResult>(){}.getType();
				result = DataProvider.getRelatedMetting(type,(Long)params[1]);
				break;
				
			case MESSAGE:
				type = new TypeToken<MTMessageListResult>(){}.getType();
				result = DataProvider.getMessage(type,(String)params[1], (Integer)params[2]);
				break;
			case SIGNIN:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.signIn(type, (Long) params[1], (String)params[2]);
				break;
			case EDIT_USERINF:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.modifyUser(type, (MTUser) params[1]);
				break;
			case FORGET_PASSWORD:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.forgetPassword(type, (String) params[1], (String) params[2], (String) params[3]);
				break;
			case MODIFY_PASSWORD:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.modifyPassword(type, (String) params[1], (String) params[2], (String) params[3]);
				break;
			case SHARE:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.share(type, (String) params[1], (Long) params[2], (Integer) params[3]);
				break;
			default:
				Log.e(TAG, TYPE_NOT_SUPPORTED);
				break;
			}
		}else{
			MTCommon.ShowToast("无法链接到网络，请检查网络链接");
		}
		return result;
	}

	protected void onPostExecute(Object result) {
		if (onDataAnsyTaskListener != null) {
			onDataAnsyTaskListener.onPostExecute(result);
		}
	};
	
	public void getMeetList(String account, int page, int queryType){
		this.execute(JsonTarget.MEET_LIST, account, page, queryType);
	}
	
	public void getMyMeet(String account, int page, int statusType){
		this.execute(JsonTarget.MY_MEET, account, page, statusType);
	}

	public void getVerifyCode(String phone, Integer type) {
		this.execute(JsonTarget.VERIFY_CODE,phone, type);
	}
	
	public void Validation(String phone, String vCode){
		this.execute(JsonTarget.VALIDATION, phone, vCode);
	}

	public void register(String account, String name,
			String password, String email, String unit) {
		this.execute(JsonTarget.REGISTER, account, name, password, email, unit);
	}

	public void login(String account, String pwd) {
		this.execute(JsonTarget.LOGIN, account, pwd);
	}

	public void getMettingDetails(long id) {
		this.execute(JsonTarget.METTING_DETAILS, id);
	}

	public void colloctMetting(Long id, String Account, int opt) {
		this.execute(JsonTarget.COLLECT, id, Account, opt);
	}

	public void enroll(long id, String account, String name, String mobile, String company, String postion, String weixin) {
		this.execute(JsonTarget.ENROLL, id, account, name, mobile, company, postion, weixin);
	}

	public void getMettingComment(long mettingId, int page) {
		this.execute(JsonTarget.COMMENT, mettingId, page);
	}

	public void addComment(long id, String account,
			String content) {
		this.execute(JsonTarget.ADD_COMMENT, id, account, content);
	}

	public void getRelatedMetting(long id) {
		this.execute(JsonTarget.RELATED_METTING, id);
	}

	public void getMessage(String account, int page) {
		this.execute(JsonTarget.MESSAGE, account, page);
	}

	public void singIn(long id, String muAccount) {
		this.execute(JsonTarget.SIGNIN, id, muAccount);
	}


	public void modifyUserInf(MTUser user) {
		this.execute(JsonTarget.EDIT_USERINF, user);
	}

	public void forgetPassword(String phone, String passwordStr, String vcode) {
		this.execute(JsonTarget.FORGET_PASSWORD, phone, passwordStr, vcode);
	}
	

	public void modifyPassword(String muAccount, String old, String newP) {
		this.execute(JsonTarget.MODIFY_PASSWORD, muAccount, old, newP);
	}

	public void addShareLog(String muAccount, Long mmId, int type) {
		this.execute(JsonTarget.SHARE, muAccount, mmId, type);
	}


}
