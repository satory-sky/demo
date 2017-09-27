package au.com.serenity.ms.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class ApiException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2616515566807188301L;
	
	@JsonInclude(value=Include.NON_NULL)
	private String rawCode;

	@JsonInclude(value=Include.NON_NULL)
	private String rawMessage;
	private String errorCode;
	private String errorMessage;
	private List<String> apiSubIssues=null;
	
	
	public ApiException(ApiMnemonicCode errorCode) {
		super();
		this.errorCode = errorCode.getCode();
		this.errorMessage = errorCode.getDefaultMessage();
	}
	
	public ApiException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ApiException() {
		super();
		
		
	}

	public String getRawCode() {
		return rawCode;
	}
	public void setRawCode(String rawCode) {
		this.rawCode = rawCode;
	}
	public String getRawMessage() {
		return rawMessage;
	}
	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getApiSubIssues() {
		return apiSubIssues;
	}
	
	/*public void setApiSubIssues(String[] apiSubIssues) {
		this.apiSubIssues = apiSubIssues;
	}*/
	
	
	@JsonInclude(value=Include.NON_NULL)
	public void setApiSubIssues(List<String> apiSubIssues) {
		this.apiSubIssues = apiSubIssues;
	}
	
}
