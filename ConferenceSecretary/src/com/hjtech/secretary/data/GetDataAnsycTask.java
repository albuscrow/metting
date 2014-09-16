package com.hjtech.secretary.data;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;


import com.hjtech.secretary.common.AppConfig;
import com.hjtech.secretary.utils.JsonTarget;

import android.os.AsyncTask;
import android.util.Log;


/**
 * The Class GetDataAnsycTask.
 * 异步任务，用来联网获取数据
 * @author albuscrow
 */
public class GetDataAnsycTask extends AsyncTask<Object, Void, Object> {

	/** The Constant TYPE_NOT_SUPPORTED. */
	private static final String TYPE_NOT_SUPPORTED = "type is not supported";
	
	/** The Constant TAG. */
	private static final String TAG = "GetDataAnsycTask";

	/**
	 * The listener interface for receiving onDataAnsyTask events. The class
	 * that is interested in processing a onDataAnsyTask event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addOnDataAnsyTaskListener<code> method. When
	 * the onDataAnsyTask event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see OnDataAnsyTaskEvent
	 */
	public interface OnDataAnsyTaskListener{
		
		/**
		 * 失败传入null.
		 * 
		 * @param result
		 *            the result
		 */
		void onPostExecute(Object result);
		
		/**
		 * On pre execute.
		 */
		void onPreExecute();
	}

	/**
	 * Sets the on data ansy task listener.
	 * 该监听器用于在获取数据前后执行一些操作
	 * @param onDataAnsyTaskListener
	 *            the on data ansy task listener
	 * @return the gets the data ansyc task
	 */
	public GetDataAnsycTask setOnDataAnsyTaskListener(OnDataAnsyTaskListener onDataAnsyTaskListener){
		this.onDataAnsyTaskListener = onDataAnsyTaskListener;
		return this;
	}

	/** The on data ansy task listener. */
	private OnDataAnsyTaskListener onDataAnsyTaskListener;
	
	/** The json target. */
	private JsonTarget jsonTarget;


	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (onDataAnsyTaskListener != null) {
			onDataAnsyTaskListener.onPreExecute();
		}
	}

	/**
	 * 第一个参数为请求数据的类型，后面为请求的url参数.
	 * 
	 * @param params
	 *            the params
	 * @return the object
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
				result = DataProvider.getMyMetting(type, (String)params[1],(Integer)params[2], (Integer) params[3]);
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
//			case METTING_DETAILS:
//				type = new TypeToken<MTMettingResult>(){}.getType();
//				result = DataProvider.getMettingDetails(type,(Long)params[1]);
//				break;
			case COLLECT:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.collectMetting(type,(Long)params[1],(String)params[2], (Integer) params[3]);
				break;
			case ENROLL:
				type = new TypeToken<MTSimpleResult>(){}.getType();
				result = DataProvider.enrollMetting(type,(Long)params[1],(String)params[2],
						(String)params[3],(String)params[4],(String)params[5],
						(String)params[6],(String)params[7],(Integer)params[8]);
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
				result = DataProvider.share(type, (String) params[1], (Long) params[2], (Integer) params[3], (String) params[4], (String) params[5]);
				break;
			default:
				Log.e(TAG, TYPE_NOT_SUPPORTED);
				break;
			}
		}else{
			return -1;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	protected void onPostExecute(Object result) {
		
		if (onDataAnsyTaskListener != null) {
			onDataAnsyTaskListener.onPostExecute(result);
		}
	};
	
	/**
	 * Gets the metting list.
	 * 
	 * @param account
	 *            the account
	 * @param page
	 *            the page
	 * @param queryType
	 *            the query type
	 * @return the metting list
	 */
	public void getMettingList(String account, int page, int queryType){
		this.execute(JsonTarget.MEET_LIST, account, page, queryType);
	}
	
	/**
	 * Gets the my metting.
	 * 
	 * @param account
	 *            the account
	 * @param page
	 *            the page
	 * @param statusType
	 *            the status type
	 * @return the my metting
	 */
	public void getMyMetting(String account, int page, int statusType){
		this.execute(JsonTarget.MY_MEET, account, page, statusType);
	}

	/**
	 * Gets the verify code.
	 * 
	 * @param phone
	 *            the phone
	 * @param type
	 *            the type
	 * @return the verify code
	 */
	public void getVerifyCode(String phone, Integer type) {
		this.execute(JsonTarget.VERIFY_CODE,phone, type);
	}
	
	/**
	 * Validation.
	 * 
	 * @param phone
	 *            the phone
	 * @param vCode
	 *            the v code
	 */
	public void Validation(String phone, String vCode){
		this.execute(JsonTarget.VALIDATION, phone, vCode);
	}

	/**
	 * Register.
	 * 
	 * @param account
	 *            the account
	 * @param name
	 *            the name
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param unit
	 *            the unit
	 */
	public void register(String account, String name,
			String password, String email, String unit) {
		this.execute(JsonTarget.REGISTER, account, name, password, email, unit);
	}

	/**
	 * Login.
	 * 
	 * @param account
	 *            the account
	 * @param pwd
	 *            the pwd
	 */
	public void login(String account, String pwd) {
		this.execute(JsonTarget.LOGIN, account, pwd);
	}

