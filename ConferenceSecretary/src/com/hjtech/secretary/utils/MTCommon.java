package com.hjtech.secretary.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class MTCommon {
	private static Context context;
	
	public static void init(Context context){
		MTCommon.context = context;
	}
	
	public static String getContent(EditText et){
		String content = et.getText().toString();
		if (content == null || content.length() == 0) {
			return null;
		}
		return content;
	}
	
	public static void ShowToast(String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
}
