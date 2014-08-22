package com.hjtech.secretary.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hugo.android.scanner.CaptureActivity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.activity.EnrollActivity;
import com.hjtech.secretary.activity.MainActivity;
import com.hjtech.secretary.activity.MettingCommentActivity;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTComment;
import com.hjtech.secretary.data.MTCommentResult;
import com.hjtech.secretary.data.MTMetting;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class MettingDetailsFragment extends BaseFragment {
	
	public static final int UNCOLLECT = 0;
	public static final int COLLECT = 1;
	private MTMetting metting;
	private List<MTComment> comments;
	
//	private LinearLayout relatedMettingLayout;
	
//	SwipeView mSwipeView;
//	int[] images = new int[]{R.drawable.home_picture_1,R.drawable.home_picture_1,R.drawable.home_picture_1,R.drawable.home_picture_1};
	private TextView collect;
private LinearLayout commentsLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initData();
		initActionBar(R.string.title_activity_metting_list, R.string.title_activity_metting_details, 0);
		return initUI(inflater);
	}
	
	private View initUI(LayoutInflater inflater) {
		setbackButton();
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_metting_detail, null);
		
		collect = (TextView) gv(R.id.detail_collect);
		changeCollectView();
		collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (metting.getIsCollect() == MTMetting.UNCOLLECT) {

					new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

						@Override
						public void onPreExecute() {
							MTCommon.ShowToast("正在收藏...");
						}

						@Override
						public void onPostExecute(Object result) {
							if (result != null && result instanceof Integer) {
								MTCommon.ShowToast("当前网络不可用,请检查网络链接");
								return;
							}	

							if (result == null) {
								MTCommon.ShowToast("收藏失败");
								return;
							}
							int resultCode = ((MTSimpleResult) result).getResult();
							switch (resultCode) {
							case 0:
								MTCommon.ShowToast("收藏失败");
								break;
							case 1:
								MTCommon.ShowToast("收藏成功");
								metting.setIsCollect(MTMetting.COLLECT);
								changeCollectView();
								MTUserManager.getUser().addCollect();
								break;
							case 2:
								MTCommon.ShowToast("参数错误，请检查提交参数");
								break;
							case 3:
								MTCommon.ShowToast("会员不存在");
								break;
							case 5:
								MTCommon.ShowToast("该会议已收藏过");
								metting.setIsCollect(MTMetting.COLLECT);
								changeCollectView();
							default:
								break;
							}
						}
					}).colloctMetting(metting.getMmId(), MTUserManager.getUser().getMuAccount(), COLLECT);
				}else{
					new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

						@Override
						public void onPreExecute() {
							MTCommon.ShowToast("正在取消收藏...");
						}

						@Override
						public void onPostExecute(Object result) {
							if (result == null) {
								MTCommon.ShowToast("取消收藏失败");
							}
							int resultCode = ((MTSimpleResult) result).getResult();
							switch (resultCode) {
							case 0:
								MTCommon.ShowToast("取消收藏失败");
								break;
							case 1:
								MTCommon.ShowToast("取消收藏成功");
								metting.setIsCollect(MTMetting.UNCOLLECT);
								changeCollectView();
								MTUserManager.getUser().minCollect();
								break;
							case 2:
								MTCommon.ShowToast("参数错误，请检查提交参数");
								break;
							case 3:
								MTCommon.ShowToast("会员不存在");
								break;
							case 5:
								MTCommon.ShowToast("该会议已收藏过");
								metting.setIsCollect(MTMetting.COLLECT);
								changeCollectView();
							default:
								break;
							}
						}
					}).colloctMetting(metting.getMmId(), MTUserManager.getUser().getMuAccount(), UNCOLLECT);
				}
			}
		});

