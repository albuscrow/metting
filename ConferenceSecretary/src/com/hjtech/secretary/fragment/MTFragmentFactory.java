package com.hjtech.secretary.fragment;

import java.lang.ref.SoftReference;

import android.util.SparseArray;

public class MTFragmentFactory {
	public static final int MY_METTING = 1;
	public static final int METTING_LIST = 2;
	public static final int METTING_DETAILS = 3;
	public static final int INVITE = 4;
	public static final int MESSAGE = 5;
	
	private static SparseArray<SoftReference<BaseFragment>> fragmentArray = new SparseArray<SoftReference<BaseFragment>>();
	
	
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

	public static void clear() {
		if (fragmentArray != null) {
			fragmentArray.clear();
		}
	}
	
}
