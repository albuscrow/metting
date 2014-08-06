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
	
	private int mmType;
	private String mmAddtime;
	
	private String mmSponsor;
	private String mmOrganizer;
	private String mmPhone;
	
	private int mmScale;

	private String mmQr;
	private String mmEnpage;
	private String Enqr;
	
	private String mmMayVisits;
	private int mmViewCount;
	private int mmSignCount;
	private int mmEnrollCount;
	private int mmStatus;
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
		return mmAddress;
	}
	
	public void setMmAddress(String mmAddress) {
		this.mmAddress = mmAddress;
	}
	public int getMmFreeType() {
		return mmFreeType;
	}
	public String getMmFreeTypeStr() {
		if (mmFreeType == 0) {
			return "免费";
		}else{
			return "收费";
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
	public String getMemberRttForDetail() {
		if (memberRtt == null) {
			return "无限制";
		}
		return memberRtt;
	}
	public void setMemberRtt(String memberRtt) {
		this.memberRtt = memberRtt;
	}
	public String getTime() {
		return mmStartdate.substring(0,11) + "－" + mmEndtime.substring(0,11);
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
	public int getMmType() {
		return mmType;
	}
	
	public void setMmType(int mmType) {
		this.mmType = mmType;
	}
	public String getMmAddtime() {
		return mmAddtime;
	}
	public void setMmAddtime(String mmAddtime) {
		this.mmAddtime = mmAddtime;
	}
	public String getMmSponsor() {
		return mmSponsor;
	}
	public void setMmSponsor(String mmSponsor) {
		this.mmSponsor = mmSponsor;
	}
	public String getMmOrganizer() {
		return mmOrganizer;
	}
	public void setMmOrganizer(String mmOrganizer) {
		this.mmOrganizer = mmOrganizer;
	}
	public String getMmPhone() {
		return mmPhone;
	}
	public void setMmPhone(String mmPhone) {
		this.mmPhone = mmPhone;
	}
	public int getMmScale() {
		return mmScale;
	}
	public void setMmScale(int mmScale) {
		this.mmScale = mmScale;
	}
	public String getMmQr() {
		return mmQr;
	}
	public void setMmQr(String mmQr) {
		this.mmQr = mmQr;
	}
	public String getMmEnpage() {
		return mmEnpage;
	}
	public void setMmEnpage(String mmEnpage) {
		this.mmEnpage = mmEnpage;
	}
	public String getEnqr() {
		return Enqr;
	}
	public void setEnqr(String enqr) {
		Enqr = enqr;
	}
	public String getMmMayVisits() {
		return mmMayVisits;
	}
	public void setMmMayVisits(String mmMayVisits) {
		this.mmMayVisits = mmMayVisits;
	}
	public int getMmViewCount() {
		return mmViewCount;
	}
	public void setMmViewCount(int mmViewCount) {
		this.mmViewCount = mmViewCount;
	}
	public int getMmSignCount() {
		return mmSignCount;
	}
	public void setMmSignCount(int mmSignCount) {
		this.mmSignCount = mmSignCount;
	}
	public int getMmEnrollCount() {
		return mmEnrollCount;
	}
	public void setMmEnrollCount(int mmEnrollCount) {
		this.mmEnrollCount = mmEnrollCount;
	}
	public int getMmStatus() {
		return mmStatus;
	}
	public void setMmStatus(int mmStatus) {
		this.mmStatus = mmStatus;
	}
	public CharSequence getTimeForDetail() {
		return "会议时间：" + mmStartdate.substring(0, 4) + "年"
				+ mmStartdate.substring(5,7) + "月"
				+ mmStartdate.substring(8, 10) + "日";
	}
	
	

}