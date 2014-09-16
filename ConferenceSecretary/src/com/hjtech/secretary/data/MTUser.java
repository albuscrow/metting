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
 * 
 * @author albuscrow
 */
public class MTUser implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5263530164919208607L;
	
	/** The mu id. */
	private long muId;
	
	/** The mu account. */
	private String muAccount;
	
	/** The mu password. */
	private String muPassword;
	
	/** The mu unit name. */
	private String muUnitName;
	
	/** The mu name. */
	private String muName;
	
	/** The mu sex. */
	private int muSex = -1;
	
	/** The mu nick name. */
	private String muNickName;
	
	/** The mu position. */
	private String muPosition;
	
	/** The mu sector. */
	private String muSector;
	
	/** The mu qq. */
	private String muQq;
	
	/** The mu mobile. */
	private String muMobile;
	
	/** The mu email. */
	private String muEmail;
	
	/** The mu weixin. */
	private String muWeixin;
	
	/** The mu provice. */
	private String muProvice;
	
	/** The mu city. */
	private String muCity;
	
	/** The mu addtime. */
	private String muAddtime;
	
	/** The mu photo. */
	private String muPhoto;
	
	/** The mu qr. */
	private String muQr;
	
	/** The mu last date. */
	private String muLastDate;
	
	/** The mu last ip. */
	private String muLastIp;
	
	/** The mu status. */
	private String muStatus;
	
	/** The mu register type. */
	private int muRegisterType;
	
	/** The en count. */
	private int enCount = -1;
	
	/** The co count. */
	private int coCount = -1;
	
	/** The mu sex string. */
	private String muSexString;
	
	/** The mu photo image. */
	transient private Bitmap muPhotoImage;
	
	
	/**
	 * Gets the mu photo image.
	 * 
	 * @return the mu photo image
	 */
	public Bitmap getMuPhotoImage() {
		return muPhotoImage;
	}
	
	/**
	 * Sets the mu photo image.
	 * 
	 * @param muPhotoImage
	 *            the new mu photo image
	 */
	public void setMuPhotoImage(Bitmap muPhotoImage) {
		this.muPhotoImage = muPhotoImage;
	}
	
	/**
	 * Gets the mu sex.
	 * 
	 * @return the mu sex
	 */
	public int getMuSex() {
		return muSex;
	}
	
	/**
	 * Gets the mu sex string.
	 * 
	 * @return the mu sex string
	 */
	public String getMuSexString(){
		setMuSex(muSex);
		return muSexString;
	}
	
	/**
	 * Sets the mu sex string.
	 * 
	 * @param string
	 *            the new mu sex string
	 */
	public void setMuSexString(String string){
		muSexString = string;
	}
	
	/**
	 * Sets the mu sex.
	 * 
	 * @param muSex
	 *            the new mu sex
	 */
	public void setMuSex(int muSex) {
		this.muSex = muSex;
		if (muSex == 0) {
			muSexString = "男";
		}else if (muSex == 1){
			muSexString = "女";
		}
	}
	
	/**
	 * Gets the mu sector.
	 * 
	 * @return the mu sector
	 */
	public String getMuSector() {
		return muSector;
	}
	
	/**
	 * Sets the mu sector.
	 * 
	 * @param muSector
	 *            the new mu sector
	 */
	public void setMuSector(String muSector) {
		this.muSector = muSector;
	}
	
	/**
	 * Gets the mu qq.
	 * 
	 * @return the mu qq
	 */
	public String getMuQq() {
		return muQq;
	}
	
	/**
	 * Sets the mu qq.
	 * 
	 * @param muQq
	 *            the new mu qq
	 */
	public void setMuQq(String muQq) {
		this.muQq = muQq;
	}
	
	/**
	 * Gets the en count.
	 * 
	 * @return the en count
	 */
	public int getEnCount() {
		return enCount;
	}
	
	/**
	 * Sets the en count.
	 * 
	 * @param enCount
	 *            the new en count
	 */
	public void setEnCount(int enCount) {
		this.enCount = enCount;
	}
	
	/**
	 * Gets the co count.
	 * 
	 * @return the co count
	 */
	public int getCoCount() {
		return coCount;
	}
	
	/**
	 * Sets the co count.
	 * 
	 * @param coCount
	 *            the new co count
	 */
	public void setCoCount(int coCount) {
		this.coCount = coCount;
	}
	
	/**
	 * Gets the mu register type.
	 * 
	 * @return the mu register type
	 */
	public int getMuRegisterType() {
		return muRegisterType;
	}
	
	/**
	 * Sets the mu register type.
	 * 
	 * @param muRegisterType
	 *            the new mu register type
	 */
	public void setMuRegisterType(int muRegisterType) {
		this.muRegisterType = muRegisterType;
	}
	
	/**
	 * Gets the mu id.
	 * 
	 * @return the mu id
	 */
	public long getMuId() {
		return muId;
	}
	
	/**
	 * Sets the mu id.
	 * 
	 * @param muId
	 *            the new mu id
	 */
	public void setMuId(long muId) {
		this.muId = muId;
	}
	
	/**
	 * Gets the mu account.
	 * 
	 * @return the mu account
	 */
	public String getMuAccount() {
		return muAccount;
	}
	
	/**
	 * Sets the mu account.
	 * 
	 * @param muAccount
	 *            the new mu account
	 */
	public void setMuAccount(String muAccount) {
		this.muAccount = muAccount;
	}
	
	/**
	 * Gets the mu password.
	 * 
	 * @return the mu password
	 */
	public String getMuPassword() {
		return muPassword;
	}
	
	/**
	 * Sets the mu password.
	 * 
	 * @param muPassword
	 *            the new mu password
	 */
	public void setMuPassword(String muPassword) {
		this.muPassword = muPassword;
	}
	
	/**
	 * Gets the mu unit name.
	 * 
	 * @return the mu unit name
	 */
	public String getMuUnitName() {
		if (muUnitName == null) {
			return "";
		}
		return muUnitName;
	}
	
	/**
	 * Sets the mu unit name.
	 * 
	 * @param muUnitName
	 *            the new mu unit name
	 */
	public void setMuUnitName(String muUnitName) {
		this.muUnitName = muUnitName;
	}
	
	/**
	 * Gets the mu name.
	 * 
	 * @return the mu name
	 */
	public String getMuName() {
		if (muName == null) {
			return "";
		}
		return muName;
	}
	
	/**
	 * Sets the mu name.
	 * 
	 * @param muName
	 *            the new mu name
	 */
	public void setMuName(String muName) {
		this.muName = muName;
	}
	
	/**
	 * Gets the mu nick name.
	 * 
	 * @return the mu nick name
	 */
	public String getMuNickName() {
		return muNickName;
	}
	
	/**
	 * Sets the mu nick name.
	 * 
	 * @param muNickName
	 *            the new mu nick name
	 */
	public void setMuNickName(String muNickName) {
		this.muNickName = muNickName;
	}
	
	/**
	 * Gets the mu position.
	 * 
	 * @return the mu position
	 */
	public String getMuPosition() {
		return muPosition;
	}
	
	/**
	 * Sets the mu position.
	 * 
	 * @param muPosition
	 *            the new mu position
	 */
	public void setMuPosition(String muPosition) {
		this.muPosition = muPosition;
	}
	
	/**
	 * Gets the mu mobile.
	 * 
	 * @return the mu mobile
	 */
	public String getMuMobile() {
		return muMobile;
	}
	
	/**
	 * Sets the mu mobile.
	 * 
	 * @param muMobile
	 *            the new mu mobile
	 */
	public void setMuMobile(String muMobile) {
		this.muMobile = muMobile;
	}
	
	/**
	 * Gets the mu email.
	 * 
	 * @return the mu email
	 */
	public String getMuEmail() {
		if (muEmail == null) {
			return "";
		}
		return muEmail;
	}
	
	/**
	 * Sets the mu email.
	 * 
	 * @param muEmail
	 *            the new mu email
	 */
	public void setMuEmail(String muEmail) {
		this.muEmail = muEmail;
	}
	
	/**
	 * Gets the mu weixin.
	 * 
	 * @return the mu weixin
	 */
	public String getMuWeixin() {
		return muWeixin;
	}
	
	/**
	 * Sets the mu weixin.
	 * 
	 * @param muWeixin
	 *            the new mu weixin
	 */
	public void setMuWeixin(String muWeixin) {
		this.muWeixin = muWeixin;
	}
	
	/**
	 * Gets the mu provice.
	 * 
	 * @return the mu provice
	 */
	public String getMuProvice() {
		return muProvice;
	}
	
	/**
	 * Sets the mu provice.
	 * 
	 * @param muProvice
	 *            the new mu provice
	 */
	public void setMuProvice(String muProvice) {
		this.muProvice = muProvice;
	}
	
	/**
	 * Gets the mu city.
	 * 
	 * @return the mu city
	 */
	public String getMuCity() {
		return muCity;
	}
	
	/**
	 * Sets the mu city.
	 * 
	 * @param muCity
	 *            the new mu city
	 */
	public void setMuCity(String muCity) {
		this.muCity = muCity;
	}
	
	/**
	 * Gets the mu addtime.
	 * 
	 * @return the mu addtime
	 */
	public String getMuAddtime() {
		return muAddtime;
	}
	
	/**
	 * Sets the mu addtime.
	 * 
	 * @param muAddtime
	 *            the new mu addtime
	 */
	public void setMuAddtime(String muAddtime) {
		this.muAddtime = muAddtime;
	}
	
	/**
	 * Gets the mu photo.
	 * 
	 * @return the mu photo
	 */
	public String getMuPhoto() {
		if (muPhoto == null || muPhoto.length() == 0) {
			return null;
		}
		return "http://211.155.229.136:8050/"+muPhoto.replace("\\", "/") + "?data=" + String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * Sets the mu photo.
	 * 
	 * @param muPhoto
	 *            the new mu photo
	 */
	public void setMuPhoto(String muPhoto) {
		this.muPhoto = muPhoto;
	}
	
	/**
	 * Gets the mu qr.
	 * 
	 * @return the mu qr
	 */
	public String getMuQr() {
		return muQr;
	}
	
	/**
	 * Sets the mu qr.
	 * 
	 * @param muQr
	 *            the new mu qr
	 */
	public void setMuQr(String muQr) {
		this.muQr = muQr;
	}
	
	/**
	 * Gets the mu last date.
	 * 
	 * @return the mu last date
	 */
	public String getMuLastDate() {
		return muLastDate;
	}
	
	/**
	 * Sets the mu last date.
	 * 
	 * @param muLastDate
	 *            the new mu last date
	 */
	public void setMuLastDate(String muLastDate) {
		this.muLastDate = muLastDate;
	}
	
	/**
	 * Gets the mu last ip.
	 * 
	 * @return the mu last ip
	 */
	public String getMuLastIp() {
		return muLastIp;
	}
	
	/**
	 * Sets the mu last ip.
	 * 
	 * @param muLastIp
	 *            the new mu last ip
	 */
	public void setMuLastIp(String muLastIp) {
		this.muLastIp = muLastIp;
	}
	
	/**
	 * Gets the mu status.
	 * 
	 * @return the mu status
	 */
	public String getMuStatus() {
		return muStatus;
	}
	
	/**
	 * Sets the mu status.
	 * 
	 * @param muStatus
	 *            the new mu status
	 */
	public void setMuStatus(String muStatus) {
		this.muStatus = muStatus;
	}
	
	/**
	 * Gets the image file.
	 * 
	 * @return the image file
	 */
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
	
	/**
	 * Adds the collect.
	 */
	public void addCollect() {
		++ coCount;
	}
	
	/**
	 * Adds the enroll.
	 */
	public void addEnroll() {
		++ enCount;
	}
	
	/**
	 * Min collect.
	 */
	public void minCollect() {
		-- coCount;
	}
}