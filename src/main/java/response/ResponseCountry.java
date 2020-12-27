package response;

import model.Country;

public class ResponseCountry extends Response implements IResponse {
	private Country country;
	
	public ResponseCountry(boolean success, String message, Country country) {
		super(success, message);
		this.country = country;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}	
