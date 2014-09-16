package com.hjtech.secretary.listener;

import java.io.Serializable;

import com.hjtech.secretary.activity.RegisterActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * The listener interface for receiving newActivity events. The class that is
 * interested in processing a newActivity event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addNewActivityListener<code> method. When
 * the newActivity event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see NewActivityEvent
 */
public class NewActivityListener implements OnClickListener {


	/** The intent. */
	private Intent intent;
	
	/** The context. */
	private Context context;

	/**
	 * Instantiates a new new activity listener.
	 * 
	 * @param context
	 *            the context
	 * @param clz
	 *            the clz
	 * @param objects
	 *            the objects
	 */
	public NewActivityListener(Context context, Class<?> clz, Serializable...objects){
		this.context = context;
		this.intent = new Intent(context, clz);
		for (int i = 0; i < objects.length; i+=2) {
			intent.putExtra((String)objects[i], objects[i + 1]);
		}
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		context.startActivity(intent);
	}

}
