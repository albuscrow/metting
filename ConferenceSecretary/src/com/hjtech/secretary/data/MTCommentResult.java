package com.hjtech.secretary.data;

import java.util.List;

/**
 * The Class MTCommentResult.
 * 
 * @author albuscrow
 */
public class MTCommentResult {
	
	/** The total. */
	private int total;
	
	/** The page. */
	private int page;
	
	/** The result. */
	private int result;
	
	/** The details. */
	private List<MTComment> details;
	
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
	public List<MTComment> getDetails() {
		return details;
	}
	
	/**
	 * Sets the details.
	 * 
	 * @param details
	 *            the new details
	 */
	public void setDetails(List<MTComment> details) {
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
