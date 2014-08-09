package com.hjtech.secretary.utils;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;

import Decoder.BASE64Encoder;

public class Encryption {

	public static String getKey(){
		return getRandomString(5);
	}

	public static String getCode(String key){
		String temp = key +  "METTING-HJTECH";
		return encode(temp);
	}

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

	private static String encode(String s)  {
		s = md5(s).toUpperCase();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(s.getBytes());
	}

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

	private static String hex(byte[] array)  {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

}