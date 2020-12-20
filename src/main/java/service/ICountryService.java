package service;

import java.util.List;
import java.util.Map;

import model.Country;

public interface ICountryService {
	
	List<Country> getAllCountries();
	
	Country getCountryByCodes(String code1, String code2);
	
	Country saveCountry(Country country);
	
	Country updateCountry(String code1, String code2, Country country);
	
	Map<String, Boolean> deleteCountry(String code1, String code2);
}
