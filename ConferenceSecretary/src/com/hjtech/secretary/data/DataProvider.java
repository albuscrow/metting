package com.hjtech.secretary.data;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;















import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hjtech.secretary.fragment.MettingDetailsFragment;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.JsonUtils;
import com.hjtech.secretary.utils.NetUtils;


/**
 * The Class DataProvider.
 * 用于联网获取数据
 * @author albuscrow
 */
public class DataProvider {

	/** The Constant TAG. */
	public static final String TAG = "DataProvider";
	
	/** The Constant BASE_URL. */
	public static final String BASE_URL = "http://211.155.229.136:8080/mettingapi/";
	
	/** The Constant METTING_LIST_URL. */
	public static final String METTING_LIST_URL = BASE_URL + "metting/list";
	
	/** The Constant MY_METTING_URL. */
	public static final String MY_METTING_URL = BASE_URL + "metting/userMettings";
	
	/** The Constant GET_COLLECT. */
	public static final String GET_COLLECT = BASE_URL + "collect/list";
	
	/** The Constant METTING_DETAILS_URL. */
	public static final String METTING_DETAILS_URL = BASE_URL + "metting/desp";
	
	/** The Constant COLLECT_URL. */
	public static final String COLLECT_URL = BASE_URL + "metting/collect";
	
	/** The Constant UNCOLLECT_URL. */
	public static final String UNCOLLECT_URL = BASE_URL + "metting/cancel_collect";
	
	/** The Constant COMMENT_URL. */
	public static final String COMMENT_URL = BASE_URL + "comment/list";
	
	/** The Constant VERIFY_CODE. */
	public static final String VERIFY_CODE = BASE_URL + "vcode";
	
	/** The Constant VALIDATION. */
	public static final String VALIDATION = BASE_URL + "validation";
	
	/** The Constant REGISTER. */
	public static final String REGISTER = BASE_URL + "register";
	
	/** The Constant LOGIN. */
	public static final String LOGIN = BASE_URL + "users/login";
	
	/** The Constant ENROLL_URL. */
	public static final String ENROLL_URL = BASE_URL + "metting/apply";
	
	/** The Constant ADD_COMMENT_URL. */
	public static final String ADD_COMMENT_URL = BASE_URL + "comment/add";
	
	/** The Constant RELATED_METTING. */
	public static final String RELATED_METTING = BASE_URL + "metting/related";
	
	/** The Constant GET_MESSAGE. */
	public static final String GET_MESSAGE = BASE_URL + "message/list";
	
	/** The Constant SINGIN. */
	public static final String SINGIN = BASE_URL + "metting/sign";
	
	/** The Constant EDIT_USERINF. */
	public static final String EDIT_USERINF = BASE_URL + "users/modify";
	
	/** The Constant MODIFY_PHOTO. */
	public static final String MODIFY_PHOTO = BASE_URL + "users/modify_photo";
	
	/** The Constant FORGET_PASSWORD. */
	public static final String FORGET_PASSWORD = BASE_URL + "users/forget";
	
	/** The Constant MODIFY_PASSWORD. */
	public static final String MODIFY_PASSWORD = BASE_URL + "users/password";
	
	/** The Constant SHARE. */
	private static final String SHARE = BASE_URL + "invite/share";
	
	/** The Constant KEY. */
	public static final String KEY = "key";
	
	/** The Constant CODE. */
	public static final String CODE = "code";
	
	/** The Constant MU_ACCOUNT. */
	public static final String MU_ACCOUNT = "muAccount";
	
	/** The Constant ACCOUNT. */
	public static final String ACCOUNT = "account";
	
	/** The Constant PAGE. */
	public static final String PAGE = "page";
	
	/** The Constant SORT_ORDER. */
	public static final String SORT_ORDER = "sortOrder";
	
	/** The Constant SORT_ORDER_ASC. */
	public static final String SORT_ORDER_ASC = "desc";
	
	/** The Constant SORT_ORDER_DeSC. */
	public static final String SORT_ORDER_DeSC = "desc";
	
	/** The Constant QUERY_TYPE. */
	public static final String QUERY_TYPE = "type";
	
	/** The Constant TIME. */
	public static final String TIME = "time";
	
	/** The Constant TIME_TODAY. */
	public static final int TIME_TODAY = 1;
	
	/** The Constant TIME_THIS_WEEK. */
	public static final int TIME_THIS_WEEK = 2;
	
