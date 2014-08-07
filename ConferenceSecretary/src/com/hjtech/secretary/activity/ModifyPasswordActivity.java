package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyPasswordActivity extends BaseActivity {
	private EditText oldPassword;
	private EditText newPassword;
	private EditText conPassword;
	private Button modifyButton;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initUI(R.layout.activity_modify_password, R.string.title_activity_user_edit, R.string.title_activity_modify_password);
	}
	
	@Override
	protected void initUI(int layoutId, int titleId, int left) {
		super.initUI(layoutId, titleId, left);
		setbackButton();
		oldPassword = (EditText) gv(R.id.modify_password_old);
		newPassword = (EditText) gv(R.id.modify_password_new_textview);
		conPassword = (EditText) gv(R.id.modify_password_con_textview);
		modifyButton = (Button) gv(R.id.modify_button);
		
		modifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String old = MTCommon.getContent(oldPassword);
				if (old == null) {
					MTCommon.ShowToast("请输入原密码");
					return;
				}
				
				String newP = MTCommon.getContent(newPassword);
				if (newP == null) {
					MTCommon.ShowToast("请输入新密码");
					return;
				}
				
				String con = MTCommon.getContent(conPassword);
				if (con == null) {
					MTCommon.ShowToast("请再次输入新密码");
					return;
				}
				
				if (!con.equals(newP)) {
					MTCommon.ShowToast("两次密码输入不一致");
					return;
				}
				
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
					}
					
					@Override
					public void onPostExecute(Object result) {
						if (result == null) {
							MTCommon.ShowToast("修改密码失败");
							return;
						}
						MTSimpleResult sr = (MTSimpleResult) result;
						if (sr.getResult() == 1) {
							MTCommon.ShowToast("修改密码成功");
							finish();
						}else{
							MTCommon.ShowToast("修改密码失败");
							return;
						}
					}
				}).modifyPassword(MTUserManager.getUser().getMuAccount(), old, newP);;
			}
		});
	}
}
