package com.hjtech.secretary.data;

import java.util.List;

public class MTMessageListResult {
	int result;
	List<MTMessage> details;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public List<MTMessage> getDetails() {
		return details;
	}
	public void setDetails(List<MTMessage> details) {
		this.details = details;
	}
	
}
