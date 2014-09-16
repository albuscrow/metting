package com.hjtech.secretary.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hjtech.secretary.R;

/**
 * MtMetting entity. @author MyEclipse Persistence Tools
 * 
 * @author albuscrow
 */
public class MTMetting implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3729758463593905136L;
	// Fields

	/** The mm id. */
	private Long mmId;
	
	/** The mm title. */
	private String mmTitle;
	
	/** The mm logo. */
	private String mmLogo;
	
	/** The mm desp. */
	private String mmDesp;
	
	/** The Constant COLLECT. */
	public static final int COLLECT = 1;
	
	/** The Constant UNCOLLECT. */
	public static final int UNCOLLECT = 0;
	
	/** The is collect. */
	private int isCollect = 1;
	
	/** The Constant ENROLL. */
	public static final int ENROLL = 0;
	
	/** The Constant UNENROLL. */
	public static final int UNENROLL = 2;
	
	/** The Constant SIGNIN. */
	public static final int SIGNIN = 1;
	
	/** The is enroll. */
	private int isEnroll = -1;
	
	/** The mm address. */
	private String mmAddress;
	
	/** The Constant FREE. */
	public static final int FREE = 0;
	
	/** The Constant UNFREE. */
	public static final int UNFREE = 1;
	
	/** The mm fee type. */
	private int mmFeeType;
	
	/** The mm fee amount. */
	private int mmFeeAmount;
	
	/** The member rtt. */
	private String memberRtt;
	
	/** The mm startdate. */
	private String mmStartdate;
	
	/** The mm endtime. */
	private String mmEndtime;
	
	/** The mm type. */
	private int mmType;
	
	/** The mm addtime. */
	private String mmAddtime;
	
	/** The mm sponsor. */
	private String mmSponsor;
	
	/** The mm organizer. */
	private String mmOrganizer;
	
	/** The mm phone. */
	private String mmPhone;
	
	/** The mm scale. */
	private int mmScale;

	/** The mm qr. */
	private String mmQr;
	
	/** The mm enpage. */
	private String mmEnpage;
	
	/** The mm enqr. */
	private String mmEnqr;
	
	/** The mm may visits. */
	private String mmMayVisits;
	
	/** The mm view count. */
	private int mmViewCount;
	
	/** The mm sign count. */
	private int mmSignCount;
	
	/** The mm enroll count. */
	private int mmEnrollCount;
	
	/** The mm status. */
	private int mmStatus;
	
	/** The en count. */
	private int enCount;
	
	/**
	 * Gets the en count.
	 * 
	 * @return the en count
	 */
	public int getEnCount() {
		return enCount;
	}
	
	/**
	 * Gets the en count picture id.
	 * 
	 * @return the en count picture id
	 */
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
	 * Gets the mm id.
	 * 
	 * @return the mm id
	 */
	public Long getMmId() {
		return mmId;
	}
	
	/**
	 * Sets the mm id.
	 * 
	 * @param mmId
	 *            the new mm id
	 */
	public void setMmId(Long mmId) {
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
	 * Gets the mm logo.
	 * 
	 * @return the mm logo
	 */
	public String getMmLogo() {
		if (mmLogo == null) {
			return null;
		}
		if (mmLogo.charAt(0) != '/') {
			mmLogo = "/" + mmLogo;
		}
		return "http://211.155.229.136:8050"+mmLogo.replace("\\", "/");
	}
	
	/**
	 * Sets the mm logo.
	 * 
	 * @param mmLogo
	 *            the new mm logo
	 */
	public void setMmLogo(String mmLogo) {
		this.mmLogo = mmLogo;
	}
	
	/**
	 * Gets the mm desp.
	 * 
	 * @return the mm desp
	 */
	public String getMmDesp() {
		return mmDesp;
	}
	
	/**
	 * Sets the mm desp.
	 * 
	 * @param mmDesp
	 *            the new mm desp
	 */
	public void setMmDesp(String mmDesp) {
		this.mmDesp = mmDesp;
	}
	
	/**
	 * Gets the checks if is collect.
	 * 
	 * @return the checks if is collect
	 */
	public int getIsCollect() {
		return isCollect;
	}
	
	/**
	 * Sets the checks if is collect.
	 * 
	 * @param isCollect
	 *            the new checks if is collect
	 */
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	
	/**
	 * Gets the checks if is enroll.
	 * 
	 * @return the checks if is enroll
	 */
	public int getIsEnroll() {
		return isEnroll;
	}
	
	/**
	 * Gets the checks if is enroll str.
	 * 
	 * @return the checks if is enroll str
	 */
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
	
	/**
	 * Sets the checks if is enroll.
	 * 
	 * @param isEnroll
	 *            the new checks if is enroll
	 */
	public void setIsEnroll(int isEnroll) {
		this.isEnroll = isEnroll;
	}
	
	/**
	 * Gets the mm address.
	 * 
	 * @return the mm address
	 */
	public String getMmAddress() {
		return mmAddress;
	}
	
	/**
	 * Sets the mm address.
	 * 
	 * @param mmAddress
	 *            the new mm address
	 */
	public void setMmAddress(String mmAddress) {
		this.mmAddress = mmAddress;
	}
	
	/**
	 * Gets the mm free type.
	 * 
	 * @return the mm free type
	 */
	public int getMmFreeType() {
		return mmFeeType;
	}
	
	/**
	 * Gets the mm free type str.
	 * 
	 * @return the mm free type str
	 */
	public String getMmFreeTypeStr() {
		if (mmFeeType == 0) {
			return "免费";
		}else{
			return "收费";
		}
	}
	
	/**
	 * Sets the mm free type.
	 * 
	 * @param mmFreeType
	 *            the new mm free type
	 */
	public void setMmFreeType(int mmFreeType) {
		this.mmFeeType = mmFreeType;
	}
	
	/**
	 * Gets the mm fee amount.
	 * 
	 * @return the mm fee amount
	 */
	public int getMmFeeAmount() {
		return mmFeeAmount;
	}
	
	/**
	 * Sets the mm fee amount.
	 * 
	 * @param mmFeeAmount
	 *            the new mm fee amount
	 */
	public void setMmFeeAmount(int mmFeeAmount) {
		this.mmFeeAmount = mmFeeAmount;
	}
	
	/**
	 * Gets the member rtt.
	 * 
	 * @return the member rtt
	 */
	public String getMemberRtt() {
		if (memberRtt == null) {
			return "是否有参会限制："+"无限制";
		}
		return "是否有参会限制：" + memberRtt;
	}
	
	/**
	 * Gets the member rtt for detail.
	 * 
	 * @return the member rtt for detail
	 */
	public String getMemberRttForDetail() {
		if (memberRtt == null) {
			return "无限制";
		}
		return memberRtt;
	}
	
	/**
	 * Sets the member rtt.
	 * 
	 * @param memberRtt
	 *            the new member rtt
	 */
	public void setMemberRtt(String memberRtt) {
		this.memberRtt = memberRtt;
	}
	
	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	public String getTime() {
		return mmStartdate.substring(0,11) + "-" + mmEndtime.substring(0,11);
	}
	
	/**
	 * Gets the mm startdate.
	 * 
	 * @return the mm startdate
	 */
	public String getMmStartdate() {
		return mmStartdate;
	}
	
	/**
	 * Sets the mm startdate.
	 * 
	 * @param mmStartdate
	 *            the new mm startdate
	 */
	public void setMmStartdate(String mmStartdate) {
		this.mmStartdate = mmStartdate;
	}
	
	/**
	 * Gets the mm endtime.
	 * 
	 * @return the mm endtime
	 */
	public String getMmEndtime() {
		return mmEndtime;
	}
	
	/**
	 * Sets the mm endtime.
	 * 
	 * @param mmEndtime
	 *            the new mm endtime
	 */
	public void setMmEndtime(String mmEndtime) {
		this.mmEndtime = mmEndtime;
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
		return mmAddtime;
	}
	
	/**
	 * Sets the mm addtime.
	 * 
	 * @param mmAddtime
	 *            the new mm addtime
	 */
	public void setMmAddtime(String mmAddtime) {
		this.mmAddtime = mmAddtime;
	}
	
	/**
	 * Gets the mm sponsor.
	 * 
	 * @return the mm sponsor
	 */
	public String getMmSponsor() {
		return mmSponsor;
	}
	
	/**
	 * Sets the mm sponsor.
	 * 
	 * @param mmSponsor
	 *            the new mm sponsor
	 */
	public void setMmSponsor(String mmSponsor) {
		this.mmSponsor = mmSponsor;
	}
	
	/**
	 * Gets the mm organizer.
	 * 
	 * @return the mm organizer
	 */
	public String getMmOrganizer() {
		return mmOrganizer;
	}
	
	/**
	 * Sets the mm organizer.
	 * 
	 * @param mmOrganizer
	 *            the new mm organizer
	 */
	public void setMmOrganizer(String mmOrganizer) {
		this.mmOrganizer = mmOrganizer;
	}
	
	/**
	 * Gets the mm phone.
	 * 
	 * @return the mm phone
	 */
	public String getMmPhone() {
		return mmPhone;
	}
	
	/**
	 * Sets the mm phone.
	 * 
	 * @param mmPhone
	 *            the new mm phone
	 */
	public void setMmPhone(String mmPhone) {
		this.mmPhone = mmPhone;
	}
	
	/**
	 * Gets the mm scale.
	 * 
	 * @return the mm scale
	 */
	public int getMmScale() {
		return mmScale;
	}
	
	/**
	 * Sets the mm scale.
	 * 
	 * @param mmScale
	 *            the new mm scale
	 */
	public void setMmScale(int mmScale) {
		this.mmScale = mmScale;
	}
	
	/**
	 * Gets the mm qr.
	 * 
	 * @return the mm qr
	 */
	public String getMmQr() {
		return mmQr;
	}
	
	/**
	 * Sets the mm qr.
	 * 
	 * @param mmQr
	 *            the new mm qr
	 */
	public void setMmQr(String mmQr) {
		this.mmQr = mmQr;
	}
	
	/**
	 * Gets the mm enpage.
	 * 
	 * @return the mm enpage
	 */
	public String getMmEnpage() {
		if (mmEnpage == null || mmEnpage.length() == 0) {
			return "http://www.xxxx.com/wap/showShare/";
		}
		if (mmEnpage.indexOf("http://") != -1) {
			return mmEnpage;
		}
		if (mmEnpage.charAt(0) == '/') {
			mmEnpage = mmEnpage.substring(1);
		}
		return "http://211.155.229.136:8050/"+mmEnpage.replace("\\", "/");
	}
	
	/**
	 * Sets the mm enpage.
	 * 
	 * @param mmEnpage
	 *            the new mm enpage
	 */
	public void setMmEnpage(String mmEnpage) {
		this.mmEnpage = mmEnpage;
	}
	
	/**
	 * Gets the mm enqr.
	 * 
	 * @return the mm enqr
	 */
	public String getMmEnqr() {
		if (mmEnqr == null || mmEnqr.length() == 0) {
			return null;
		}
		return "http://211.155.229.136:8050/"+mmEnqr.replace("\\", "/");
	}
	
	/**
	 * Sets the mm enqr.
	 * 
	 * @param enqr
	 *            the new mm enqr
	 */
	public void setMmEnqr(String enqr) {
		mmEnqr = enqr;
	}
	
	/**
	 * Gets the mm may visits.
	 * 
	 * @return the mm may visits
	 */
	public String getMmMayVisits() {
		return mmMayVisits;
	}
	
	/**
	 * Sets the mm may visits.
	 * 
	 * @param mmMayVisits
	 *            the new mm may visits
	 */
	public void setMmMayVisits(String mmMayVisits) {
		this.mmMayVisits = mmMayVisits;
	}
	
	/**
	 * Gets the mm view count.
	 * 
	 * @return the mm view count
	 */
	public int getMmViewCount() {
		return mmViewCount;
	}
	
	/**
	 * Sets the mm view count.
	 * 
	 * @param mmViewCount
	 *            the new mm view count
	 */
	public void setMmViewCount(int mmViewCount) {
		this.mmViewCount = mmViewCount;
	}
	
	/**
	 * Gets the mm sign count.
	 * 
	 * @return the mm sign count
	 */
	public int getMmSignCount() {
		return mmSignCount;
	}
	
	/**
	 * Sets the mm sign count.
	 * 
	 * @param mmSignCount
	 *            the new mm sign count
	 */
	public void setMmSignCount(int mmSignCount) {
		this.mmSignCount = mmSignCount;
	}
	
	/**
	 * Gets the mm enroll count.
	 * 
	 * @return the mm enroll count
	 */
	public int getMmEnrollCount() {
		return mmEnrollCount;
	}
	
	/**
	 * Sets the mm enroll count.
	 * 
	 * @param mmEnrollCount
	 *            the new mm enroll count
	 */
	public void setMmEnrollCount(int mmEnrollCount) {
		this.mmEnrollCount = mmEnrollCount;
	}
	
	/**
	 * Gets the mm status.
	 * 
	 * @return the mm status
	 */
	public int getMmStatus() {
		return mmStatus;
	}
	
	/**
	 * Sets the mm status.
	 * 
	 * @param mmStatus
	 *            the new mm status
	 */
	public void setMmStatus(int mmStatus) {
		this.mmStatus = mmStatus;
	}
	
	/**
	 * Gets the time for detail.
	 * 
	 * @return the time for detail
	 */
	public CharSequence getTimeForDetail() {
		return "会议时间：" + mmStartdate.substring(0, 4) + "年"
				+ mmStartdate.substring(5,7) + "月"
				+ mmStartdate.substring(8, 10) + "日";
	}
	
	/** The Constant WEEK. */
	private static final String[] WEEK = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	
	/** The calendar. */
	private Calendar calendar = Calendar.getInstance();
	
	/** The day. */
	private String day;
	
	/** The month. */
	private String month;
	
	/** The year. */
	private String year;
	
	/** The week. */
	private String week;
	
	/** The is time init. */
	private boolean isTimeInit = false;
	
	/** The Constant END. */
	public static final int END = 0;
	
	/** The Constant STARTED. */
	public static final int STARTED = 1;
	
	/** The Constant UNSTART. */
	public static final int UNSTART = 2;
	
	/** The is started. */
	private int isStarted;
	
	/**
	 * Inits the time.
	 */
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
	
	/**
	 * Gets the date.
	 * 
	 * @param date
	 *            the date
	 * @return the date
	 */
	public Date getDate(String date) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			try {
				return format.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return  null;
	}
	
	/**
	 * Gets the start day.
	 * 
	 * @return the start day
	 */
	public void getStartDay(){
		
	}
	
	/**
	 * Gets the day.
	 * 
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	
	/**
	 * Sets the day.
	 * 
	 * @param day
	 *            the new day
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	/**
	 * Gets the month.
	 * 
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	
	/**
	 * Sets the month.
	 * 
	 * @param month
	 *            the new month
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	
	/**
	 * Gets the year.
	 * 
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * Sets the year.
	 * 
	 * @param year
	 *            the new year
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * Gets the week.
	 * 
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}
	
	/**
	 * Sets the week.
	 * 
	 * @param week
	 *            the new week
	 */
	public void setWeek(String week) {
		this.week = week;
	}
	
	/**
	 * Gets the checks if is started.
	 * 
	 * @return the checks if is started
	 */
	public int getIsStarted() {
		return isStarted;
	}
	
	/**
	 * Sets the checks if is started.
	 * 
	 * @param isStarted
	 *            the new checks if is started
	 */
	public void setIsStarted(int isStarted) {
		this.isStarted = isStarted;
	}

}