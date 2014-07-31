package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.utils.MTCommon;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	
	private View waitBar;
	private TextView leftText;
	private TextView titleTextView;
	private TextView rightTextView;
	
	protected void initUI(int layoutId){
		initUI(layoutId, 0, 0, 0);
	}
	

	protected void initUI(int layoutId, int leftContentId, int titleId) {
		initUI(layoutId, leftContentId, titleId, 0);
	}
	
	protected void initUI(int layoutId, int titleId) {
		initUI(layoutId, 0, titleId, 0);
	}
	protected void initUI(int layoutId, int backContentId, int titleContentId, int rightTextViewContentId) {
		// init title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		setContentView(layoutId);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar);
		
		leftText = (TextView)gv(R.id.back_text);
		if (backContentId != 0) {
			showActionLeft();
			MTCommon.setContent(leftText, backContentId);
		}else{
			hideActionLeft();
		}
		
		titleTextView = (TextView)gv(R.id.titlebar_text);
		
		if (titleContentId != 0) {
			MTCommon.setContent(titleTextView, titleContentId);
		}
		
		rightTextView = (TextView)gv(R.id.rightView);
		if (rightTextViewContentId != 0) {
			showActionRight();
			MTCommon.setContent(rightTextView, rightTextViewContentId);
		}else{
			hideActionRight();
		}
		
		waitBar = findViewById(R.id.common_wait);
	}
	
	
	public void changeTitle(int StringId){
		MTCommon.setContent(titleTextView, StringId);
	}
	
	public void changeActionLeft(int StringId){
		showActionLeft();
		MTCommon.setContent(leftText, StringId);
	}
	
	

	public void changeActionRight(int StringId){
		showActionRight();
		MTCommon.setContent(leftText, StringId);
	}
	
	protected void hideActionRight() {
		rightTextView.setVisibility(View.GONE);
	}
	
	protected void showActionRight() {
		rightTextView.setVisibility(View.VISIBLE);
	}

	protected void hideActionLeft() {
		leftText.setVisibility(View.GONE);
	}

	protected void showActionLeft() {
		leftText.setVisibility(View.VISIBLE);
	}
	
	protected void setbackButton(){
		leftText.setOnClickListener(new OnClickListener() {
			
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

	public void setbackButtonForFragment() {
		getSupportFragmentManager().popBackStack();
	}




}