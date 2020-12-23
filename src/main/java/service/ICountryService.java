package service;

import java.util.List;
import model.Country;

public interface ICountryService {
	List<Country> getAllCountries();
	Country saveCountry(Country country);
	Country updateCountry(String code1);
	List<Country> getMax() ;
	List<Country> getMin() ;
	long distanceAverage();
}
