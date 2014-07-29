package com.hjtech.secretary.fragment;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.RegisterActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.data.MTUserResult;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class RegisterFormFragment extends Fragment {
		
	private EditText nameTextView;
	private EditText passwordView;
	private EditText passwordConView;
	private EditText emailView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register_form, null);
		nameTextView = (EditText)view.findViewById(R.id.register_nickname_edittext);
		passwordView = (EditText)view.findViewById(R.id.register_password_editext);
		passwordConView = (EditText)view.findViewById(R.id.register_password_confirm_editext);
		emailView = (EditText)view.findViewById(R.id.register_email_edittext);
		view.findViewById(R.id.register_complete_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String nickName = MTCommon.getContent(nameTextView);
				if(nickName == null){
					MTCommon.ShowToast("请输入昵称");
					return;
				}
				
				final String password = MTCommon.getContent(passwordView);
				if(password == null){
					MTCommon.ShowToast("请输入密码");
					return;
				}
				
				String passwordConfirm = MTCommon.getContent(passwordConView);
				if(passwordConfirm == null){
					MTCommon.ShowToast("请再次输入密码");
					return;
				}
				if (!password.equals(passwordConfirm)) {
					MTCommon.ShowToast("密码不一致");
					return;
				}
				final String email = MTCommon.getContent(emailView);
				if (email == null) {
					MTCommon.ShowToast("请输入邮箱");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
					}
					
					@Override
					public void onPostExecute(Object result) {
						MTUserResult ur = (MTUserResult) result;
						if (ur.getDetails() == null) {
							MTCommon.ShowToast("注册失败");
							MTCommon.ShowToast(String.valueOf(ur.getResult()));
							return;
						}
						MTCommon.ShowToast("注册成功");
						MTUserManager.save((MTUser)ur.getDetails());
						((RegisterActivity)getActivity()).goHomeActivity();
					}
				}).register(((RegisterActivity)getActivity()).getPhone(),"albuscrow",nickName,password);
			}
		});
		return view;
	}
}
