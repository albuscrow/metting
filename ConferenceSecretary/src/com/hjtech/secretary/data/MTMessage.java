package com.hjtech.secretary.data;

import java.io.Serializable;

/**
 * The Class MTMessage.
 * 
 * @author albuscrow
 */
public class MTMessage implements Serializable {
	
	/** The mm id. */
	public long mmId;
	
	/** The mm title. */
	public String mmTitle;
	
	/** The mm content. */
	public String mmContent;
	
	/** The mm user id. */
	public long mmUserId;
	
	/** The mm type. */
	public int mmType;
	
	/** The mm addtime. */
	public String mmAddtime;
	
	/**
	 * Gets the mm id.
	 * 
	 * @return the mm id
	 */
	public long getMmId() {
		return mmId;
	}
	
	/**
	 * Sets the mm id.
	 * 
	 * @param mmId
	 *            the new mm id
	 */
	public void setMmId(long mmId) {
		this.mmId = mmId;
	}
	
	/**
	 * Gets the mm title.
	 * 
	 * @return the mm title
	 */
	public String getMmTitle() {
		return mmTitle;
	}
	
	/**
	 * Sets the mm title.
	 * 
	 * @param mmTitle
	 *            the new mm title
	 */
	public void setMmTitle(String mmTitle) {
		this.mmTitle = mmTitle;
	}
	
	/**
	 * Gets the mm content.
	 * 
	 * @return the mm content
	 */
	public String getMmContent() {
		return mmContent;
	}
	
	/**
	 * Sets the mm content.
	 * 
	 * @param mmContent
	 *            the new mm content
	 */
	public void setMmContent(String mmContent) {
		this.mmContent = mmContent;
	}
	
	/**
	 * Gets the mm user id.
	 * 
	 * @return the mm user id
	 */
	public long getMmUserId() {
		return mmUserId;
	}
	
	/**
	 * Sets the mm user id.
	 * 
	 * @param mmUserId
	 *            the new mm user id
	 */
	public void setMmUserId(long mmUserId) {
		this.mmUserId = mmUserId;
	}
	
	/**
	 * Gets the mm type.
	 * 
	 * @return the mm type
	 */
	public int getMmType() {
		return mmType;
	}
	
	/**
	 * Sets the mm type.
	 * 
	 * @param mmType
	 *            the new mm type
	 */
	public void setMmType(int mmType) {
		this.mmType = mmType;
	}
	
	/**
	 * Gets the mm addtime.
	 * 
	 * @return the mm addtime
	 */
	public String getMmAddtime() {
		return mmAddtime.substring(0,11);
	}
	
	/**
	 * Sets the mm add time.
	 * 
	 * @param mmAddTime
	 *            the new mm add time
	 */
	public void setMmAddTime(String mmAddTime) {
		this.mmAddtime = mmAddTime;
	}
	
}
