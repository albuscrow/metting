package com.hjtech.secretary.data;

import java.util.List;

public class MTCommentResult {
	private int result;
	private List<MTComment> details;
	
	public int getResult() {
		return result;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
	
	public List<MTComment> getDetails() {
		return details;
	}
	
	public void setDetails(List<MTComment> details) {
		this.details = details;
	}
}
