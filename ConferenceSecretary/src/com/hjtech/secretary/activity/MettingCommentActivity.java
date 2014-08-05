package com.hjtech.secretary.activity;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.MettingCommentAdapter;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTCommentResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MettingCommentActivity extends BaseActivity {
	private long mettingId;
	private PullToRefreshListView listView;
	private MettingCommentAdapter adapter;
	private EditText comment;
	private Button submit;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		initUI(R.layout.activity_metting_comment, R.string.title_activity_metting_details, R.string.title_activity_comment);
	}
	
	@Override
	protected void initUI(int layoutId, int iconId, int titleId) {
		super.initUI(layoutId, iconId, titleId);
		setbackButton();
		listView = (PullToRefreshListView) gv(R.id.metting_comment_list);
		adapter = new MettingCommentAdapter(this);
		listView.setAdapter(adapter);
		
		comment = (EditText) gv(R.id.metting_comment_add_content);
		
		submit = (Button) gv(R.id.metting_comment_submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String commentContent = MTCommon.getContent(comment);
				if (commentContent == null || commentContent.trim().length() == 0) {
					MTCommon.ShowToast("请输入评论内容");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onPostExecute(Object result) {
						if (result == null) {
							MTCommon.ShowToast("评论失败");
							return;
						}
						MTSimpleResult sr = (MTSimpleResult) result;
						if (sr.getResult() != 1) {
							MTCommon.ShowToast("评论失败");
							return;
						}else{
							MTCommon.ShowToast("评论成功");
							comment.setText("");
							refreshList();
						}
					}

				}).addComment(mettingId, MTUserManager.getUser().getMuAccount(), commentContent);
			}
		});
	}
	
	private void refreshList() {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {

			}

			@Override
			public void onPostExecute(Object result) {
				MTCommentResult cr = (MTCommentResult) result;
				if (cr == null || cr.getResult() != 1) {
					MTCommon.ShowToast("评论获取失败");
					return;
				}
				adapter.setData(cr.getDetails());
			}
		}).getMettingComment(mettingId,0);
	}

	private void initData() {
		String idStr = (String) getIntent().getSerializableExtra("id");
		this.mettingId = Long.parseLong(idStr);
		refreshList();
	}

}
