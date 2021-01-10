package service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import model.Country;
import repository.CountryRepository;
import repository.SingletonCountry;

@Service
public class CountryService implements ICountryService {

	@Autowired
	private CountryRepository countryRepository;
	@Override
	public List<Country> getAllCountries() {
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		//Verfico si no esta en el objecto singleton o si se actualizó hace mas del tiempo configurado.
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
	public int updateRequestCount(String alpha3Code) {
		return countryRepository.updateRequestCount(alpha3Code);
	}
	
	@Override
	public Country getCountryByAlphaCode(String alpha3Code) {
		Country country = new Country();
		country = countryRepository.findById(alpha3Code).orElse(null);
	    return country;
	}

	@Override
	public Country getMax() {
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		//Verfico si no esta en el objecto singleton o si se actualizó hace mas del tiempo configurado.
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
	public Country getMin() {
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		//Verfico si no esta en el objecto singleton o si se actualizó hace mas del tiempo configurado.
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
	public long distanceAverage(){
		SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
		//Verfico si no esta en el objecto singleton o si se actualizó hace mas del tiempo configurado.
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
