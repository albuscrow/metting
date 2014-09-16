package com.hjtech.secretary.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * The Class ViewPagerCompat.
 * 
 * @author albuscrow
 */
public class ViewPagerCompat extends ViewPager {

    //mViewTouchMode表示ViewPager是否全权控制滑动事件，默认为false，即不控制
    /** The m view touch mode. */
    private boolean mViewTouchMode = false;

    /**
	 * Instantiates a new view pager compat.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
    public ViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
	 * Sets the view touch mode.
	 * 
	 * @param b
	 *            the new view touch mode
	 */
    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
            //全权控制滑动事件
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
            //终止控制滑动事件
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    /**
	 * 在mViewTouchMode为true的时候，ViewPager不拦截点击事件，点击事件将由子View处理.
	 * 
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mViewTouchMode) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
	 * 在mViewTouchMode为true或者滑动方向不是左右的时候，ViewPager将放弃控制点击事件，
	 * 这样做有利于在ViewPager中加入ListView等可以滑动的控件，否则两者之间的滑动将会有冲突.
	 * 
	 * @param direction
	 *            the direction
	 * @return true, if successful
	 */
    @Override
    public boolean arrowScroll(int direction) {
        if (mViewTouchMode) return false;
        if (direction != FOCUS_LEFT && direction != FOCUS_RIGHT) return false;
        return super.arrowScroll(direction);
    }

}
