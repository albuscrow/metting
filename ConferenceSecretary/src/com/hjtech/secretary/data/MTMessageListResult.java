package com.hjtech.secretary.data;

import java.util.List;

/**
 * The Class MTMessageListResult.
 * 
 * @author albuscrow
 */
public class MTMessageListResult {
	
	/** The result. */
	int result;
	
	/** The details. */
	List<MTMessage> details;
	
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
	public List<MTMessage> getDetails() {
		return details;
	}
	
	/**
	 * Sets the details.
	 * 
	 * @param details
	 *            the new details
	 */
	public void setDetails(List<MTMessage> details) {
		this.details = details;
	}
	
}
