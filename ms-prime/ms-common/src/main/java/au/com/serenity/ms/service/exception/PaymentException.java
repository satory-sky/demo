package au.com.serenity.ms.service.exception;

import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.exception.ApiMnemonicCode;

public class PaymentException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1928685484578140684L;

	
	public PaymentException(String msg, ApiException exceptionDetails) {
		super(msg, exceptionDetails);
	}

	public PaymentException(String msg, ApiException exceptionDetails, String rawCode, String rawMessage) {
		super(msg, exceptionDetails,rawCode,rawMessage);
		
	}
	
	public PaymentException(String msg, ApiMnemonicCode exceptionDetails, String rawCode, String rawMessage) {
		super(msg, exceptionDetails,rawCode,rawMessage);
		
		
	}

}
