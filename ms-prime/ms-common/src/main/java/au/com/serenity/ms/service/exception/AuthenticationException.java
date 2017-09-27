package au.com.serenity.ms.service.exception;

import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.exception.ApiMnemonicCode;

public class AuthenticationException extends ApiCallException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3098023221987923837L;

	public AuthenticationException(String msg, ApiException exceptionDetails) {
		super( msg, exceptionDetails);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String msg, ApiException exceptionDetails, String rawCode, String rawMessage) {
		super(msg, exceptionDetails);
		exceptionDetails.setRawCode(rawCode);
		exceptionDetails.setRawMessage(rawMessage);
		
		
	}

	
	public AuthenticationException(String msg, ApiMnemonicCode exceptionDetails, String code, String rawMessage) {
		super(msg, exceptionDetails);
		super.getExceptionDetails().setRawCode(code);
		super.getExceptionDetails().setRawMessage(rawMessage);
	
		
	}
}
