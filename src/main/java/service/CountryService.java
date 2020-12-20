package service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Country;
import repository.CountryRepository;

@Service
public class CountryService implements ICountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}

	@Override
	public Country getCountryByCodes(String code1, String code2) {
			return null;
	}

	@Override
	public Country saveCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Country updateCountry(String code1, String code2, Country country) {
		return null;
	}

	@Override
	public Map<String, Boolean> deleteCountry(String code1, String code2) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
