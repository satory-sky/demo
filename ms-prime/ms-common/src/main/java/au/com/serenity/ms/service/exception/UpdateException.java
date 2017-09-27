package au.com.serenity.ms.service.exception;

import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.exception.ApiMnemonicCode;

public class UpdateException extends ApiCallException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1928685484578140684L;

	public UpdateException(String msg, ApiException exceptionDetails) {
		super(msg, exceptionDetails);
	}

	public UpdateException(String msg, ApiException exceptionDetails, String rawCode, String rawMessage) {
		super(msg, exceptionDetails);
		exceptionDetails.setRawCode(rawCode);
		exceptionDetails.setRawMessage(rawMessage);
	}

	public UpdateException(String msg, ApiMnemonicCode exceptionDetails) {
		super(msg, exceptionDetails);
	}
	
	public UpdateException(String msg,  ApiMnemonicCode errorCode,  String rawCode, String rawMessage) {
		super(msg,errorCode);
		super.getExceptionDetails().setRawCode(rawCode);
		super.getExceptionDetails().setRawMessage(rawMessage);
		
	}
}
