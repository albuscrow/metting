package com.hjtech.secretary.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
	
	public static void setContent(EditText et, int StringId){
		et.setText(context.getResources().getString(StringId));
	}
	
	public static void setContent(TextView et, int StringId){
		et.setText(context.getResources().getString(StringId));
	}
	
	public static void ShowToast(String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public static void moveSelectionToLast(EditText passWord) {
		passWord.requestFocus();
		passWord.setSelection(passWord.getText().toString().length());
	}
}
