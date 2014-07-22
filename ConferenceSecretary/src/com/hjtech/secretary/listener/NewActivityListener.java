package com.hjtech.secretary.listener;

import com.hjtech.secretary.activity.RegisterActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class NewActivityListener implements OnClickListener {


	private Intent intent;
	private Context context;

	public NewActivityListener(Context context, Class<?> clz, String...objects){
		this.context = context;
		this.intent = new Intent(context, clz);
		for (int i = 0; i < objects.length; i+=2) {
			intent.putExtra(objects[i], objects[i + 1]);
		}
	}
	
	@Override
	public void onClick(View v) {
		context.startActivity(intent);
	}

}