	/** The Constant TIME_MONTH. */
	public static final int TIME_MONTH = 3;
	
	/** The Constant TIME_ALL. */
	public static final int TIME_ALL = 0;

	/** The Constant STATUS. */
	public static final String STATUS = "status";
	
	/** The Constant STATUS_ALL. */
	public static final int STATUS_ALL = 0;
	
	/** The Constant STATUS_ENROLL. */
	public static final int STATUS_ENROLL = 1;
	
	/** The Constant STATUS_SIGNIN. */
	public static final int STATUS_SIGNIN = 2;
	
	/** The Constant STATUS_COLLECT. */
	public static final int STATUS_COLLECT = 3;
	
	/** The Constant PHONE. */
	public static final String PHONE = "phone";
	
	/** The Constant VCODE. */
	public static final String VCODE = "vcode";
	
	/** The Constant MU_NAME. */
	public static final String MU_NAME = "muName";
	
	/** The Constant NAME. */
	public static final String NAME = "name";
	
	/** The Constant MU_NICK_NAME. */
	public static final String MU_NICK_NAME = "muNickName";
	
	/** The Constant NICK_NAME. */
	public static final String NICK_NAME = "nickName";
	
	/** The Constant SEX. */
	public static final String SEX = "sex";
	
	/** The Constant MU_PASSWORD. */
	public static final String MU_PASSWORD = "muPassword";
	
	/** The Constant PASSWORD. */
	public static final String PASSWORD = "password";
	
	/** The Constant NEW_PASSWORD. */
	public static final String NEW_PASSWORD = "password";
	
	/** The Constant METTING_ID. */
	public static final	String METTING_ID = "mettingId";
	
	/** The Constant MU_REGISTER_TYPE. */
	public static final String MU_REGISTER_TYPE = "muRegisterType";
	
	/** The Constant MU_MOBILE. */
	public static final String MU_MOBILE = "muMobile";
	
	/** The Constant MU_COMPANY. */
	public static final String MU_COMPANY = "muUnitName";
	
	/** The Constant MU_POSITION. */
	public static final String MU_POSITION = "muPosition";
	
	/** The Constant MU_WEIXIN. */
	public static final String MU_WEIXIN = "muWeiXin";
	
	/** The Constant COMPANY. */
	public static final String COMPANY = "company";
	
	/** The Constant POSITION. */
	public static final String POSITION = "position";
	
	/** The Constant SECTOR. */
	public static final String SECTOR = "sector";
	
	/** The Constant QQ. */
	public static final String QQ = "qq";
	
	/** The Constant EMAIL. */
	public static final String EMAIL = "email";
	
	/** The Constant WEIXIN. */
	public static final String WEIXIN = "weixin";
	
	/** The Constant COMMENT_CONTENT. */
	public static final String COMMENT_CONTENT = "content";
	
	/** The Constant V_TYPE_REGISTER. */
	public static final int V_TYPE_REGISTER = 1;
	
	/** The Constant V_TYPE_FORGET. */
	public static final int V_TYPE_FORGET = 2;
	
	/** The Constant V_TYPE. */
	private static final String V_TYPE = "type";
	
	/** The Constant MU_EMAIL. */
	private static final String MU_EMAIL = "muEmail";
	
	/** The Constant WEIXIN_SHARE. */
	public static final int WEIXIN_SHARE = 0;
	
	/** The Constant WEIBO_SHARE. */
	public static final int WEIBO_SHARE = 1;
	
