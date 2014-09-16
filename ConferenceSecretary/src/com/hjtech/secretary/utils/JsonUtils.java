package com.hjtech.secretary.utils;

import java.lang.reflect.Type;

import android.util.Log;

import com.google.gson.Gson;

/**
 * The Class JsonUtils.
 * 
 * @author albuscrow
 */
public class JsonUtils {
	
	/** The Constant TAG. */
	private static final String TAG = "JsonUtils";
	
	/** The Constant PARSE_ERROR. */
	private static final String PARSE_ERROR = "json prase error";
	
	/**
	 * Parses the json result.
	 * 
	 * @param type
	 *            the type
	 * @param json
	 *            the json
	 * @return the object
	 */
	public static Object parseJsonResult(Type type,String json){
		Gson gson = new Gson();
		Object result = null;
		try {
			result = gson.fromJson(json, type);
		} catch (Exception e) {
			Log.e(TAG, PARSE_ERROR);
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	/**
	 * Gets the result.
	 * 
	 * @param json
	 *            the json
	 * @return the result
	 */
	public static int getResult(String json){
		String temp = new String(json);
		int position = temp.indexOf("\"result\"");
		temp = temp.substring(position);
		int pos1 = temp.indexOf(':');
		int pos2 = temp.indexOf(',');
		String res = temp.substring(pos1+1, pos2).trim(); 
		return Integer.parseInt(res);
	}
}
