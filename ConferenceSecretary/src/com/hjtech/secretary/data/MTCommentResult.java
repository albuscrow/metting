package com.hjtech.secretary.data;

import java.util.List;

public class MTCommentResult {
	private int total;
	private int page;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
