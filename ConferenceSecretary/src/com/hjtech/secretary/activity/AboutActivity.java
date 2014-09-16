package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * The Class AboutActivity.
 * 关于界面
 * 
 * @author albuscrow
 */
public class AboutActivity extends BaseActivity {
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_about, R.string.homepage, R.string.home_about);
	}

	/* (non-Javadoc)
	 * @see com.hjtech.secretary.activity.BaseActivity#initUI(int, int, int)
	 */
	protected void initUI(int layoutId, int left,int titleId) {
		super.initUI(layoutId,R.string.homepage, titleId);
		setBackButton();

		gv(R.id.about_phone_num).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//＊＊＊＊点击电话号码跳转到播号界面时默认的电话号码＊＊＊＊＊
				Uri uri = Uri.parse("tel:4006775050");    
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);       
				startActivity(intent);
				
			}
		});
	}

}
