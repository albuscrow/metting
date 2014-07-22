package com.hjtech.secretary.data;

import java.sql.Timestamp;

/**
 * MtMetting entity. @author MyEclipse Persistence Tools
 */
public class MTMetting implements java.io.Serializable {

	// Fields

	private Long mmId;
	private String mmTitle;
	private String mmLogo;
	private String mmDesp;
	
	public static final int COLLECT = 1;
	public static final int UNCOLLECT = 0;
	private int isCollect;
	
	public static final int ENROLL = 0;
	public static final int UNENROLL = 2;
	public static final int SIGNIN = 1;
	private int isEnroll;
	
	private String mmAddress;
	
	public static final int FREE = 0;
	public static final int UNFREE = 1;
	private int mmFreeType;
	
	private int mmFeeAmount;
	
	private String memberRtt;
	
	private String mmStartdate;
	private String mmEndtime;
	
	public Long getMmId() {
		return mmId;
	}
	public void setMmId(Long mmId) {
		this.mmId = mmId;
	}
	public String getMmTitle() {
		return mmTitle;
	}
	public void setMmTitle(String mmTitle) {
		this.mmTitle = mmTitle;
	}
	public String getMmLogo() {
		return mmLogo;
	}
	public void setMmLogo(String mmLogo) {
		this.mmLogo = mmLogo;
	}
	public String getMmDesp() {
		return mmDesp;
	}
	public void setMmDesp(String mmDesp) {
		this.mmDesp = mmDesp;
	}
	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	public int getIsEnroll() {
		return isEnroll;
	}
	public String getIsEnrollStr() {
		switch (isEnroll) {
		case ENROLL:
			return "已报名";
		case UNENROLL:
			return "未报名";
		case SIGNIN:
			return "已签到";
		default:
			return null;
		}
	}
	public void setIsEnroll(int isEnroll) {
		this.isEnroll = isEnroll;
	}
	public String getMmAddress() {
		return "会议地点"+mmAddress;
	}
	public void setMmAddress(String mmAddress) {
		this.mmAddress = mmAddress;
	}
	public int getMmFreeType() {
		return mmFreeType;
	}
	public String getMmFreeTypeStr() {
		if (mmFreeType == 0) {
			return "免费会议";
		}else{
			return "收费会议";
		}
	}
	public void setMmFreeType(int mmFreeType) {
		this.mmFreeType = mmFreeType;
	}
	public int getMmFeeAmount() {
		return mmFeeAmount;
	}
	public void setMmFeeAmount(int mmFeeAmount) {
		this.mmFeeAmount = mmFeeAmount;
	}
	public String getMemberRtt() {
		if (memberRtt == null) {
			return "是否有参会限制："+"无限制";
		}
		return "是否有参会限制：" + memberRtt;
	}
	public void setMemberRtt(String memberRtt) {
		this.memberRtt = memberRtt;
	}
	public String getTime() {
		return mmStartdate + "-" + mmEndtime;
	}
	public String getMmStartdate() {
		return mmStartdate;
	}
	public void setMmStartdate(String mmStartdate) {
		this.mmStartdate = mmStartdate;
	}
	public String getMmEndtime() {
		return mmEndtime;
	}
	public void setMmEndtime(String mmEndtime) {
		this.mmEndtime = mmEndtime;
	}

}