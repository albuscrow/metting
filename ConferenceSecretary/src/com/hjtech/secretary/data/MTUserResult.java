package com.hjtech.secretary.data;


public class MTUserResult {
	int result;
	MTUser details;
	int enCount;
	int coCount;
	
	public MTUser getDetails() {
		return details;
	}
	public void setDetails(MTUser details) {
		this.details = details;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	public void init(){
		details.setEnCount(enCount);
		details.setCoCount(coCount);
	}
	
}
