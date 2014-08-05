package com.hjtech.secretary.activity;

import cn.hugo.android.scanner.CaptureActivity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.adapter.PersonalCenterAdatper;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.fragment.MTFragmentFactory;
import com.hjtech.secretary.utils.MTCommon;

import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
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
	}

	@Override
	protected void initUI(int layoutId, int textId) {
		super.initUI(layoutId, textId);
		
        //init main menu
        GridView gridView = (GridView) findViewById(R.id.shape_menu);
		gridView.setAdapter(new PersonalCenterAdatper(this));
		gridView.setOnItemClickListener(this);
		
		newsFlipper = (ViewFlipper) gv(R.id.home_news);
		for (int i = 0; i < 3; ++i) {
			TextView tx = (TextView) getLayoutInflater().inflate(R.layout.news_text_view, newsFlipper, false);
			tx.setText("nihao");
			newsFlipper.addView(tx);
		}
		
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
			//TODO temp
			result = "1";
			long id = Long.parseLong(result);
			new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

				@Override
				public void onPreExecute() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPostExecute(Object result) {
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
}