	/** The Constant MESSAGE_SHARE. */
	public static final int MESSAGE_SHARE = 2;
	
	
	/**
	 * Gets the metting list.
	 * 
	 * @param type
	 *            the type
	 * @param account
	 *            the account
	 * @param page
	 *            the page
	 * @param timeType
	 *            the time type
	 * @return the metting list
	 */
	static Object getMettingList(Type type,String account,int page,int timeType){
		Map<String, Object> params = genParems();
		if (account == null) {
			return null;
		}else{
			params.put(MU_ACCOUNT, account);
		}
		
		params.put(PAGE, page);
		
		//these parameter is deprecated
		params.put(SORT_ORDER, SORT_ORDER_ASC);
		params.put(QUERY_TYPE, 0);
		params.put(TIME, timeType);
		String json = NetUtils.getPostResult(params,METTING_LIST_URL);
		if (json == null) {
			return null;
		}
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			return JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return JsonUtils.parseJsonResult(type, json);
		}

	}
	
	/**
	 * Gets the my metting.
	 * 
	 * @param type
	 *            the type
	 * @param account
	 *            the account
	 * @param page
	 *            the page
	 * @param status
	 *            the status
	 * @return the my metting
	 */
	static Object getMyMetting(Type type,String account,int page, int status){
		Map<String, Object> params = genParems();
		
		params.put(MU_ACCOUNT, account);
		params.put(PAGE, page);
		String json = null;
		if (status == STATUS_COLLECT) {
			json = NetUtils.getPostResult(params, GET_COLLECT);
		}else{
			params.put(STATUS, status);
			json = NetUtils.getPostResult(params,MY_METTING_URL);
		}
		
		if (json == null) {
			return null;
		}
		
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1 ) {
			return JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return JsonUtils.parseJsonResult(type, json);
		}
	}
	
	/**
	 * Gets the verify code.
	 * 
	 * @param type
	 *            the type
	 * @param phone
	 *            the phone
	 * @param vType
	 *            the v type
	 * @return the verify code
	 */
	public static Object getVerifyCode(Type type, String phone, int vType) {
		Map<String, Object> params = genParems();
		params.put(PHONE, phone);
		params.put(V_TYPE, vType);
		String json = NetUtils.getPostResult(params,VERIFY_CODE);
		if (json == null) {
			return null;
		}
		return JsonUtils.parseJsonResult(type, json);
	}
	
	/**
	 * Validation.
	 * 
	 * @param type
	 *            the type
	 * @param phone
	 *            the phone
	 * @param verifyCode
	 *            the verify code
	 * @return the object
	 */
	public static Object validation(Type type, String phone, String verifyCode) {
		Map<String, Object> params = genParems();
		if (phone == null || phone.length() == 0) {
			return -2;
		}
		params.put(PHONE, phone);
		
		if (verifyCode == null || verifyCode.length() == 0) {
			return -2;
		}
		params.put(VCODE, verifyCode);
		String json = NetUtils.getPostResult(params,VALIDATION);
		if (json == null) {
			return null;
		}
		return JsonUtils.parseJsonResult(type, json);
	}
	
	/**
	 * Register.
	 * 
	 * @param type
	 *            the type
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
	 * @return the object
	 */
	public static Object register(Type type, String account, String name,
			String password, String email, String unit) {
		
		Map<String, Object> params = genParems();
		params.put(MU_ACCOUNT, account);
		params.put(MU_NAME,name);
		params.put(MU_NICK_NAME,name);
		params.put(MU_PASSWORD, password);
		params.put(MU_EMAIL, email);
		params.put("muUnitName", unit);
		
		String json = NetUtils.getPostResult(params,REGISTER);
		if (json == null) {
			return null;
		}
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			return JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return JsonUtils.parseJsonResult(type, json);
		}
	}
	
	/**
	 * Login.
	 * 
	 * @param type
	 *            the type
	 * @param account
	 *            the account
	 * @param password
	 *            the password
	 * @return the object
	 */
	public static Object login(Type type, String account, String password) {
		
		Map<String, Object> params = genParems();
		
		params.put(ACCOUNT, account);
		params.put(PASSWORD, password);
		
		String json = NetUtils.getPostResult(params,LOGIN);
		if (json == null) {
			return null;
		}
		
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			return JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return JsonUtils.parseJsonResult(type, json);
		}
		
	}


//	public static MTMettingResult getMettingDetails(Type type, Long id) {
//		Map<String, Object> params = genParems();
//		
//		params.put(METTING_ID, id);
//		
//		String json = NetUtils.getPostResult(params,METTING_DETAILS_URL);
//		MTMettingResult result = null;
//		if (json != null) {
//			result = (MTMettingResult) JsonUtils.parseJsonResult(type, json);
//		}
//		return result;
//	}
	
	/**
 * Collect metting.
 * 
 * @param type
 *            the type
 * @param mettingId
 *            the metting id
 * @param account
 *            the account
 * @param opt
 *            the opt
 * @return the object
 */
