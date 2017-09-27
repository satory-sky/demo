package au.com.serenity.ms.service.exception;

import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.exception.ApiMnemonicCode;

public class MiddlewareException extends ApiCallException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3759314997029371000L;

	public MiddlewareException(String msg, ApiException exceptionDetails) {
		super( msg, exceptionDetails);
		
	}
	
	public MiddlewareException(String msg, ApiMnemonicCode exceptionDetails) {
		super( msg, exceptionDetails);
		
	}
	
	public MiddlewareException(String msg) {
		super( msg);
		
	}


}
