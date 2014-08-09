package com.hjtech.secretary.data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hjtech.secretary.common.MettingApplication;
import com.tencent.android.tpush.horse.n;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;


/**
 * MtMetting entity. @author MyEclipse Persistence Tools
 */
public class MTUser implements java.io.Serializable {

	private static final long serialVersionUID = 5263530164919208607L;
	private long muId;
	private String muAccount;
	private String muPassword;
	private String muUnitName;
	private String muName;
	private int muSex = -1;
	private String muNickName;
	private String muPosition;
	private String muSector;
	private String muQq;
	private String muMobile;
	private String muEmail;
	private String muWeixin;
	private String muProvice;
	private String muCity;
	private String muAddtime;
	private String muPhoto;
	private String muQr;
	private String muLastDate;
	private String muLastIp;
	private String muStatus;
	private int muRegisterType;
	private int enCount = -1;
	private int coCount = -1;
	private String muSexString;
	transient private Bitmap muPhotoImage;
	
	
	public Bitmap getMuPhotoImage() {
		return muPhotoImage;
	}
	public void setMuPhotoImage(Bitmap muPhotoImage) {
		this.muPhotoImage = muPhotoImage;
	}
	public int getMuSex() {
		return muSex;
	}
	public String getMuSexString(){
		setMuSex(muSex);
		return muSexString;
	}
	public void setMuSexString(String string){
		muSexString = string;
	}
	public void setMuSex(int muSex) {
		this.muSex = muSex;
		if (muSex == 0) {
			muSexString = "男";
		}else if (muSex == 1){
			muSexString = "女";
		}
	}
	public String getMuSector() {
		return muSector;
	}
	public void setMuSector(String muSector) {
		this.muSector = muSector;
	}
	public String getMuQq() {
		return muQq;
	}
	public void setMuQq(String muQq) {
		this.muQq = muQq;
	}
	public int getEnCount() {
		return enCount;
	}
	public void setEnCount(int enCount) {
		this.enCount = enCount;
	}
	public int getCoCount() {
		return coCount;
	}
	public void setCoCount(int coCount) {
		this.coCount = coCount;
	}
	public int getMuRegisterType() {
		return muRegisterType;
	}
	public void setMuRegisterType(int muRegisterType) {
		this.muRegisterType = muRegisterType;
	}
	public long getMuId() {
		return muId;
	}
	public void setMuId(long muId) {
		this.muId = muId;
	}
	public String getMuAccount() {
		return muAccount;
	}
	public void setMuAccount(String muAccount) {
		this.muAccount = muAccount;
	}
	public String getMuPassword() {
		return muPassword;
	}
	public void setMuPassword(String muPassword) {
		this.muPassword = muPassword;
	}
	public String getMuUnitName() {
		return muUnitName;
	}
	public void setMuUnitName(String muUnitName) {
		this.muUnitName = muUnitName;
	}
	public String getMuName() {
		return muName;
	}
	public void setMuName(String muName) {
		this.muName = muName;
	}
	public String getMuNickName() {
		return muNickName;
	}
	public void setMuNickName(String muNickName) {
		this.muNickName = muNickName;
	}
	public String getMuPosition() {
		return muPosition;
	}
	public void setMuPosition(String muPosition) {
		this.muPosition = muPosition;
	}
	public String getMuMobile() {
		return muMobile;
	}
	public void setMuMobile(String muMobile) {
		this.muMobile = muMobile;
	}
	public String getMuEmail() {
		return muEmail;
	}
	public void setMuEmail(String muEmail) {
		this.muEmail = muEmail;
	}
	public String getMuWeixin() {
		return muWeixin;
	}
	public void setMuWeixin(String muWeixin) {
		this.muWeixin = muWeixin;
	}
	public String getMuProvice() {
		return muProvice;
	}
	public void setMuProvice(String muProvice) {
		this.muProvice = muProvice;
	}
	public String getMuCity() {
		return muCity;
	}
	public void setMuCity(String muCity) {
		this.muCity = muCity;
	}
	public String getMuAddtime() {
		return muAddtime;
	}
	public void setMuAddtime(String muAddtime) {
		this.muAddtime = muAddtime;
	}
	public String getMuPhoto() {
		if (muPhoto == null || muPhoto.length() == 0) {
			return null;
		}
		return "http://211.155.229.136:8050/"+muPhoto.replace("\\", "/");
	}
	public void setMuPhoto(String muPhoto) {
		this.muPhoto = muPhoto;
	}
	public String getMuQr() {
		return muQr;
	}
	public void setMuQr(String muQr) {
		this.muQr = muQr;
	}
	public String getMuLastDate() {
		return muLastDate;
	}
	public void setMuLastDate(String muLastDate) {
		this.muLastDate = muLastDate;
	}
	public String getMuLastIp() {
		return muLastIp;
	}
	public void setMuLastIp(String muLastIp) {
		this.muLastIp = muLastIp;
	}
	public String getMuStatus() {
		return muStatus;
	}
	public void setMuStatus(String muStatus) {
		this.muStatus = muStatus;
	}
	public File getImageFile() {
		File f =new File(MettingApplication.context.getCacheDir(), "tempFile.png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		muPhotoImage.compress(CompressFormat.PNG, 80 , bos);
		byte[] bitmapdata = bos.toByteArray();	
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return f;
	}
	public void addCollect() {
		++ coCount;
	}
	public void addEnroll() {
		++ enCount;
	}
	public void minCollect() {
		-- enCount;
	}
}