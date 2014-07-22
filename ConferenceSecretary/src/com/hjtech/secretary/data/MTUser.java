package com.hjtech.secretary.data;


/**
 * MtMetting entity. @author MyEclipse Persistence Tools
 */
public class MTUser implements java.io.Serializable {

	private static final long serialVersionUID = 5263530164919208607L;
	private long muId;
	private String muAccount;
	private String muPassword;
	private String muUnitName;
	private String muName;
	private String muNickName;
	private String muPosition;
	private String muMobile;
	private String muEmail;
	private String muWeixin;
	private String muProvice;
	private String muCity;
	private String muAddtime;
	private String muPhoto;
	private String muQr;
	private String muLastDate;
	private String muLastIp;
	private String muStatus;
	private int muRegisterType;
	
	public int getMuRegisterType() {
		return muRegisterType;
	}
	public void setMuRegisterType(int muRegisterType) {
		this.muRegisterType = muRegisterType;
	}
	public long getMuId() {
		return muId;
	}
	public void setMuId(long muId) {
		this.muId = muId;
	}
	public String getMuAccount() {
		return muAccount;
	}
	public void setMuAccount(String muAccount) {
		this.muAccount = muAccount;
	}
	public String getMuPassword() {
		return muPassword;
	}
	public void setMuPassword(String muPassword) {
		this.muPassword = muPassword;
	}
	public String getMuUnitName() {
		return muUnitName;
	}
	public void setMuUnitName(String muUnitName) {
		this.muUnitName = muUnitName;
	}
	public String getMuName() {
		return muName;
	}
	public void setMuName(String muName) {
		this.muName = muName;
	}
	public String getMuNickName() {
		return muNickName;
	}
	public void setMuNickName(String muNickName) {
		this.muNickName = muNickName;
	}
	public String getMuPosition() {
		return muPosition;
	}
	public void setMuPosition(String muPosition) {
		this.muPosition = muPosition;
	}
	public String getMuMobile() {
		return muMobile;
	}
	public void setMuMobile(String muMobile) {
		this.muMobile = muMobile;
	}
	public String getMuEmail() {
		return muEmail;
	}
	public void setMuEmail(String muEmail) {
		this.muEmail = muEmail;
	}
	public String getMuWeixin() {
		return muWeixin;
	}
	public void setMuWeixin(String muWeixin) {
		this.muWeixin = muWeixin;
	}
	public String getMuProvice() {
		return muProvice;
	}
	public void setMuProvice(String muProvice) {
		this.muProvice = muProvice;
	}
	public String getMuCity() {
		return muCity;
	}
	public void setMuCity(String muCity) {
		this.muCity = muCity;
	}
	public String getMuAddtime() {
		return muAddtime;
	}
	public void setMuAddtime(String muAddtime) {
		this.muAddtime = muAddtime;
	}
	public String getMuPhoto() {
		return muPhoto;
	}
	public void setMuPhoto(String muPhoto) {
		this.muPhoto = muPhoto;
	}
	public String getMuQr() {
		return muQr;
	}
	public void setMuQr(String muQr) {
		this.muQr = muQr;
	}
	public String getMuLastDate() {
		return muLastDate;
	}
	public void setMuLastDate(String muLastDate) {
		this.muLastDate = muLastDate;
	}
	public String getMuLastIp() {
		return muLastIp;
	}
	public void setMuLastIp(String muLastIp) {
		this.muLastIp = muLastIp;
	}
	public String getMuStatus() {
		return muStatus;
	}
	public void setMuStatus(String muStatus) {
		this.muStatus = muStatus;
	}
}