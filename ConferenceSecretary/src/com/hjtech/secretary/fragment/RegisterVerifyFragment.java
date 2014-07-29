package com.hjtech.secretary.fragment;


import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.LoginActivity;
import com.hjtech.secretary.activity.RegisterActivity;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.utils.MTCommon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterVerifyFragment extends Fragment{
	private EditText phoneView;
	private Button verifyButton;
	private View registerButton;
	private EditText vcodeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register_verify, null);
		phoneView = (EditText) view.findViewById(R.id.register_tel_edittext);
		vcodeView = (EditText) view.findViewById(R.id.register_verify_edittext);
		verifyButton = (Button) view.findViewById(R.id.register_verifycode_button);
		verifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String phone = MTCommon.getContent(phoneView);
				if (phone == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (phone.length() != 11 && phone.length() != 13) {
					MTCommon.ShowToast("请输入正确的号码");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						changeButton();
					}
					
					@Override
					public void onPostExecute(Object result) {
						if (result == null) {
							MTCommon.ShowToast("网络错误！");
							return;
						}
						int resultCode = ((MTSimpleResult) result).getResult();
						switch (resultCode) {
						case -1:
							MTCommon.ShowToast("验证不通过，非法用户");
							break;
						case 0:
							MTCommon.ShowToast("获取失败，服务器内部错误");
							break;
						case 1:
							MTCommon.ShowToast("已发送验证码到您手机，请注意查收");
							break;
						case 2:
							MTCommon.ShowToast("提交参数错误");
							break;
						case 3:
							MTCommon.ShowToast("该号码已经被注册，请直接用该号码登陆");
							Intent intent = new Intent(getActivity(), LoginActivity.class);
							intent.putExtra("account", phone);
							getActivity().startActivity(intent);
							break;

						default:
							break;
						}
					}

					
				}).getVerifyCode(phone);
			}
		});
		
		registerButton = view.findViewById(R.id.next_step_button);
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String phone = MTCommon.getContent(phoneView);
				if (phone == null) {
					MTCommon.ShowToast("请输入手机号");
					return;
				}
				if (phone.length() != 11 && phone.length() != 13) {
					MTCommon.ShowToast("请输入正确的号码");
					return;
				}		
				String vcode = MTCommon.getContent(vcodeView);
				if (vcode == null) {
					MTCommon.ShowToast("请输入验证码");
					return;
				}
				
				if (vcode.length() != 5) {
					MTCommon.ShowToast("请输入正确的验证码");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onPostExecute(Object result) {
						
						int resultCode = (Integer) result;
						switch (resultCode) {
						case -1:
							Toast.makeText(getActivity(), "验证不通过，非法用户", Toast.LENGTH_SHORT).show();
							break;
						case 0:
							Toast.makeText(getActivity(), "获取失败，服务器内部错误", Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Toast.makeText(getActivity(), "验证成功", Toast.LENGTH_SHORT).show();
							((RegisterActivity)getActivity()).next(phone);
							break;
						case 2:
							Toast.makeText(getActivity(), "提交参数错误", Toast.LENGTH_SHORT).show();
							break;
						case 3:
							Toast.makeText(getActivity(), "验证码失效，请重新获取", Toast.LENGTH_SHORT).show();
							break;
						case 4:
							Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
							break;
						default:
							break;
						}
					}
				}).Validation(phone, vcode);
			}
		});
		return view;
	}
	private void changeButton() {
		verifyButton.setEnabled(false);
		new Thread(){
			
			private int time;

			public void run() {
				FragmentActivity activity = getActivity();
				
				time = 5;
				while (true) {
					activity = getActivity();
					if (activity != null) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								verifyButton.setText("请等候" + time +"秒...");
							}
						});
						if (--time == 0) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				activity = getActivity();
				if (activity != null) {
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							verifyButton.setEnabled(true);
							verifyButton.setText("获取验证码");
						}
					});

				}
			}
		}.start();
	}
	
}
