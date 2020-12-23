package service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import model.Country;
import repository.CountryRepository;

@Service
public class CountryService implements ICountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	
	@Override
	@Async
	public List<Country> getAllCountries() {
		return countryRepository.findAll(Sort.by(Sort.Direction.DESC, "requestCount"));
	}

	@Override
	public Country saveCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Country updateCountry(String code1) {
			Country updatedCountry = new Country();
				Country country = new Country();
				country = countryRepository.findById(code1).orElse(null);
				
				if(country == null) {
					return null;
				}
				
				country.increaseRequestCount();
				updatedCountry = countryRepository.save(country);
				
			return updatedCountry;
	}

	@Override
	@Async
	public List<Country> getMax() {
		return countryRepository.findMax();
	}
	
	@Override
	@Async
	public List<Country> getMin() {
		return countryRepository.findMin();
	}
	
	@Override
	@Async
	public long distanceAverage(){
		return countryRepository.distanceAverage();
	}
	
}