//		relatedMettingLayout= (LinearLayout) gv(R.id.detail_relate_metting);
		
		Button enroll = (Button) gv(R.id.detail_enroll);
		if (metting.getIsEnroll() == MTMetting.UNENROLL && metting.getIsStarted() != MTMetting.END) {
			enroll.setOnClickListener(new NewActivityListener(getMainActivity(), EnrollActivity.class, "metting", metting));
		}else if (metting.getIsEnroll() == MTMetting.ENROLL) {
			enroll.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, CaptureActivity.class);
					MettingDetailsFragment.this.startActivityForResult(intent, MyMettingFragment.SIGNAL);
				}
			});
			enroll.setText("现在签到");
		}else{
			enroll.setTextColor(getResources().getColor(R.color.mt_text_3));
			enroll.setBackgroundResource(R.drawable.button_gray);
			enroll.setEnabled(false);
		}

		gv(R.id.detail_comment).setOnClickListener(new NewActivityListener(getMainActivity(), MettingCommentActivity.class, "id", metting.getMmId()));
		gv(R.id.detail_share).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("metting", metting);
				getMainActivity().switchFragment(MTFragmentFactory.INVITE, intent, true);
				
			}
		});
		((TextView)gv(R.id.detail_time)).setText(metting.getTimeForDetail());
		((TextView)gv(R.id.detail_address)).setText(String.format(activity.getResources().getString(R.string.metting_place),metting.getMmAddress()));
		((TextView)gv(R.id.detail_fee_res)).setText(String.format(activity.getResources().getString(R.string.metting_fee_and_res),metting.getMmFreeTypeStr(),metting.getMemberRttForDetail()));
		WebView wv = (WebView)gv(R.id.detail_detail);
		wv.loadDataWithBaseURL(null, metting.getMmDesp(), "text/html", "utf-8", null);
		((TextView)gv(R.id.detail_name)).setText(metting.getMmTitle());
		
		final ImageView imageView = (ImageView) gv(R.id.swipe_view);
		ImageLoader.getInstance().displayImage(metting.getMmLogo(), imageView, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				imageView.setImageResource(R.drawable.home_picture_1);
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage == null) {
					imageView.setImageResource(R.drawable.home_picture_1);
				}
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});
		
		commentsLayout = (LinearLayout) gv(R.id.metting_detail_comment_layout);
		
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
				if (cr == null || cr.getResult() != 1) {
					MTCommon.ShowToast("评论获取失败");
					return;
				}
				comments = cr.getDetails();
				if (comments == null || comments.size() == 0) {
					commentsLayout.setVisibility(View.GONE);
					gv(R.id.metting_detail_comment).setVisibility(View.GONE);
					gv(R.id.detail_metting_line).setVisibility(View.GONE);
					return;
				}else{
					commentsLayout.setVisibility(View.VISIBLE);
					gv(R.id.metting_detail_comment).setVisibility(View.VISIBLE);
					gv(R.id.detail_metting_line).setVisibility(View.VISIBLE);
				}
				for (int i = 0; i < 5 && i < comments.size(); ++i) {
					MTComment comment = comments.get(i);
					RelativeLayout commentLayout = (RelativeLayout) getBaseActivity().getLayoutInflater().inflate(R.layout.comment_text_view, commentsLayout, false);
					((TextView)commentLayout.findViewById(R.id.content)).setText(comment.getMcContent());
					((TextView)commentLayout.findViewById(R.id.time)).setText(comment.getMcAddtime());
					if (i % 2 == 1) {
						commentLayout.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.mt_login_bg));
					}
					commentLayout.setOnClickListener(new NewActivityListener(getBaseActivity(), MettingCommentActivity.class, "id", metting.getMmId()));
					commentsLayout.addView(commentLayout);
				}
			}
		}).getMettingComment(metting.getMmId(),1);
		
		
		//init swipe view
