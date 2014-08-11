package com.hjtech.secretary.activity;

import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.PersonalCenterAdatper;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTMessage;
import com.hjtech.secretary.data.MTMessageListResult;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.fragment.MTFragmentFactory;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.Encryption;
import com.hjtech.secretary.utils.MTCommon;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HomeActivity extends BaseActivity implements OnItemClickListener {
	private long lExitTime = 0;

	private static final int SIGNIN = 10;
	private ViewFlipper newsFlipper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI(R.layout.activity_home, R.string.title_activity_home);
		initData();
	}

	@Override
	protected void initUI(int layoutId, int textId) {
		super.initUI(layoutId, textId);
		
        //init main menu
        GridView gridView = (GridView) findViewById(R.id.shape_menu);
		gridView.setAdapter(new PersonalCenterAdatper(this));
		gridView.setOnItemClickListener(this);
		
		newsFlipper = (ViewFlipper) gv(R.id.home_news);
		TextView tx = (TextView) getLayoutInflater().inflate(R.layout.news_text_view, newsFlipper, false);
		tx.setText("");
		newsFlipper.addView(tx);
		
		gv(R.id.home_phone).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("tel:400-000-000");    
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);       
				startActivity(intent);
			}
		});
		
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - lExitTime) > 2000) {
				MTCommon.ShowToast(getText(R.string.home_exit_warning));
				lExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = null;
		switch (arg2) {
		case 0:
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("UIType", MTFragmentFactory.METTING_LIST);
			break;
		case 1:
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("UIType", MTFragmentFactory.MY_METTING);
			break;
		case 2:
			intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, SIGNIN);
			return;
		case 3:
			intent = new Intent(this, PersonalActivity.class);
			break;
		case 4:
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("UIType", MTFragmentFactory.MESSAGE);
			break;
		case 5:
			intent = new Intent(this, AboutActivity.class);
			break;

		default:
			break;
		}
		this.startActivity(intent);

	}	
	
	
	
	protected void onActivityResult(int request, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && request == SIGNIN) {
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
					// TODO Auto-generated method stub

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
		}
	}
	private void initData() {
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
					MTCommon.ShowToast("获取消息失败");
					return;
				}
				if (result instanceof MTSimpleResult) {
					MTCommon.ShowToast("获取消息失败");
					return;
				}else{
					List<MTMessage> data = (List<MTMessage>) ((MTMessageListResult)result).getDetails();
					newsFlipper.removeAllViews();
					for (int i = 0; i < data.size(); ++i) {
						TextView tx = (TextView) getLayoutInflater().inflate(R.layout.news_text_view, newsFlipper, false);
						tx.setText(data.get(i).getMmTitle());
						tx.setOnClickListener(new NewActivityListener(HomeActivity.this, MainActivity.class, "UIType", MTFragmentFactory.MESSAGE));
						newsFlipper.addView(tx);
					}				}
			}
		}).getMessage(MTUserManager.getUser().getMuAccount(), 0);
	}
}
