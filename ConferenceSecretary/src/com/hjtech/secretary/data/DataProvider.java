package com.hjtech.secretary.data;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.provider.ContactsContract.CommonDataKinds.Nickname;

import com.google.gson.reflect.TypeToken;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.JsonUtils;
import com.hjtech.secretary.utils.MTCommon;
import com.hjtech.secretary.utils.NetUtils;


public class DataProvider {

	public static final String TAG = "DataProvider";
	public static final String BASE_URL = "http://211.155.229.136:8080/mettingapi/";
	public static final String METTING_LIST_URL = BASE_URL + "metting/list";
	public static final String MY_METTING_URL = BASE_URL + "metting/userMettings";
	public static final String METTING_DETAILS_URL = BASE_URL + "metting/desp";
	public static final String COLLECT_URL = BASE_URL + "metting/collect";
	public static final String COMMENT_URL = BASE_URL + "comment/list";
	public static final String VERIFY_CODE = BASE_URL + "vcode";
	public static final String VALIDATION = BASE_URL + "validation";
	public static final String REGISTER = BASE_URL + "register";
	public static final String LOGIN = BASE_URL + "users/login";
	public static final String ENROLL_URL = BASE_URL + "metting/apply";
	public static final String ADD_COMMENT_URL = BASE_URL + "comment/add";
	public static final String RELATED_METTING = BASE_URL + "metting/related";
	public static final String GET_MESSAGE = BASE_URL + "message/list";
	public static final String SINGIN = BASE_URL + "metting/sign";
	public static final String EDIT_USERINF = BASE_URL + "users/modify";

	public static final String KEY = "key";
	public static final String CODE = "code";
	public static final String MU_ACCOUNT = "muAccount";
	public static final String ACCOUNT = "account";
	public static final String PAGE = "page";
	
	public static final String SORT_ORDER = "sortOrder";
	public static final String SORT_ORDER_ASC = "asc";
	public static final String SORT_ORDER_DeSC = "desc";
	
	public static final String QUERY_TYPE = "type";
	public static final String TIME = "time";
	public static final int TIME_TODAY = 1;
	public static final int TIME_THIS_WEEK = 2;
	public static final int TIME_MONTH = 3;
	public static final int TIME_ALL = 0;

	public static final String STATUS = "status";
	public static final int STATUS_ALL = 0;
	public static final int STATUS_ENROLL = 1;
	public static final int STATUS_SIGNIN = 2;
	
	public static final String PHONE = "phone";
	public static final String VCODE = "vcode";
	public static final String MU_NAME = "muName";
	public static final String NAME = "name";
	public static final String MU_NICK_NAME = "muNickName";
	public static final String NICK_NAME = "nickName";
	public static final String SEX = "sex";
	public static final String MU_PASSWORD = "muPassword";
	public static final String PASSWORD = "password";
	public static final String NEW_PASSWORD = "password";
	
	public static final	String METTING_ID = "mettingId";
	public static final String MU_REGISTER_TYPE = "muRegisterType";
	public static final String MU_MOBILE = "muMobile";
	public static final String MU_COMPANY = "muUnitName";
	public static final String MU_POSITION = "muPosition";
	public static final String MU_WEIXIN = "muWeiXin";
	public static final String COMPANY = "company";
	public static final String POSITION = "position";
	public static final String SECTOR = "sector";
	public static final String QQ = "qq";
	public static final String EMAIL = "email";
	public static final String WEIXIN = "weixin";
	public static final String COMMENT_CONTENT = "content";
	public static final int V_TYPE_REGISTER = 1;
	public static final int V_TYPE_FORGET = 2;
	private static final String V_TYPE = "type";
	
	
	static List<MTMetting> getMettingList(Type type,String account,int page,int timeType){
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
		MTMettingListResult result = null;
		if (json != null) {
			result = (MTMettingListResult) JsonUtils.parseJsonResult(type, json);
		}else{
			return null;
		}
		if (result.getResult() <= 0) {
			return null;
		}
		return (List<MTMetting>) result.getDetails();
	}
	
	static List<MTMetting> getMyMeet(Type type,String account,int page, int status){
		Map<String, Object> params = genParems();
		if (account == null) {
			return null;
		}else{
			params.put(MU_ACCOUNT, account);
		}
		
		params.put(PAGE, page);
	
		params.put(STATUS, status);
		String json = NetUtils.getPostResult(params,MY_METTING_URL);
		MTMettingListResult result = null;
		if (json != null) {
			result = (MTMettingListResult) JsonUtils.parseJsonResult(type, json);
		}else{
			return null;
		}
		if (result.getResult() <= 0) {
			return null;
		}
		return (List<MTMetting>) result.getDetails();
	}
	
