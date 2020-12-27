package service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import model.Country;
import repository.CountryRepository;

import repository.SingletonCountry;

@Service
public class CountryService implements ICountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	@Async
	public List<Country> getAllCountries() {
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		if(singletonCountry.getAllCountries() == null || singletonCountry.shouldUpdate(singletonCountry.getLastUpdateAllCountries())) {
			try {
				singletonCountry.setAllCountries(countryRepository.findAll(Sort.by(Sort.Direction.DESC, "requestCount")));
				singletonCountry.calculateAll();
			} catch (Exception e) {
				return null;
			}
		}
		return singletonCountry.getAllCountries();
	}

	@Override
	public Country saveCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Country updateCountry(Country country) {
		Country updatedCountry = new Country();
			if(country == null) {
				return null;
			}
			updatedCountry = countryRepository.save(country);
		return updatedCountry;
	}
	
	@Override
	public Country getCountryByAlphaCode(String alpha3Code) {
		Country country = new Country();
		country = countryRepository.findById(alpha3Code).orElse(null);
	    return country;
	}

	@Override
	@Async
	public Country getMax() {
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		if(singletonCountry.getMaxDistanceCountry() == null || singletonCountry.shouldUpdate(singletonCountry.getLastUpdateMaxDistance())) {
			try {
				singletonCountry.setMaxDistanceCountry(countryRepository.findMax());
				singletonCountry.setLastUpdateMaxDistance(LocalDateTime.now());
			} catch (Exception e) {
				return null;
			}
		}
		
		return singletonCountry.getMaxDistanceCountry();
	}
	
	@Override
	@Async
	public Country getMin() {
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		//Verfico si esta en el objecto singleton menos del tiempo configurado
		if(singletonCountry.getMinDistanceCountry() == null || singletonCountry.shouldUpdate(singletonCountry.getLastUpdateMinDistance())) {
			try {
				singletonCountry.setMinDistanceCountry(countryRepository.findMin());
				singletonCountry.setLastUpdateMinDistance(LocalDateTime.now());
			} catch (Exception e) {
				return null;
			}
		}
		
		return singletonCountry.getMinDistanceCountry();
	}
	
	@Override
	@Async
	public long distanceAverage(){
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		if(singletonCountry.getAverageDistanceCountries() == 0 || singletonCountry.shouldUpdate(singletonCountry.getLastUpdateAverage())) {
			try {
				singletonCountry.setAverageDistanceCountries(countryRepository.distanceAverage());
				singletonCountry.setLastUpdateAverage(LocalDateTime.now());
			} catch (Exception e) {
				return 0;
			}
		}
		return singletonCountry.getAverageDistanceCountries();
	}
	
}
