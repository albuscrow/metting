package com.hjtech.secretary.data;

import java.io.Serializable;

public class MTComment  implements Serializable{
	private long mcId;
	private String mcContent;
	private long mcUserId;
	private String muName;
	private String mcAddtime;
	
	public long getMcId() {
		return mcId;
	}
	public void setMcId(long mcId) {
		this.mcId = mcId;
	}
	public String getMcContent() {
//		return mcContent;
		return "fdsafljdsafklsd;fjsdakjsd;aflsfjdsakfjdksalf;jdskalf;jdsaklfjdskaflj;dsafljdsa;fkljdsafljds;afjdksl;afjdsa;lfjds;kalfjdskla;fjdslafjsldfjls;akfjdsalfjd;skafjs";
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
