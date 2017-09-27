package au.com.serenity.ms.exception;

public enum BaseApiMnemonicCodes implements ApiMnemonicCode{
	MIDDLEWARE_EXCEPTION("Issue with middleware interaction."),
	NO_AGREEMENT_LISTED("Account service agreement combination"),
	INVALID_ACCOUNT_STATE("Can't do action with current account state"),
	AUTH_BAD_ACCOUNT_LIST("Token has an account that isn't yours!"),
	INVALID_ACCOUNT_AGREEMENT("Account and service agreement combination not allowed"),
	AUTH_ACCOUNT_UNAUTHORIZED("No access for account."),
	
	AUTH_INVALID_TOKEN("Invalid token."),
	PAYMENT_INVALID_BANK_ACCOUNT("Invalid bank account details."),
	PAYMENT_INVALID_CC_DETAILS("Invalid credit card details."),
	MIDDLEWARE_FAILED_PAYMENT_UPDATE("Issue while attempting to update details."),
	MIDDLEWARE_UPDATE_ISSUE("Issue while attempting to read details."),
	MIDDLEWARE_READ_ISSUE("Issue while attempting to read details."),
	INVALID_REQUEST("Invalid request."),
	
	//TODO TBR - MEANS TO BE REMOVED.
	PROFILE_FETCH_PROB_TBR("Error occurred while retrieving account profile details"),
	PROFILE_UPDATE_PROB_TBR("Your profile could not be updated. Please try again."),
	SUGGESTED_TOPUP_AMT_FETCH_PROB_TBR("Sorry, there was a problem retrieving suggested payment amount"),
	CREDITCARD_UPDATE_PROB_TBR("Error occured while adding or editing credit card"),
	VEHICLE_DETAILS_RETREIVAL_PROB_TBR("Vehicle details cannot be retrieved at this time. Please try again later."),
	ADDRESS_VALIDATION_PROB_TBR("There was an error retrieving an address match. Please select the address you typed below or re-enter  your details and try again."),
	TRIPS_FETCH_PROB_TBR("Error occurred while retrieving Trip details"),
	STATES_RETRIEVAL_PROB_TBR("Error occurred while retrieving states"),
	CREDITCARD_TYPES_SURCHARGES_RETRIEVAL_PROB_TBR("Error occurred while retrieving Credit Card Types");
	
	
	private final String defaultMessage;
	
	private BaseApiMnemonicCodes( String defaultMessage) {
		
		this.defaultMessage=defaultMessage;
	}

	public String getCode() {
		return this.toString();
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	@Override
	public String getRawMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
