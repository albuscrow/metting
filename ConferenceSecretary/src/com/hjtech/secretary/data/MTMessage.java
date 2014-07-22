package com.hjtech.secretary.data;

import java.io.Serializable;

public class MTMessage implements Serializable {
	public long mmId;
	public String mmTitle;
	public String mmContent;
	public long mmUserId;
	public int mmType;
	public String mmAddtime;
	public long getMmId() {
		return mmId;
	}
	public void setMmId(long mmId) {
		this.mmId = mmId;
	}
	public String getMmTitle() {
		return mmTitle;
	}
	public void setMmTitle(String mmTitle) {
		this.mmTitle = mmTitle;
	}
	public String getMmContent() {
		return mmContent;
	}
	public void setMmContent(String mmContent) {
		this.mmContent = mmContent;
	}
	public long getMmUserId() {
		return mmUserId;
	}
	public void setMmUserId(long mmUserId) {
		this.mmUserId = mmUserId;
	}
	public int getMmType() {
		return mmType;
	}
	public void setMmType(int mmType) {
		this.mmType = mmType;
	}
	public String getMmAddtime() {
		return mmAddtime;
	}
	public void setMmAddTime(String mmAddTime) {
		this.mmAddtime = mmAddTime;
	}
	
}
