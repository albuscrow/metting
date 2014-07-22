package com.hjtech.secretary.adapter;


import com.hjtech.secretary.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalCenterAdatper extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	
	
	public PersonalCenterAdatper(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}



	@Override
	public int getCount() {
		return 6;
	}



	@Override
	public Object getItem(int position) {
		return null;
	}



	@Override
	public long getItemId(int position) {
		return 0;
	}

	private static final String[] menuItemText = {"我的会议","会议列表","电子门票","个人中心","消息通知","关于我们"};
	private static final int[] menuItemImageId = {R.drawable.main_my_meeting, R.drawable.main_meet_list, R.drawable.main_ticket,
			R.drawable.main_personal, R.drawable.main_message, R.drawable.main_about,};
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.adapter_item_main_menu, null);
		
		TextView item = (TextView) view.findViewById(R.id.main_menu_item);
		Drawable drawable = context.getResources().getDrawable(menuItemImageId[position]);
		/// 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		item.setCompoundDrawables(null,drawable, null, null);
		item.setText(menuItemText[position]);
		return view;
	}
}
