package com.hjtech.secretary.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.MettingCommentAdapter;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTComment;
import com.hjtech.secretary.data.MTCommentResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MettingCommentActivity extends BaseActivity {
	private long mettingId;
	private PullToRefreshListView listView;
	private MettingCommentAdapter adapter;
	private EditText comment;
	private TextView submit;
	
	private int pageNum = 1;
	private int totalNumber = 0;

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
		
		listView.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				refreshList(true);
			}
		});
		
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				getMore();
			}

		});
		
		comment = (EditText) gv(R.id.metting_comment_add_content);
		
		submit = (TextView) gv(R.id.metting_comment_submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String commentContent = MTCommon.getContent(comment);
				if (commentContent == null || commentContent.trim().length() == 0) {
					MTCommon.ShowToast("请输入评论内容");
					return;
				}
				new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
					
					@Override
					public void onPreExecute() {
						MTCommon.ShowToast("正在发表您的评论");
						submit.setEnabled(false);
					}
					
					@Override
					public void onPostExecute(Object result) {
						if (result != null && result instanceof Integer) {
					MTCommon.ShowToast("当前网络不可用,请检查网络链接");
					return;
				}	

						if (result == null) {
							MTCommon.ShowToast("评论失败");
							submit.setEnabled(true);
							return;
						}
						MTSimpleResult sr = (MTSimpleResult) result;
						if (sr.getResult() != 1) {
							MTCommon.ShowToast("评论失败");
							submit.setEnabled(true);
							return;
						}else{
							MTCommon.ShowToast("评论成功");
							comment.setText("");
							addComment(commentContent);
							refreshList(false);
						}
					}

			

				}).addComment(mettingId, MTUserManager.getUser().getMuAccount(), commentContent);
			}
		});
		refreshList(true);
	}
	
	private void addComment(String commentContent) {
		MTComment comment = new MTComment();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINESE);
		comment.setMcAddtime(sdf.format(new Date()));
		comment.setMcContent(commentContent);
		comment.setMcId(0);
		comment.setMcUserId(MTUserManager.getUser().getMuId());
		comment.setMuName(MTUserManager.getUser().getMuName());
		comment.setMuPhoto(MTUserManager.getUser().getMuPhoto());
		adapter.addData(comment);
	}
	
	private void refreshList(final boolean needWaitBar) {
		pageNum = 1;
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				if (needWaitBar) {
					showWaitBar();
				}
			}

			@Override
			public void onPostExecute(Object result) {
				if (needWaitBar) {
					hideWaitBar();
				}
				if (result != null && result instanceof Integer) {
					MTCommon.ShowToast("当前网络不可用,请检查网络链接");
					return;
				}

				MTCommentResult cr = (MTCommentResult) result;
				totalNumber = cr.getTotal();
				if (cr == null || cr.getResult() != 1) {
					MTCommon.ShowToast("评论获取失败");
					return;
				}
				adapter.setData(cr.getDetails());
				submit.setEnabled(true);
			}
		}).getMettingComment(mettingId,0);
	}

	private void initData() {
		this.mettingId = (Long) getIntent().getSerializableExtra("id");
	}
	
	private void getMore() {
		if (pageNum * 15 < totalNumber) {
			new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

				@Override
				public void onPreExecute() {

				}

				@Override
				public void onPostExecute(Object result) {
					if (result != null && result instanceof Integer) {
						MTCommon.ShowToast("当前网络不可用,请检查网络链接");
						return;
					}
					MTCommentResult cr = (MTCommentResult) result;
					totalNumber = cr.getTotal();
					if (cr == null || cr.getResult() != 1) {
						MTCommon.ShowToast("评论获取失败");
						return;
					}
					adapter.appendData(cr.getDetails());

				}
			}).getMettingComment(mettingId, pageNum++);
		}
	}

}
