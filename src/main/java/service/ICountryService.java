package service;

import java.util.List;
import model.Country;

public interface ICountryService {
	List<Country> getAllCountries();
	Country saveCountry(Country country);
	int updateRequestCount(String alpha3Code);
	Country getMax() ;
	Country getMin() ;
	long distanceAverage();
	Country getCountryByAlphaCode(String alpha3Code);
}
