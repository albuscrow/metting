package com.hjtech.secretary.data;

import java.util.List;

public class MTMettingListResult {
	int result;
	List<MTMetting> details;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public List<MTMetting> getDetails() {
		return details;
	}
	public void setDetails(List<MTMetting> details) {
		this.details = details;
	}
	
}
