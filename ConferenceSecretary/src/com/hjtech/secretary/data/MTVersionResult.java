package com.hjtech.secretary.data;

import com.hjtech.secretary.common.AppVersion;


public class MTVersionResult {
	int result;
	AppVersion details;
	
	public AppVersion getDetails() {
		return details;
	}
	public void setDetails(AppVersion details) {
		this.details = details;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
}
