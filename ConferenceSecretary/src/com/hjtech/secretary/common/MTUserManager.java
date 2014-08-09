package com.hjtech.secretary.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.hjtech.secretary.data.MTUser;

import android.content.Context;

public class MTUserManager {
	private static Context context;
	private static MTUser user = null;
	public static void init(Context context){
		MTUserManager.context = context;
	}
	
	public static MTUser getUser(){
		if(user != null){
			return user;
		}
		FileInputStream ifs = null;
		MTUser user = null;
		try {
			ifs = context.openFileInput("userinfo.dat");
			ObjectInputStream ois = new ObjectInputStream(ifs);
			user = (MTUser) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				if(ifs != null){
					ifs.close();
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public static boolean save(MTUser user){
		MTUserManager.user = user;
		FileOutputStream ofs = null;
		try {
			ofs = context.openFileOutput("userinfo.dat", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(ofs);
			oos.writeObject(user);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally{
			try {
				if(ofs != null){
					ofs.close();
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static MTUser updateUser(MTUser user) {
		if (user.getCoCount() != 0) {
			MTUserManager.user.setCoCount(user.getCoCount());
		}
		if (user.getEnCount() != 0) {
			MTUserManager.user.setEnCount(user.getEnCount());
		}
		if (user.getMuEmail() != null) {
			MTUserManager.user.setMuEmail(user.getMuEmail());
		}
		if (user.getMuName() != null) {
			MTUserManager.user.setMuName(user.getMuName());
		}
		if (user.getMuNickName() != null) {
			MTUserManager.user.setMuNickName(user.getMuNickName());
		}
		if (user.getMuPassword() != null) {
			MTUserManager.user.setMuPassword(user.getMuPassword());
		}
		if (user.getMuPhoto() != null) {
			MTUserManager.user.setMuPhoto(user.getMuPhoto());
		}
		if (user.getMuPosition() != null) {
			MTUserManager.user.setMuPosition(user.getMuPosition());
		}
		if (user.getMuQq() != null) {
			MTUserManager.user.setMuQq(user.getMuQq());
		}
		if (user.getMuSector() != null) {
			MTUserManager.user.setMuSector(user.getMuSector());
		}
		if (user.getMuSexString() != null) {
			MTUserManager.user.setMuSex(user.getMuSex());
		}
		if (user.getMuUnitName() != null) {
			MTUserManager.user.setMuUnitName(user.getMuUnitName());
		}
		if (user.getMuWeixin() != null) {
			MTUserManager.user.setMuWeixin(user.getMuWeixin());
		}
		if (user.getMuPhotoImage() != null) {
			MTUserManager.user.setMuPhotoImage(user.getMuPhotoImage());
		}
		System.out.println(MTUserManager.user.getMuAccount());
		save(MTUserManager.user);
		return MTUserManager.user;
	}

}