public static Object collectMetting(Type type, Long mettingId, String account, Integer opt) {
		Map<String, Object> params = genParems();
		
		String json;
		if (opt == MettingDetailsFragment.COLLECT) {
			params.put(MU_ACCOUNT, account);
			params.put(METTING_ID, mettingId);
			json = NetUtils.getPostResult(params, COLLECT_URL);
		}else{
			params.put(ACCOUNT, account);
			params.put(METTING_ID, mettingId);
			json = NetUtils.getPostResult(params, UNCOLLECT_URL);
		}

		if (json == null) {
			return null;
		}
		
		return (MTSimpleResult) JsonUtils.parseJsonResult(type, json);
	}
	
	/**
	 * Enroll metting.
	 * 
	 * @param type
	 *            the type
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
	 * @return the object
	 */
	public static Object enrollMetting(Type type, Long id, String account,
			String name, String mobile, String company,
			String email, String weixin) {
		
		Map<String, Object> params = genParems();
		
		params.put(MU_ACCOUNT, account);
		
		params.put(METTING_ID, id);
		
		params.put(MU_NAME, name);
		
		params.put(MU_MOBILE, mobile);
		
		params.put(MU_COMPANY, company);
		
		params.put(MU_EMAIL, email);
		
		String json = NetUtils.getPostResult(params,ENROLL_URL);
		if (json == null) {
			return null;
		}
		return JsonUtils.parseJsonResult(type, json);
	}
	
	/**
	 * Gets the comment.
	 * 
	 * @param type
	 *            the type
	 * @param id
	 *            the id
	 * @param page
	 *            the page
	 * @return the comment
	 */
	public static Object getComment(Type type, Long id, Integer page) {
		Map<String, Object> params = genParems();
		params.put(METTING_ID, id);
		params.put(PAGE, page);
		
		String json = NetUtils.getPostResult(params,COMMENT_URL);
		
		if (json == null) {
			return null;
		}
		
		int resultCode = JsonUtils.getResult(json);
		System.out.println(resultCode);
		if (resultCode != 1) {
			return (MTSimpleResult) JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return (MTCommentResult) JsonUtils.parseJsonResult(type, json);
		}
	}
	
	/**
	 * Adds the comment.
	 * 
	 * @param type
	 *            the type
	 * @param id
	 *            the id
	 * @param account
	 *            the account
	 * @param content
	 *            the content
	 * @return the object
	 */
	public static Object addComment(Type type, Long id, String account, String content) {
		Map<String, Object> params = genParems();
		params.put(METTING_ID, id);
		params.put(MU_ACCOUNT, account);
		params.put(COMMENT_CONTENT, content);
		
		String json = NetUtils.getPostResult(params, ADD_COMMENT_URL);
		if (json == null) {
			return null;
		}else{
			return (MTSimpleResult) JsonUtils.parseJsonResult(type, json);
		}
	}
	
	/**
	 * Gets the related metting.
	 * 
	 * @param type
	 *            the type
	 * @param id
	 *            the id
	 * @return the related metting
	 */
	public static Object getRelatedMetting(Type type, Long id) {
		Map<String, Object> params = genParems();
		
		params.put(METTING_ID, id);
		
		String json = NetUtils.getPostResult(params, RELATED_METTING);
		if (json == null) {
			return null;
		}
		int resultCode = JsonUtils.getResult(json);
		
		if (resultCode != 1) {
			return JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return JsonUtils.parseJsonResult(type, json);
		}
	}
	
	/**
	 * Gets the message.
	 * 
	 * @param type
	 *            the type
	 * @param account
	 *            the account
	 * @param page
	 *            the page
	 * @return the message
	 */
	public static Object getMessage(Type type, String account, Integer page) {
		Map<String, Object> params = genParems();
		params.put(MU_ACCOUNT, account);
		params.put(PAGE, page);
		
		String json = NetUtils.getPostResult(params, GET_MESSAGE);
		if (json == null) {
			return null;
		}
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			return JsonUtils.parseJsonResult(new TypeToken<MTSimpleResult>(){}.getType(), json);
		}else{
			return JsonUtils.parseJsonResult(type, json);
		}
	}

	/**
	 * Sign in.
	 * 
	 * @param type
	 *            the type
	 * @param id
	 *            the id
	 * @param account
	 *            the account
	 * @return the object
	 */
	public static Object signIn(Type type, Long id, String account) {
		Map<String, Object> params = genParems();
		params.put(MU_ACCOUNT, account);
		params.put(METTING_ID, id);
		
		String json = NetUtils.getPostResult(params, SINGIN);
		if (json == null) {
			return null;
		}
		return JsonUtils.parseJsonResult(type, json);
	}
	
	/**
	 * Modify password.
	 * 
	 * @param type
	 *            the type
	 * @param account
	 *            the account
	 * @param old
	 *            the old
	 * @param newP
	 *            the new p
	 * @return the object
	 */
	public static Object modifyPassword(Type type, String account,
			String old, String newP) {
		Map<String, Object> params = genParems();
		
		params.put(ACCOUNT, account);
		params.put(PASSWORD, old);
		params.put("newPassword", newP);
			
		String json = NetUtils.getPostResult(params, MODIFY_PASSWORD);
		if (json == null) {
			return null;
		}

		return JsonUtils.parseJsonResult(type, json);
	}
	

	/**
	 * Forget password.
	 * 
	 * @param type
	 *            the type
	 * @param phone
	 *            the phone
	 * @param password
	 *            the password
	 * @param code
	 *            the code
	 * @return the object
	 */
	public static Object forgetPassword(Type type, String phone,
			String password, String code) {
		Map<String, Object> params = genParems();
		params.put(MU_ACCOUNT, phone);
		params.put(PASSWORD, password);
		params.put(VCODE, code);
		
		String json = NetUtils.getPostResult(params, FORGET_PASSWORD);
		if (json == null) {
			return null;
		}
		
		MTSimpleResult parseJsonResult = (MTSimpleResult) JsonUtils.parseJsonResult(type, json);
		if (parseJsonResult.getResult() != 1) {
			Log.e(TAG, "add share log failed");
		}else{
			Log.e(TAG, "add share log success");
		}
		return parseJsonResult;
	}	
	/**
	 * Share.
	 * 
	 * @param type
	 *            the type
	 * @param account
	 *            the account
	 * @param id
	 *            the id
	 * @param typeS
	 *            the type s
	 * @param phone
	 *            the phone
	 * @param message
	 *            the message
	 * @return the object
	 */
	public static Object share(Type type, String account, long id,
			int typeS, String phone, String message) {
		Map<String, Object> params = genParems();
		params.put(MU_ACCOUNT, account);
		params.put(METTING_ID, id);
		params.put("type", typeS);
		
		if (phone != null && phone.length() != 0) {
			params.put("phone", phone);
		}
		
		if (message != null && phone.length() != 0) {
			params.put("content", message);
		}
		
		String json = NetUtils.getPostResult(params, SHARE);
		System.out.println(json);
		return null;
	}
	
	
	/**
	 * Modify user.
	 * 
	 * @param type
	 *            the type
	 * @param user
	 *            the user
	 * @return the object
	 */
	public static Object modifyUser(Type type, MTUser user){
		
		try {
			int resultCode = NetUtils.post(genParems(), user.getMuAccount(), user.getImageFile(), MODIFY_PHOTO);
			if (resultCode != 200) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		Map<String, Object> params = genParems();
		params.put(ACCOUNT, user.getMuAccount());
		if (user.getMuName() != null) {
			params.put(NAME, user.getMuName());
		}
		if (user.getMuNickName() != null) {
			params.put(NICK_NAME, user.getMuNickName());
		}
		System.out.println(user.getMuSex());
		if (user.getMuSex() != -1) {
			params.put(SEX, user.getMuSex());
		}
		if (user.getMuUnitName() != null) {
			params.put(COMPANY, user.getMuUnitName());
		}
		if (user.getMuSector() != null) {
			params.put(SECTOR, user.getMuSector());
		}
		if (user.getMuPosition() != null) {
			params.put(POSITION, user.getMuPosition());
		}
		if (user.getMuWeixin() != null) {
			params.put(WEIXIN, user.getMuWeixin());
		}
		if (user.getMuEmail() != null) {
			params.put(EMAIL, user.getMuEmail());
		}
		if (user.getMuQq() != null) {
			params.put(QQ, user.getMuQq());
		}
		
		String json = NetUtils.getPostResult(params, EDIT_USERINF);
		
		if (json == null) {
			return null;
		}
		
		return JsonUtils.parseJsonResult(type, json);
	}
	
	/**
	 * Gen parems.
	 * 
	 * @return the hash map
	 */
	private static HashMap<String, Object> genParems() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String key = Encryption.getKey();
		String code = Encryption.getCode(key);
		result.put(KEY, key);
		result.put(CODE, code);
		return result;
	}
	

}