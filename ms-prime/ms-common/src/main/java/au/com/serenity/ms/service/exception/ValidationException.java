package au.com.serenity.ms.service.exception;

import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.exception.ApiMnemonicCode;

public class ValidationException extends ApiCallException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1928685484578140684L;

	public ValidationException(String msg, ApiException exceptionDetails) {
		super(msg, exceptionDetails);
		// TODO Auto-generated constructor stub
	}
	
	public ValidationException(String msg, ApiMnemonicCode errorCode) {
		super(msg);
		super.setExceptionDetails(new ApiException(errorCode));

	}
	
	public ValidationException(String msg,  ApiException errorCode,  String rawCode, String rawMessage) {
		super(msg);
		super.setExceptionDetails(errorCode);
		super.getExceptionDetails().setRawCode(rawCode);
		super.getExceptionDetails().setRawMessage(rawMessage);
		
	}
	public ValidationException(String msg,  ApiMnemonicCode errorCode,  String rawCode, String rawMessage) {
		super(msg);
		super.setExceptionDetails(new ApiException(errorCode));
		super.getExceptionDetails().setRawCode(rawCode);
		super.getExceptionDetails().setRawMessage(rawMessage);
		
	}
	

}
