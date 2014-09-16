package com.hjtech.secretary.adapter;


import com.hjtech.secretary.R;
import com.hjtech.secretary.common.AppConfig;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The Class PersonalCenterAdatper.
 * 用于展示首页菜单
 * @author albuscrow
 */
public class PersonalCenterAdatper extends BaseAdapter {

	/** The inflater. */
	private LayoutInflater inflater;
	
	/** The context. */
	private Context context;
	
	
	/**
	 * Instantiates a new personal center adatper.
	 * 
	 * @param context
	 *            the context
	 */
	public PersonalCenterAdatper(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return 6;
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return null;
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/** The Constant menuItemText. */
	private static final String[] menuItemText = {"会议列表","我的会议","会议签到","个人中心","消息中心","关于我们"};
	
	/** The Constant menuItemImageId. */
	private static final int[] menuItemImageId = {R.drawable.home_metting_list, R.drawable.home_my_metting, R.drawable.home_sign,
			R.drawable.home_person, R.drawable.home_message, R.drawable.home_about,};
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.adapter_item_main_menu, parent, false);
		
		TextView item = (TextView) view.findViewById(R.id.main_menu_item);
		Drawable drawable = context.getResources().getDrawable(menuItemImageId[position]);
		/// 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		item.setCompoundDrawables(null,drawable, null, null);
		item.setText(menuItemText[position]);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, (int) (parent.getHeight()/2-1));
		view.setLayoutParams(params);
		return view;
	}
}
