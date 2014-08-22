package com.hjtech.secretary.data;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;















import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hjtech.secretary.common.AppVersion;
import com.hjtech.secretary.fragment.MettingDetailsFragment;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.JsonUtils;
import com.hjtech.secretary.utils.NetUtils;


public class DataProvider {

	public static final String TAG = "DataProvider";
//	public static final String BASE_URL = "http://42.62.41.98:8080/meetingapi/";
//	public static final String BASE_URL_IMAGE = "http://42.62.41.98:8081/";
	public static final String BASE_URL = "http://211.155.229.136:8080/mettingapi/";
	public static final String BASE_URL_IMAGE = "http://211.155.229.136:8050/";
	public static final String METTING_LIST_URL = BASE_URL + "metting/list";
	public static final String MY_METTING_URL = BASE_URL + "metting/userMettings";
	public static final String GET_COLLECT = BASE_URL + "collect/list";
	public static final String METTING_DETAILS_URL = BASE_URL + "metting/desp";
	public static final String COLLECT_URL = BASE_URL + "metting/collect";
	public static final String UNCOLLECT_URL = BASE_URL + "metting/cancel_collect";
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
	public static final String MODIFY_PHOTO = BASE_URL + "users/modify_photo";
	public static final String FORGET_PASSWORD = BASE_URL + "users/forget";
	public static final String MODIFY_PASSWORD = BASE_URL + "users/password";
	private static final String SHARE = BASE_URL + "invite/share";
	private static final String CHECK_APP_VERSION = BASE_URL + "app/check_version";
	
	public static final String KEY = "key";
	public static final String CODE = "code";
	public static final String MU_ACCOUNT = "muAccount";
	public static final String ACCOUNT = "account";
	public static final String PAGE = "page";
	
	public static final String SORT_ORDER = "sortOrder";
	public static final String SORT_ORDER_ASC = "desc";
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
	public static final int STATUS_COLLECT = 3;
	
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
	private static final String MU_EMAIL = "muEmail";
	public static final int WEIXIN_SHARE = 0;
	public static final int WEIBO_SHARE = 1;
	public static final int MESSAGE_SHARE = 2;

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
	
	public static Object enrollMetting(Type type, Long id, String account,
			String name, String mobile, String company,
			String email, String weixin, Integer muType) {
		
		Map<String, Object> params = genParems();
		
		params.put(MU_ACCOUNT, account);
		
		params.put(METTING_ID, id);
		
		params.put(MU_NAME, name);
		
		params.put(MU_MOBILE, mobile);
		
		params.put(MU_COMPANY, company);
		
		params.put(MU_EMAIL, email);
		
		params.put("muType", muType);
		
		String json = NetUtils.getPostResult(params,ENROLL_URL);
		if (json == null) {
			return null;
		}
		return JsonUtils.parseJsonResult(type, json);
	}
	
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
	
	public static Object getAppVersion(String string) {
		HashMap<String, Object> params = genParems();
		params.put("platform", string);
		
		String json = NetUtils.getPostResult(params, CHECK_APP_VERSION);
		if (json == null) {
			return null;
		}
		int resultCode = JsonUtils.getResult(json);
		if (resultCode != 1) {
			return null;
		}
		return JsonUtils.parseJsonResult(new TypeToken<MTVersionResult>(){}.getType(), json);
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