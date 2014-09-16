package com.hjtech.secretary.data;

import java.io.Serializable;

/**
 * The Class MTComment.
 * 
 * @author albuscrow
 */
public class MTComment  implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4839527929204445516L;
	
	/** The mc id. */
	private long mcId;
	
	/** The mc content. */
	private String mcContent;
	
	/** The mc user id. */
	private long mcUserId;
	
	/** The mu name. */
	private String muName;
	
	/** The mc addtime. */
	private String mcAddtime;
	
	/** The mu photo. */
	private String muPhoto;
	
	/**
	 * Gets the mu photo.
	 * 
	 * @return the mu photo
	 */
	public String getMuPhoto() {
		if (muPhoto == null || muPhoto.length() == 0) {
			return null;
		}
		String temp = muPhoto.replace("\\", "/");
		if (temp.charAt(0) == '/') {
			temp = temp.substring(1);
		}
		if (temp.indexOf("http") != -1) {
			return temp;
		}
		return DataProvider.BASE_URL_IMAGE + temp + "?time=" + String.valueOf(System.currentTimeMillis());
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
	 * Gets the mc id.
	 * 
	 * @return the mc id
	 */
	public long getMcId() {
		return mcId;
	}
	
	/**
	 * Sets the mc id.
	 * 
	 * @param mcId
	 *            the new mc id
	 */
	public void setMcId(long mcId) {
		this.mcId = mcId;
	}
	
	/**
	 * Gets the mc content.
	 * 
	 * @return the mc content
	 */
	public String getMcContent() {
		return mcContent;
	}
	
	/**
	 * Sets the mc content.
	 * 
	 * @param mcContent
	 *            the new mc content
	 */
	public void setMcContent(String mcContent) {
		this.mcContent = mcContent;
	}
	
	/**
	 * Gets the mc user id.
	 * 
	 * @return the mc user id
	 */
	public long getMcUserId() {
		return mcUserId;
	}
	
	/**
	 * Sets the mc user id.
	 * 
	 * @param mcUserId
	 *            the new mc user id
	 */
	public void setMcUserId(long mcUserId) {
		this.mcUserId = mcUserId;
	}
	
	/**
	 * Gets the mu name.
	 * 
	 * @return the mu name
	 */
	public String getMuName() {
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
	 * Gets the mc addtime.
	 * 
	 * @return the mc addtime
	 */
	public String getMcAddtime() {
		return mcAddtime.substring(0, 11);
	}
	
	/**
	 * Sets the mc addtime.
	 * 
	 * @param mcAddtime
	 *            the new mc addtime
	 */
	public void setMcAddtime(String mcAddtime) {
		this.mcAddtime = mcAddtime;
	}
}