//		PageControl mPageControl = (PageControl) gv(R.id.page_control);
//		mSwipeView = (SwipeView) gv(R.id.swipe_view);
//
//		for(int i=0; i<4;i++) {
//			mSwipeView.addView(new FrameLayout(getBaseActivity()));
//		}
//
//		ImageView i0 = new ImageView(getBaseActivity());
//		ImageView i1 = new ImageView(getBaseActivity());
//		
//		i0.setImageResource(images[0]);
//		i1.setImageResource(images[1]);
//		i0.setScaleType(ScaleType.FIT_XY);
//		i1.setScaleType(ScaleType.FIT_XY);
//
//		((FrameLayout) mSwipeView.getChildContainer().getChildAt(0)).addView(i0);
//		((FrameLayout) mSwipeView.getChildContainer().getChildAt(1)).addView(i1);
//
//		SwipeImageLoader mSwipeImageLoader = new SwipeImageLoader();
//        
//        mSwipeView.setOnPageChangedListener(mSwipeImageLoader);
//        
//        mSwipeView.setPageControl(mPageControl);
		return rootView;
	}
	
	@Override
	public void onResume() {
		
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
				if (cr == null || cr.getResult() != 1) {
					MTCommon.ShowToast("评论获取失败");
					return;
				}
				comments = cr.getDetails();
				if (comments == null || comments.size() == 0) {
					commentsLayout.setVisibility(View.GONE);
					gv(R.id.metting_detail_comment).setVisibility(View.GONE);
					gv(R.id.detail_metting_line).setVisibility(View.GONE);
					return;
				}else{
					commentsLayout.setVisibility(View.VISIBLE);
					gv(R.id.metting_detail_comment).setVisibility(View.VISIBLE);
					gv(R.id.detail_metting_line).setVisibility(View.VISIBLE);
				}
				for (int i = 0; i < 5 && i < comments.size(); ++i) {
					MTComment comment = comments.get(i);
					RelativeLayout commentLayout = (RelativeLayout) getBaseActivity().getLayoutInflater().inflate(R.layout.comment_text_view, commentsLayout, false);
					((TextView)commentLayout.findViewById(R.id.content)).setText(comment.getMcContent());
					((TextView)commentLayout.findViewById(R.id.time)).setText(comment.getMcAddtime());
					if (i % 2 == 1) {
						commentLayout.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.mt_login_bg));
					}
					commentLayout.setOnClickListener(new NewActivityListener(getBaseActivity(), MettingCommentActivity.class, "id", metting.getMmId()));
					commentsLayout.addView(commentLayout);
				}
			}
		}).getMettingComment(metting.getMmId(),1);
		super.onResume();
	}

	private void changeCollectView() {
		if (metting.getIsCollect() == MTMetting.UNCOLLECT) {
			collect.setText("点击收藏");
			collect.setTextColor(Color.parseColor("#666666"));;
			collect.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.metting_detail_uncollect), null, null);
		}else{
			collect.setText("已收藏");
			collect.setTextColor(getResources().getColor(R.color.mt_blue));
			collect.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.metting_detail_collect), null, null);
		}
	}

	private void initData() {
		if (getIntent() != null) {
			metting = (MTMetting) getIntent().getSerializableExtra("metting");
		}else{
			metting = new MTMetting();
		}
		
	}
	
	public MainActivity getMainActivity(){
		return (MainActivity) activity;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == MyMettingFragment.SIGNAL) {
			String result = data.getStringExtra("result");
			String idStr = null; 
			long id;
			try {
				idStr = Encryption.decodeBase64(result);
				int position = idStr.indexOf("sign:");
				if (position == -1) {
					MTCommon.ShowToast("请扫描正确的二维码");
					return;
				}
				id = Long.valueOf(idStr.substring(position + 5).trim());
			} catch (Exception e) {
				e.printStackTrace();
				MTCommon.ShowToast("请扫描正确的二维码");
				return;
			}

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

					if (result == null) {
						MTCommon.ShowToast("签到失败");
						return;
					}
					MTSimpleResult sr = (MTSimpleResult) result;
					switch (sr.getResult()) {
					case 1:
						MTCommon.ShowToast("签到成功");
						break;
					case 4:
						MTCommon.ShowToast("末报名,不能签到");
						break;
					default:
						MTCommon.ShowToast("签到失败");
						break;
					}

				}
			}).singIn(id, MTUserManager.getUser().getMuAccount());
		};
	}

	
//	@Override
//	public void onClick(View v) {
//		Intent intent = new Intent();
//		long id = (Long) v.getTag();
//		intent.putExtra("id", id);
//		getMainActivity().switchFragment(MTFragmentFactory.METTING_DETAILS, intent, true);
//	}
	
	
	
//	private class SwipeImageLoader implements OnPageChangedListener {
//
//		public void onPageChanged(int oldPage, int newPage) {
//			//going forwards
//			if(newPage > oldPage) {
//				//if at the end, don't load one page after the end
//				if(newPage != (mSwipeView.getPageCount() - 1)) {
//					ImageView v = new ImageView(getBaseActivity());
//					v.setImageResource(images[newPage+1]);
//					v.setScaleType(ScaleType.FIT_XY);
//					((FrameLayout) mSwipeView.getChildContainer().getChildAt(newPage+1)).addView(v);
//				}
//				//if at the beginning, don't destroy one before the beginning
//				if(oldPage != 0) {
//					((FrameLayout) mSwipeView.getChildContainer().getChildAt(oldPage-1)).removeAllViews();
//				}
//			} else {
//				//if at the beginning, don't load one before the beginning
//				if(newPage != 0) {
//					ImageView v = new ImageView(getBaseActivity());
//					v.setImageResource(images[newPage-1]);
//					v.setScaleType(ScaleType.FIT_XY);
//					((FrameLayout) mSwipeView.getChildContainer().getChildAt(newPage-1)).addView(v);
//				}
//				//if at the end, don't destroy one page after the end
//				if(oldPage != (mSwipeView.getPageCount()-1)) {
//					((FrameLayout) mSwipeView.getChildContainer().getChildAt(oldPage+1)).removeAllViews();
//				}
//			}
//
//		}
//
//	}
}
