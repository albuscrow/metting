package com.hjtech.secretary.activity;

import uk.co.jasonfry.android.tools.ui.PageControl;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity implements OnItemClickListener
{
	private long lExitTime;
	
	SwipeView mSwipeView;
	
	int[] images;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		initUI(R.layout.activity_home, R.string.title_activity_home);
	}

	@Override
	protected void initUI(int layoutId, int textId) {
		super.initUI(layoutId, textId);
		
		//init swipe view
		PageControl mPageControl = (PageControl) findViewById(R.id.page_control);
        mSwipeView = (SwipeView) findViewById(R.id.swipe_view);

        loadImages();
        
        for(int i=0; i<7;i++)
        {
        	mSwipeView.addView(new FrameLayout(this));
        }
        
        ImageView i0 = new ImageView(this);
        ImageView i1 = new ImageView(this);
        i0.setImageResource(images[0]);
        i1.setImageResource(images[1]);
        
        ((FrameLayout) mSwipeView.getChildContainer().getChildAt(0)).addView(i0);
        ((FrameLayout) mSwipeView.getChildContainer().getChildAt(1)).addView(i1);
        
        SwipeImageLoader mSwipeImageLoader = new SwipeImageLoader();
        
        mSwipeView.setOnPageChangedListener(mSwipeImageLoader);
        
        mSwipeView.setPageControl(mPageControl);
        
        
        //init main menu
        GridView gridView = (GridView) findViewById(R.id.shape_menu);
		gridView.setAdapter(new PersonalCenterAdatper(this));
		gridView.setOnItemClickListener(this);
	}

	private class SwipeImageLoader implements OnPageChangedListener
	{

		public void onPageChanged(int oldPage, int newPage) 
		{
			if(newPage > oldPage)//going forwards
			{
				if(newPage != (mSwipeView.getPageCount() - 1))//if at the end, don't load one page after the end
				{
					ImageView v = new ImageView(HomeActivity.this);
					v.setImageResource(images[newPage+1]);
					((FrameLayout) mSwipeView.getChildContainer().getChildAt(newPage+1)).addView(v);
				}
				if(oldPage != 0)//if at the beginning, don't destroy one before the beginning
				{
					((FrameLayout) mSwipeView.getChildContainer().getChildAt(oldPage-1)).removeAllViews();
				}
			}
			else //going backwards
			{
				if(newPage != 0)//if at the beginning, don't load one before the beginning
				{
					ImageView v = new ImageView(HomeActivity.this);
					v.setImageResource(images[newPage-1]);
					((FrameLayout) mSwipeView.getChildContainer().getChildAt(newPage-1)).addView(v);
				}
				if(oldPage != (mSwipeView.getPageCount()-1))//if at the end, don't destroy one page after the end
				{
					((FrameLayout) mSwipeView.getChildContainer().getChildAt(oldPage+1)).removeAllViews();
				}
			}

		}

	}

	private void loadImages()
	{
		//Not the most elegant way to do this, but it does enough for demo purposes...

		//The images are not actually being loaded into memory, but the resources 
		//ids are being put in a format that can be dealt with easily

		images = new int[25];
		images[0] = R.drawable.main_image001;
		images[1] = R.drawable.main_image002;
		images[2] = R.drawable.main_image003;
		images[3] = R.drawable.main_image004;
		images[4] = R.drawable.main_image005;
		images[5] = R.drawable.main_image006;
		images[6] = R.drawable.main_image007;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);

		switch (item.getItemId())
		{
		case R.id.home_menu_exit:
			this.finish();
			break;
		}

		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if ((System.currentTimeMillis() - lExitTime) > 2000)
			{
				Toast.makeText(this, getText(R.string.home_exit_warning), Toast.LENGTH_SHORT).show();
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
			intent.putExtra("UIType", MTFragmentFactory.MY_METTING);
			break;
		case 1:
			intent = new Intent(this, MettingListActivity.class);
			break;
		case 2:
			intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, MyMettingActivity.SIGNIN);
			return;
		case 3:
			intent = new Intent(this, PersonalActivity.class);
			break;
		case 4:
			intent = new Intent(this, MessageActivity.class);
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
		if (resultCode == RESULT_OK) {

			String result = data.getStringExtra("result");
			System.out.println(result);
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
