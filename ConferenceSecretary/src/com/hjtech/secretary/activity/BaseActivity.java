package com.hjtech.secretary.activity;

import com.hjtech.secretary.R;
import com.hjtech.secretary.utils.MTCommon;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

/**
 * The Class BaseActivity.
 * 所有Activity 的基类，抽出一些所有Activity都会用到的方法
 * @author albuscrow
 */
public class BaseActivity extends FragmentActivity {
	
	/** The wait bar. */
	private View waitBar;
	
	/** The left text.
	 *  Action bar 左边的元素
	 */
	private TextView leftText;
	
	/** The title text view. 
	 *  Action bar 中间的元素
	 */
	private TextView titleTextView;
	
	/** The right text view.
	 *  Action bar 右边的元素
	 */
	private TextView rightTextView;
	

	/**
	 * Inits the ui.
	 * 
	 * @param layoutId
	 *            the layout id
	 */
	protected void initUI(int layoutId){
		initUI(layoutId, 0, 0, 0);
	}
	
	/**
	 * Inits the ui.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @param leftContentId
	 *            the left content id
	 * @param titleId
	 *            the title id
	 */
	protected void initUI(int layoutId, int leftContentId, int titleId) {
		initUI(layoutId, leftContentId, titleId, 0);
	}
	
	/**
	 * Inits the ui.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @param titleId
	 *            the title id
	 */
	protected void initUI(int layoutId, int titleId) {
		initUI(layoutId, 0, titleId, 0);
	}
	

	/**
	 * Inits the ui.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @param backContentId
	 *            the back content id
	 * @param titleContentId
	 *            the title content id
	 * @param rightTextViewContentId
	 *            the right text view content id
	 */
	@SuppressLint("NewApi")
	protected void initUI(int layoutId, int backContentId, int titleContentId, int rightTextViewContentId) {
		// init title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		setContentView(layoutId);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar);
//		View mCustomView = getLayoutInflater().inflate(R.layout.actionbar, null);
//		ActionBar bar = getActionBar();
//		bar.setCustomView(mCustomView, new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		leftText = (TextView)gv(R.id.back_text);
		if (backContentId != 0) {
			showActionLeft();
			MTCommon.setContent(leftText, backContentId);
		}else{
			hideActionLeft();
		}
		
		titleTextView = (TextView)gv(R.id.titlebar_text);
		
		if (titleContentId != 0) {
			MTCommon.setContent(titleTextView, titleContentId);
		}
		
		rightTextView = (TextView)gv(R.id.rightView);
		if (rightTextViewContentId != 0) {
			showActionRight();
			MTCommon.setContent(rightTextView, rightTextViewContentId);
		}else{
			hideActionRight();
		}
		
		waitBar = findViewById(R.id.common_wait);
	}
	

	/**
	 * Inits the ui without action bar.
	 * 
	 * @param layoutId
	 *            the layout id
	 */
	protected void initUIWithoutActionBar(int layoutId) {
		// init title bar
		setContentView(layoutId);
		waitBar = findViewById(R.id.common_wait);
	}
	
	

	/**
	 * Change title.
	 * 
	 * @param StringId
	 *            the string id
	 */
	public void changeTitle(int StringId){
		MTCommon.setContent(titleTextView, StringId);
	}

	/**
	 * Change action left.
	 * 
	 * @param StringId
	 *            the string id
	 */
	public void changeActionLeft(int StringId){
		showActionLeft();
		MTCommon.setContent(leftText, StringId);
	}
	

	/**
	 * Change action right.
	 * 
	 * @param StringId
	 *            the string id
	 */
	public void changeActionRight(int StringId){
		showActionRight();
		MTCommon.setContent(leftText, StringId);
	}

	/**
	 * Hide action right.
	 */
	protected void hideActionRight() {
		rightTextView.setVisibility(View.GONE);
	}
	
	/**
	 * Show action right.
	 */
	protected void showActionRight() {
		rightTextView.setVisibility(View.VISIBLE);
	}

	/**
	 * Hide action left.
	 */
	protected void hideActionLeft() {
		leftText.setVisibility(View.GONE);
	}
	
	/**
	 * Show action left.
	 */
	protected void showActionLeft() {
		leftText.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Sets the back button.
	 */
	protected void setBackButton(){
		leftText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});
	}


	/**
	 * Sets the right click.
	 * 
	 * @param listener
	 *            the new right click
	 */
	protected void setRightClick(OnClickListener listener){
		rightTextView.setOnClickListener(listener);
	}
	

	/**
	 *	封闭findviewbyid() 
	 * 
	 * @param id
	 *            the id
	 * @return the view
	 */
	protected View gv(int id){
		return findViewById(id);
	}
	

	/**
	 * Show wait bar.
	 */
	protected void showWaitBar(){
		if (waitBar == null) {
			waitBar = findViewById(R.id.common_wait);
		}
		waitBar.setVisibility(View.VISIBLE);
	}
	
	
	/**
	 * Hide wait bar.
	 */
	protected void hideWaitBar(){
		if (waitBar == null) {
			waitBar = findViewById(R.id.common_wait);
		}
		waitBar.setVisibility(View.VISIBLE);
		waitBar.setVisibility(View.GONE);
	}
	

	/**
	 * Setback button for fragment.
	 */
	public void setbackButtonForFragment() {

		if (this instanceof MainActivity) {
			final MainActivity activity = (MainActivity) this;
			leftText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (activity.getBackStack().size() > 0) {
						activity.back();
					}else{
						finish();
					}
				}
			});
		}
	}

}