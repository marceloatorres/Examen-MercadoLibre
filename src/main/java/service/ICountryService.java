package service;

import java.util.List;
import model.Country;

public interface ICountryService {
	List<Country> getAllCountries();
	Country saveCountry(Country country);
	Country updateCountry(String code1);
	Country getMax() ;
	Country getMin() ;
	long distanceAverage();
}
