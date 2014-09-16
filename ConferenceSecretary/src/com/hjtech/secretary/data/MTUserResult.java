package com.hjtech.secretary.data;


/**
 * The Class MTUserResult.
 * 
 * @author albuscrow
 */
public class MTUserResult {
	
	/** The result. */
	int result;
	
	/** The details. */
	MTUser details;
	
	/** The en count. */
	int enCount;
	
	/** The co count. */
	int coCount;
	
	/**
	 * Gets the details.
	 * 
	 * @return the details
	 */
	public MTUser getDetails() {
		return details;
	}
	
	/**
	 * Sets the details.
	 * 
	 * @param details
	 *            the new details
	 */
	public void setDetails(MTUser details) {
		this.details = details;
	}
	
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
	 * Inits the.
	 */
	public void init(){
		details.setEnCount(enCount);
		details.setCoCount(coCount);
	}
	
}
