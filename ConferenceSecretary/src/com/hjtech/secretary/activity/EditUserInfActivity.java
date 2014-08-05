package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditUserInfActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initUI(R.layout.activity_user_edit, R.string.title_activity_personal, R.string.title_activity_user_edit, R.string.complete);
	}
	
	@Override
	protected void initUI(int layoutId, int backId, int titleId, int rightId) {
		super.initUI(layoutId, backId, titleId, rightId);
		setbackButton();
		
		setRightClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = MTCommon.getContent((EditText) gv(R.id.edit_name_textview));
				if (name == null) {
					MTCommon.ShowToast("请输入名字");
					return;
				}
				String nickName = MTCommon.getContent((EditText) gv(R.id.edit_nickname_textview));
				if (nickName == null) {
					MTCommon.ShowToast("请输入昵称");
					return;
				}
				String newPassword = MTCommon.getContent((EditText) gv(R.id.edit_password_textview));
				if (newPassword == null) {
					MTCommon.ShowToast("密码");
					return;
				}
				String sex = MTCommon.getContent((EditText) gv(R.id.edit_gender));
				if (sex == null) {
					MTCommon.ShowToast("请输入性别");
					return;
				}
				int sex_int;
				if (sex.equals("男")) {
					sex_int = 0;
				}else{
					sex_int = 1;
				}
				String unit = MTCommon.getContent((EditText) gv(R.id.edit_company_textview));
				if (unit == null) {
					MTCommon.ShowToast("请输入公司");
					return;
				}
				String department = MTCommon.getContent((EditText) gv(R.id.edit_department_textview));
				if (department == null) {
					MTCommon.ShowToast("请输入部门");
					return;
				}
				String position = MTCommon.getContent((EditText) gv(R.id.edit_position_textview));
				if (position == null) {
					MTCommon.ShowToast("请输入职位");
					return;
				}
				String qq = MTCommon.getContent((EditText) gv(R.id.edit_qq_textview));
				if (qq == null) {
					MTCommon.ShowToast("请输入QQ");
					return;
				}
				String email = MTCommon.getContent((EditText) gv(R.id.edit_email_textview));
				if (email == null) {
					MTCommon.ShowToast("请输入E-mail");
					return;
				}
				String weixin = MTCommon.getContent((EditText) gv(R.id.edit_weixin_textview));
				if (weixin == null) {
					MTCommon.ShowToast("请输入微信");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

					@Override
					public void onPreExecute() {

					}

					@Override
					public void onPostExecute(Object result) {
						if (result == null) {
							MTCommon.ShowToast("修改失败");
							return;
						}
						MTSimpleResult sr = (MTSimpleResult) result;
						if (sr.getResult() == 1) {
							MTCommon.ShowToast("修改成功");
						}else{
							MTCommon.ShowToast("修改失败");
						}
					}
				}).modifyUserInf(MTUserManager.getUser().getMuAccount(), MTUserManager.getUser().getMuPassword(), newPassword,
						name, nickName, sex_int, unit, position, department, qq, email, weixin);
			}
		});
		
		gv(R.id.edit_password_button).setOnClickListener(new NewActivityListener(this, ModifyPasswordActivity.class));
	}
}
