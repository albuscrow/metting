package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.utils.MTCommon;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	
	private ImageView titleIcon;
	private View waitBar;

	protected void initUI(int layoutId, int iconId, int titleId) {
		// init title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		setContentView(layoutId);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar);
		if (iconId != -1) {
			titleIcon = (ImageView)findViewById(R.id.titlebar_icon);
			titleIcon.setImageResource(iconId);
		}
		((TextView)findViewById(R.id.titlebar_text)).setText(getResources().getString(titleId));;
		waitBar = findViewById(R.id.common_wait);
	}
	
	protected void setbackButton(){
		titleIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});
	}
	
	protected View gv(int id){
		return findViewById(id);
	}
	
	protected void showWaitBar(){
		waitBar.setVisibility(View.VISIBLE);
	}
	
	protected void hideWaitBar(){
		waitBar.setVisibility(View.GONE);
	}
	

}