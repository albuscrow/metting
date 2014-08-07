package com.hjtech.secretary.data;

import java.util.List;

public class MTMettingListResult {
	int total;
	int result;
	int page;
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
