package com.hjtech.secretary.utils;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;

import android.util.Base64;
import Decoder.BASE64Encoder;

/**
 * The Class Encryption.
 * 
 * @author albuscrow
 */
public class Encryption {

	/**
	 * Gets the key.
	 * 
	 * @return the key
	 */
	public static String getKey(){
		return getRandomString(5);
	}

	/**
	 * Gets the code.
	 * 
	 * @param key
	 *            the key
	 * @return the code
	 */
	public static String getCode(String key){
		String temp = key +  "METTING-HJTECH";
		return encode(temp);
	}

	/**
	 * Gets the random string.
	 * 
	 * @param length
	 *            the length
	 * @return the random string
	 */
	private static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
		Random random = new Random();
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < length; i++) {   
			int number = random.nextInt(base.length());   
			sb.append(base.charAt(number));   
		}   
		return sb.toString();   
	}  	

	/**
	 * Encode.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	private static String encode(String s)  {
		s = md5(s).toUpperCase();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(s.getBytes());
	}

	/**
	 * Md5.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	public static String md5(String str) {
		String md5Str = "";  
		try {  
			MessageDigest md = MessageDigest.getInstance("MD5");  
			// 使用指定byte[]更新摘要
			md.update(str.getBytes("utf8"));  
			// 完成计算，返回结果数组
			byte[] b = md.digest();  
			md5Str = hex(b);
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return md5Str;
	}

	/**
	 * Hex.
	 * 
	 * @param array
	 *            the array
	 * @return the string
	 */
	private static String hex(byte[] array)  {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	/**
	 * Decode base64.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String decodeBase64(String input) {
		return new String(Base64.decode(input, Base64.DEFAULT));
	}

}