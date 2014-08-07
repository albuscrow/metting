package com.hjtech.secretary.data;

import java.io.Serializable;

public class MTComment  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4839527929204445516L;
	private long mcId;
	private String mcContent;
	private long mcUserId;
	private String muName;
	private String mcAddtime;
	private String muPhoto;
	
	public String getMuPhoto() {
		if (muPhoto == null) {
			return null;
		}
		return "http://211.155.229.136:8050/"+muPhoto.replace("\\", "/");
	}
	public void setMuPhoto(String muPhoto) {
		this.muPhoto = muPhoto;
	}
	public long getMcId() {
		return mcId;
	}
	public void setMcId(long mcId) {
		this.mcId = mcId;
	}
	public String getMcContent() {
		return mcContent;
	}
	public void setMcContent(String mcContent) {
		this.mcContent = mcContent;
	}
	public long getMcUserId() {
		return mcUserId;
	}
	public void setMcUserId(long mcUserId) {
		this.mcUserId = mcUserId;
	}
	public String getMuName() {
		return muName;
	}
	public void setMuName(String muName) {
		this.muName = muName;
	}
	public String getMcAddtime() {
		return mcAddtime.substring(0, 11);
	}
	public void setMcAddtime(String mcAddtime) {
		this.mcAddtime = mcAddtime;
	}
}
