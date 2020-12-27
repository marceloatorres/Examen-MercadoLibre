package response;

import java.util.List;

import model.Country;

public class ResponseStatistic extends Response implements IResponse{
	List<Country> countries;

	public ResponseStatistic(boolean success, String message, List<Country> countries) {
		super(success, message);
		this.countries = countries;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	
	
}
