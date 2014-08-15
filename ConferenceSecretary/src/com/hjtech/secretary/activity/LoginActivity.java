package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.data.MTUserResult;
import com.hjtech.secretary.fragment.MTFragmentFactory;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.MTCommon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class LoginActivity extends BaseActivity {
	protected static final int FORGET_PASSWORD = 0;
	private EditText phoneNum;
	private EditText passWord;
	private CheckBox remPwd;
	private View loginButton;
	private MTUser user;
	private String from;
	private boolean needGuide;
	private GestureDetector gesture;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		if (!needGuide) {
			initUIWithoutActionBar(R.layout.activity_login);
		}else{
			initUIWithoutActionBar(R.layout.activity_login_first);
		}
	}
	
	private void initData(){
		from = getIntent().getStringExtra("from");
		needGuide = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("needGuide", true);
//		needGuide = true;
	}
	
	@Override
	protected void initUIWithoutActionBar(int layoutId) {
		super.initUIWithoutActionBar(layoutId);
		if (!needGuide) {
			initLoginView();
		}else{
			final ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.root_view);  
			gesture = new GestureDetector(this, new OnGestureListener() {
				private int account = 0;
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return false;
				}
				
				@Override
				public void onShowPress(MotionEvent e) {
					
				}
				
				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
						float distanceY) {
					return false;
				}
				
				@Override
				public void onLongPress(MotionEvent e) {
					
				}
				
				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						float velocityY) {
					if(e1.getX() - e2.getX() > 120) {//向右滑动  
						if (account < 3) {
							++account;
							viewFlipper.setInAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.push_left_in));  
							viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.push_left_out));  
							viewFlipper.showNext();
							if (account == 3) {
								initLoginView();
							}
						}
					}else if(e2.getX() - e1.getX() > 120){//向左滑动  
						if (account >= 0) {
							--account;
							viewFlipper.setInAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.push_right_in));  
							viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.push_right_out));  
							viewFlipper.showPrevious();  
						}
					}
					return false; 				

				}
				
				@Override
				public boolean onDown(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}
			});  

			View view1 = getLayoutInflater().inflate(R.layout.guidepage_animation, viewFlipper, false);
			viewFlipper.addView(view1);
			startAnimation(view1);
			RelativeLayout view2 = (RelativeLayout) getLayoutInflater().inflate(R.layout.guidepage2, viewFlipper, false);
			viewFlipper.addView(view2);
			
			RelativeLayout view3 = (RelativeLayout) getLayoutInflater().inflate(R.layout.guidepage3, viewFlipper, false);
			viewFlipper.addView(view3);
			
			View view4 = getLayoutInflater().inflate(R.layout.activity_login, viewFlipper, false);
			viewFlipper.addView(view4);
		
			PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("needGuide", false).commit();
		}
	}

	private void startAnimation(View view1) {
		View t1 = gv(R.id.t1);
		View t2 = gv(R.id.t2);
		View t3 = gv(R.id.t3);
		View t4 = gv(R.id.t4);
		View t5 = gv(R.id.t5);
		
		Animation a1 = AnimationUtils.loadAnimation(this, R.anim.guide_show);
		a1.setStartOffset(400);
		t1.startAnimation(a1);
		
		Animation a2 = AnimationUtils.loadAnimation(this, R.anim.guide_show);
		a2.setStartOffset(700);
		t2.startAnimation(a2);
		
		Animation a3 = AnimationUtils.loadAnimation(this, R.anim.guide_show);
		a3.setStartOffset(0);
		t3.startAnimation(a3);
		
		Animation a4 = AnimationUtils.loadAnimation(this, R.anim.guide_show);
		a4.setStartOffset(900);
		t4.startAnimation(a4);
		
		Animation a5 = AnimationUtils.loadAnimation(this, R.anim.guide_show);
		a5.setStartOffset(950);
		t5.startAnimation(a5);
	}

	private void initLoginView() {
		gv(R.id.login_register_text).setOnClickListener(new NewActivityListener(this, RegisterActivity.class));
		phoneNum = ((EditText)gv(R.id.login_phonenum));
		passWord = ((EditText)gv(R.id.login_password));
		remPwd = ((CheckBox)gv(R.id.login_remember_password));
		user = MTUserManager.getUser();

		if (from == null) {
			SharedPreferences preferences = getPreferences(LoginActivity.MODE_PRIVATE);

			if (user != null) {
				phoneNum.setText(user.getMuAccount());
				MTCommon.moveSelectionToLast(passWord);
			}

			if (preferences.getBoolean("rempwd", false)) {
				passWord.setText(preferences.getString("pwd", ""));
				MTCommon.moveSelectionToLast(passWord);
				remPwd.setChecked(true);
			}
		}
		loginButton = gv(R.id.login_button);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String account = MTCommon.getContent(phoneNum);
				if (account == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (account.length() != 11 && account.length() != 13) {
					MTCommon.ShowToast("请输入正确的号码");
					return;
				}

				final String pwd = MTCommon.getContent(passWord);
				if (pwd == null) {
					MTCommon.ShowToast("请输入密码");
					return;
				}

				String pwdMd5 = Encryption.md5(pwd);

				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

					@Override
					public void onPreExecute() {
						showWaitBar();
						loginButton.setEnabled(false);
					}

					@Override
					public void onPostExecute(Object result) {
						hideWaitBar();
						loginButton.setEnabled(true);

						if (result != null && result instanceof Integer) {
							MTCommon.ShowToast("当前网络不可用,请检查网络链接");
							return;
						}

						if (result == null) {
							MTCommon.ShowToast("网络出错啦");
							return;
						}

						if (result instanceof MTSimpleResult) {
							MTCommon.ShowToast("用户名或密码错误");
							return;
						}

						if (result instanceof MTUserResult) {
							MTCommon.ShowToast("登陆成功");
							MTUserResult ur = (MTUserResult) result;
							ur.init();

							//save user inf;
							MTUserManager.save((MTUser)ur.getDetails());
							Editor edit = LoginActivity.this.getPreferences(LoginActivity.MODE_PRIVATE).edit();
							if(remPwd.isChecked()){
								edit.putBoolean("rempwd", true);
								edit.putString("pwd", pwd);
							}else{
								edit.putBoolean("rempwd", false);
								edit.putString("pwd", "");
							}
							edit.commit();

							MTFragmentFactory.clear();

							//init fragment
							//to the home activity;
							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							LoginActivity.this.startActivity(intent);
							LoginActivity.this.finish();
						}

					}
				}).login(account,pwdMd5);
			}
		});

		gv(R.id.forget_password).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
				LoginActivity.this.startActivityForResult(intent, FORGET_PASSWORD);
			}
		});
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == FORGET_PASSWORD && arg1 == Activity.RESULT_OK) {
			remPwd.setChecked(false);
			Editor edit = getPreferences(Activity.MODE_PRIVATE).edit();
			edit.putBoolean("rempwd", false);
			edit.putString("pwd", "");
			edit.commit();
			String phone = arg2.getStringExtra("phone");
			phoneNum.setText(phone);
			MTCommon.moveSelectionToLast(phoneNum);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gesture.onTouchEvent(event);
	}
}
