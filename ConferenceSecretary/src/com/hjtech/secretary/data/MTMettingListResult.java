package com.hjtech.secretary.data;

import java.util.List;

/**
 * The Class MTMettingListResult.
 * 
 * @author albuscrow
 */
public class MTMettingListResult {
	
	/** The total. */
	int total;
	
	/** The result. */
	int result;
	
	/** The page. */
	int page;
	
	/** The details. */
	List<MTMetting> details;
	
	/**
	 * Gets the result.
	 * 
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	
	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            the new result
	 */
	public void setResult(int result) {
		this.result = result;
	}
	
	/**
	 * Gets the details.
	 * 
	 * @return the details
	 */
	public List<MTMetting> getDetails() {
		return details;
	}
	
	/**
	 * Sets the details.
	 * 
	 * @param details
	 *            the new details
	 */
	public void setDetails(List<MTMetting> details) {
		this.details = details;
	}
	
	/**
	 * Gets the total.
	 * 
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * Sets the total.
	 * 
	 * @param total
	 *            the new total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * Gets the page.
	 * 
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * Sets the page.
	 * 
	 * @param page
	 *            the new page
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
}
