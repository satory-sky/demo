package au.com.serenity.ms.service.exception;

import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.exception.ApiMnemonicCode;

public class ApiCallException extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = 4446145718702887911L;
	private ApiException exceptionDetails;

	public ApiCallException(String msg) {
		super(msg);
	} 
	
	public ApiCallException(String msg, ApiMnemonicCode exceptionDetails) {
		super(msg);
		this.exceptionDetails=new ApiException(exceptionDetails);
		
	}

	
	public ApiCallException(String msg, ApiException exceptionDetails) {
		super(msg);

		this.exceptionDetails = exceptionDetails;
	}

	public ApiException getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(ApiException exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

}
