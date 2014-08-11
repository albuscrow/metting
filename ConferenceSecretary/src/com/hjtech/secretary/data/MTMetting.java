package com.hjtech.secretary.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hjtech.secretary.R;

/**
 * MtMetting entity. @author MyEclipse Persistence Tools
 */
public class MTMetting implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3729758463593905136L;
	// Fields

	private Long mmId;
	private String mmTitle;
	private String mmLogo;
	private String mmDesp;
	
	public static final int COLLECT = 1;
	public static final int UNCOLLECT = 0;
	private int isCollect = 1;
	
	public static final int ENROLL = 0;
	public static final int UNENROLL = 2;
	public static final int SIGNIN = 1;
	private int isEnroll = -1;
	
	private String mmAddress;
	
	public static final int FREE = 0;
	public static final int UNFREE = 1;
	private int mmFeeType;
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
	private String mmEnqr;
	
	private String mmMayVisits;
	private int mmViewCount;
	private int mmSignCount;
	private int mmEnrollCount;
	private int mmStatus;
	
	private int enCount;
	
	public int getEnCount() {
		return enCount;
	}
	
	public int getEnCountPictureId() {
		if (enCount <= 5) {
			return R.drawable.star1;
		}else if(enCount <= 10){
			return R.drawable.star2;
		}else if(enCount <= 20){
			return R.drawable.star3;
		}else if(enCount <= 50){
			return R.drawable.star4;
		}else{
			return R.drawable.star5;
		}
	}
	
	public void setEnCount(int enCount) {
		this.enCount = enCount;
	}
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
		if (mmLogo == null) {
			return null;
		}
		if (mmLogo.charAt(0) != '/') {
			mmLogo = "/" + mmLogo;
		}
		return "http://211.155.229.136:8050"+mmLogo.replace("\\", "/");
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
		return mmFeeType;
	}
	public String getMmFreeTypeStr() {
		if (mmFeeType == 0) {
			return "免费";
		}else{
			return "收费";
		}
	}
	public void setMmFreeType(int mmFreeType) {
		this.mmFeeType = mmFreeType;
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
		return mmStartdate.substring(0,11) + "-" + mmEndtime.substring(0,11);
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
		if (mmEnpage == null || mmEnpage.length() == 0) {
			return "http://www.xxxx.com/wap/showShare/";
		}
		return "http://211.155.229.136:8050/"+mmEnpage.replace("\\", "/");
	}
	public void setMmEnpage(String mmEnpage) {
		this.mmEnpage = mmEnpage;
	}
	public String getMmEnqr() {
		if (mmEnqr == null || mmEnqr.length() == 0) {
			return null;
		}
		return "http://211.155.229.136:8050/"+mmEnqr.replace("\\", "/");
	}
	public void setMmEnqr(String enqr) {
		mmEnqr = enqr;
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
	
	private static final String[] WEEK = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	private Date startDate;
	private Calendar calendar = Calendar.getInstance();
	private String day;
	private String month;
	private String year;
	private String week;
	private boolean isTimeInit = false;
	
	public static final int END = 0;
	public static final int STARTED = 1;
	public static final int UNSTART = 2;
	
	private int isStarted;
	public void initTime(){
		if (!isTimeInit) {
			Date startDate = getDate(mmStartdate);
			calendar.setTime(startDate);
			day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			if (day.length() == 1) {
				day = "0" + day;
			}
			month = String.valueOf(calendar.get(Calendar.MONTH ) + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}
			year = String.valueOf(calendar.get(Calendar.YEAR));
			week = WEEK[calendar.get(Calendar.DAY_OF_WEEK) - 1];


			Date endDate = getDate(mmEndtime);
			Date currentDate = new Date();
			if (currentDate.before(startDate)) {
				isStarted = UNSTART;
			}else if(currentDate.before(endDate)){
				isStarted = STARTED;
			}else{
				isStarted = END;
			}
			isTimeInit = true;
		}

	}
	public Date getDate(String date) {
		if (startDate == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			try {
				startDate = format.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void getStartDay(){
		
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getIsStarted() {
		return isStarted;
	}
	public void setIsStarted(int isStarted) {
		this.isStarted = isStarted;
	}

}