	public static Object getVerifyCode(Type type, String phone, int vType) {
		Map<String, Object> params = genParems();
		params.put(PHONE, phone);
		params.put(V_TYPE, vType);
		System.out.println(vType);
		String json = NetUtils.getPostResult(params,VERIFY_CODE);
		if (json == null) {
			return null;
		}
		return JsonUtils.parseJsonResult(type, json);
	}
	
	public static int validation(Type type, String phone, String verifyCode) {
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
		MTSimpleResult result = null;
		if (json != null) {
			result = (MTSimpleResult) JsonUtils.parseJsonResult(type, json);
			return result.getResult();
		}else{
			return -1;
		}
	}
	
	public static MTUserResult register(Type type, String account, String name,
			String nickName, String password) {
		
		Map<String, Object> params = genParems();
		params.put(MU_ACCOUNT, account);
		params.put(MU_NAME,name);
		params.put(MU_NICK_NAME,nickName);
		params.put(MU_PASSWORD, password);
		System.out.println(name);
		System.out.println(nickName);
		System.out.println(password);
		System.out.println(account);
		String json = NetUtils.getPostResult(params,REGISTER);
		MTUserResult result = null;
		if (json == null) {
			result = new MTUserResult();
		}
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			result = new MTUserResult();
			result.setResult(resultCode);
		}else{
			result = (MTUserResult) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}
	
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


	public static MTMettingResult getMettingDetails(Type type, Long id) {
		Map<String, Object> params = genParems();
		
		params.put(METTING_ID, id);
		
		String json = NetUtils.getPostResult(params,METTING_DETAILS_URL);
		MTMettingResult result = null;
		if (json != null) {
			result = (MTMettingResult) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}
	
	public static int collectMetting(Type type, Long mettingId, String account) {
		Map<String, Object> params = genParems();
		
		params.put(MU_ACCOUNT, account);
		
		params.put(METTING_ID, mettingId);
		
		String json = NetUtils.getPostResult(params, COLLECT_URL);
		MTSimpleResult result = null;
		if (json != null) {
			result = (MTSimpleResult) JsonUtils.parseJsonResult(type, json);
			return result.getResult();
		}else{
			return -1;
		}
	}
	
	public static int enrollMetting(Type type, Long id, String account,
			String name, String mobile, String company,
			String position, String weixin) {
		
		Map<String, Object> params = genParems();
		
		params.put(MU_ACCOUNT, account);
		
		params.put(METTING_ID, id);
		
		params.put(MU_NAME, name);
		
		params.put(MU_MOBILE, mobile);
		
		params.put(MU_COMPANY, company);
		
		params.put(MU_POSITION, position);
		
		params.put(MU_WEIXIN, MU_WEIXIN);
		
		String json = NetUtils.getPostResult(params,ENROLL_URL);
		MTSimpleResult result = null;
		if (json != null) {
			result = (MTSimpleResult) JsonUtils.parseJsonResult(type, json);
			return result.getResult();
		}else{
			return -1;
		}
	}
	
	public static MTCommentResult getComment(Type type, Long id, Integer page) {
		Map<String, Object> params = genParems();
		params.put(METTING_ID, id);
		params.put(PAGE, page);
		String json = NetUtils.getPostResult(params,COMMENT_URL);
		MTCommentResult result = null;
		if (json == null) {
			result = new MTCommentResult();
			result.setResult(-2);
			return result;
		}
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			result = new MTCommentResult();
		}else{
			result = (MTCommentResult) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}
	
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
	
	public static Object modifyUser(Type type, String account, String password, 
			String newPassword, String name, String nickName, int sex, 
			String company, String position, String sector, String qq,String email, String weixin){
		Map<String, Object> params = genParems();
		params.put(ACCOUNT, account);
		params.put(PASSWORD, password);
		params.put(NEW_PASSWORD, newPassword);
		params.put(NAME, name);
		params.put(NICK_NAME, nickName);
		params.put(SEX, sex);
		params.put(COMPANY, company);
		params.put(POSITION, position);
		params.put(SECTOR, sector);
		params.put(QQ, qq);
		params.put(EMAIL, email);
		params.put(WEIXIN, weixin);
		
		String json = NetUtils.getPostResult(params, EDIT_USERINF);
		
		if (json == null) {
			return null;
		}
		
		return JsonUtils.parseJsonResult(type, json);
	}
	
	private static HashMap<String, Object> genParems() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String key = Encryption.getKey();
		String code = Encryption.getCode(key);
		result.put(KEY, key);
		result.put(CODE, code);
		return result;
	}

}