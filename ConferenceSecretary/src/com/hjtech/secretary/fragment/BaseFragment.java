package com.hjtech.secretary.fragment;

import com.hjtech.secretary.activity.BaseActivity;
import com.hjtech.secretary.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * The Class BaseFragment.
 * 所有fragment的基类
 * @author albuscrow
 */
public class BaseFragment extends Fragment {
	
	/** The activity. */
	protected BaseActivity activity;
	
	/** The root view. */
	protected ViewGroup rootView;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity =  (BaseActivity) activity;
	}

	/**
	 * Inits the action bar.
	 * 
	 * @param leftId
	 *            the left id
	 * @param titleId
	 *            the title id
	 * @param rightId
	 *            the right id
	 */
	protected void initActionBar(int leftId, int titleId, int rightId) {
		if (titleId != 0) {
			activity.changeTitle(titleId);
		}
		
		if (leftId != 0) {
			activity.changeActionLeft(leftId);
		}
		
		if (rightId != 0) {
			activity.changeActionRight(rightId);
		}
	}
	
	/**
	 * Setback button.
	 */
	protected void setbackButton(){
		((MainActivity)activity).setbackButtonForFragment();
	}
	
	/**
	 *	findviewbyid的封闭 
	 * 
	 * @param id
	 *            the id
	 * @return the view
	 */
	protected View gv(int id){
		return rootView.findViewById(id);
	}
	
	/** The intent. */
	protected Intent intent;
	
	/**
	 * Sets the intent.
	 * 
	 * @param intent
	 *            the new intent
	 */
	public void setIntent(Intent intent){
		this.intent = intent;
	}
	
	/**
	 * Gets the intent.
	 * 
	 * @return the intent
	 */
	public Intent getIntent(){
		return intent;
	}

	/**
	 * Gets the base activity.
	 * 
	 * @return the base activity
	 */
	public BaseActivity getBaseActivity() {
		return activity;
	}
}
