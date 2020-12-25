package service;

import java.util.List;
import model.Country;

public interface ICountryService {
	List<Country> getAllCountries();
	Country saveCountry(Country country);
	Country updateCountry(Country country);
	Country getMax() ;
	Country getMin() ;
	long distanceAverage();
	Country getCountryByAlphaCode(String alpha3Code);
}