//	public void getMettingDetails(long id) {
//		this.execute(JsonTarget.METTING_DETAILS, id);
//	}

	/**
 * Colloct metting.
 * 
 * @param id
 *            the id
 * @param Account
 *            the account
 * @param opt
 *            the opt
 */
public void colloctMetting(Long id, String Account, int opt) {
		this.execute(JsonTarget.COLLECT, id, Account, opt);
	}

	/**
	 * Enroll.
	 * 
	 * @param id
	 *            the id
	 * @param account
	 *            the account
	 * @param name
	 *            the name
	 * @param mobile
	 *            the mobile
	 * @param company
	 *            the company
	 * @param email
	 *            the email
	 * @param weixin
	 *            the weixin
	 */

	public void enroll(long id, String account, String name, String mobile, String company, String email, String weixin, Integer type) {
		this.execute(JsonTarget.ENROLL, id, account, name, mobile, company, email, weixin, type);
	}

	/**
	 * Gets the metting comment.
	 * 
	 * @param mettingId
	 *            the metting id
	 * @param page
	 *            the page
	 * @return the metting comment
	 */
	public void getMettingComment(long mettingId, int page) {
		this.execute(JsonTarget.COMMENT, mettingId, page);
	}

	/**
	 * Adds the comment.
	 * 
	 * @param id
	 *            the id
	 * @param account
	 *            the account
	 * @param content
	 *            the content
	 */
	public void addComment(long id, String account,
			String content) {
		this.execute(JsonTarget.ADD_COMMENT, id, account, content);
	}

	/**
	 * Gets the related metting.
	 * 
	 * @param id
	 *            the id
	 * @return the related metting
	 */
	public void getRelatedMetting(long id) {
		this.execute(JsonTarget.RELATED_METTING, id);
	}

	/**
	 * Gets the message.
	 * 
	 * @param account
	 *            the account
	 * @param page
	 *            the page
	 * @return the message
	 */
	public void getMessage(String account, int page) {
		this.execute(JsonTarget.MESSAGE, account, page);
	}

	/**
	 * Sing in.
	 * 
	 * @param id
	 *            the id
	 * @param muAccount
	 *            the mu account
	 */
	public void singIn(long id, String muAccount) {
		this.execute(JsonTarget.SIGNIN, id, muAccount);
	}


	/**
	 * Modify user inf.
	 * 
	 * @param user
	 *            the user
	 */
	public void modifyUserInf(MTUser user) {
		this.execute(JsonTarget.EDIT_USERINF, user);
	}

	/**
	 * Forget password.
	 * 
	 * @param phone
	 *            the phone
	 * @param passwordStr
	 *            the password str
	 * @param vcode
	 *            the vcode
	 */
	public void forgetPassword(String phone, String passwordStr, String vcode) {
		this.execute(JsonTarget.FORGET_PASSWORD, phone, passwordStr, vcode);
	}
	

	/**
	 * Modify password.
	 * 
	 * @param muAccount
	 *            the mu account
	 * @param old
	 *            the old
	 * @param newP
	 *            the new p
	 */
	public void modifyPassword(String muAccount, String old, String newP) {
		this.execute(JsonTarget.MODIFY_PASSWORD, muAccount, old, newP);
	}

	/**
	 * Adds the share log.
	 * 
	 * @param muAccount
	 *            the mu account
	 * @param mmId
	 *            the mm id
	 * @param type
	 *            the type
	 * @param phone
	 *            the phone
	 * @param message
	 *            the message
	 */
	public void addShareLog(String muAccount, Long mmId, int type, String phone, String message) {
		this.execute(JsonTarget.SHARE, muAccount, mmId, type, phone, message);
	}
}
