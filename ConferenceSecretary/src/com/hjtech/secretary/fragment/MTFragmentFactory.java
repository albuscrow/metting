package com.hjtech.secretary.fragment;

import java.lang.ref.SoftReference;

import android.util.SparseArray;

/**
 * A factory for creating MTFragment objects.
 */
public class MTFragmentFactory {
	
	/** The Constant MY_METTING. */
	public static final int MY_METTING = 1;
	
	/** The Constant METTING_LIST. */
	public static final int METTING_LIST = 2;
	
	/** The Constant METTING_DETAILS. */
	public static final int METTING_DETAILS = 3;
	
	/** The Constant INVITE. */
	public static final int INVITE = 4;
	
	/** The Constant MESSAGE. */
	public static final int MESSAGE = 5;
	
	/** The fragment array. */
	private static SparseArray<SoftReference<BaseFragment>> fragmentArray = new SparseArray<SoftReference<BaseFragment>>();
	
	
	/**
	 * Gets the fragment.
	 * 
	 * @param id
	 *            the id
	 * @return the fragment
	 */
	public static BaseFragment getFragment(int id){
		SoftReference<BaseFragment> fragmentRef = fragmentArray.get(id);
		BaseFragment fragment = null;
		if (fragmentRef == null || (fragment = fragmentRef.get()) == null) {
			fragment = produceFragment(id);
			fragmentRef = new SoftReference<BaseFragment>(fragment);
			fragmentArray.put(id, fragmentRef);
		}
		
		return fragment;
	}

	/**
	 * Produce fragment.
	 * 
	 * @param id
	 *            the id
	 * @return the base fragment
	 */
	private static BaseFragment produceFragment(int id) {
		switch (id) {
		case MY_METTING:
			return new MyMettingFragment();
			
		case METTING_LIST:
			return new MettingListFragment();

		case METTING_DETAILS:
			return new MettingDetailsFragment();
			
		case INVITE:
			return new InviteFragment();
			
		case MESSAGE:
			return new MessageFragment();
			
		default:
			return null;
		}
	}

	/**
	 * Clear.
	 */
	public static void clear() {
		if (fragmentArray != null) {
			fragmentArray.clear();
		}
	}
	
}
