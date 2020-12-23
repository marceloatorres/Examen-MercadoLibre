package repository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import model.Country;
import utils.Configuration;

public class SingletonCountry {
	private List<Country> allCountries;
	private Country maxDistanceCountry;
	private Country minDistanceCountry;
	private long averageDistanceCountries;
	private LocalDateTime  lastUpdateAllCountries;
	private LocalDateTime  lastUpdateMaxDistance;
	private LocalDateTime  lastUpdateMinDistance;
	private LocalDateTime  lastUpdateAverage;
	
	public static SingletonCountry countriesInMemory = null;
	 
	 public static  SingletonCountry getSingletonCountry() {
		 if (countriesInMemory == null) {		 
			 countriesInMemory = new SingletonCountry(null, null, null, 0);
		 }
		 	return countriesInMemory;
	 }
	 
	 private SingletonCountry(List<Country> allCountries,Country maxDistanceCountries,Country minDistanceCountries, long averageDistanceCountries){
		 this.allCountries = allCountries;
		 this.maxDistanceCountry = maxDistanceCountries;
		 this.minDistanceCountry = minDistanceCountries;
		 this.averageDistanceCountries = averageDistanceCountries;
		 this.lastUpdateAllCountries =  LocalDateTime.now();
		 this.lastUpdateMaxDistance =  LocalDateTime.now();
		 this.lastUpdateMinDistance =  LocalDateTime.now();
		 this.lastUpdateAverage =  LocalDateTime.now();
	 }
	 
	 public void setAllCountries(List<Country> allCountries) {
		this.allCountries = allCountries;
	 }

	 public List<Country> getAllCountries( ) {
			return this.allCountries;
	 }

	public Country getMaxDistanceCountry() {
		return maxDistanceCountry;
	}

	public void setMaxDistanceCountry(Country maxDistanceCountries) {
		this.maxDistanceCountry = maxDistanceCountries;
	}

	public Country getMinDistanceCountry() {
		return minDistanceCountry;
	}

	public void setMinDistanceCountry(Country minDistanceCountries) {
		this.minDistanceCountry = minDistanceCountries;
	}

	public long getAverageDistanceCountries() {
		return averageDistanceCountries;
	}

	public void setAverageDistanceCountries(long averageDistanceCountries) {
		this.averageDistanceCountries = averageDistanceCountries;
	}


	public LocalDateTime getLastUpdateAllCountries() {
		return lastUpdateAllCountries;
	}

	public void setLastUpdateAllCountries(LocalDateTime lastUpdateAllCountries) {
		this.lastUpdateAllCountries = lastUpdateAllCountries;
	}

	public LocalDateTime getLastUpdateMaxDistance() {
		return lastUpdateMaxDistance;
	}

	public void setLastUpdateMaxDistance(LocalDateTime lastUpdateMaxDistance) {
		this.lastUpdateMaxDistance = lastUpdateMaxDistance;
	}

	public LocalDateTime getLastUpdateMinDistance() {
		return lastUpdateMinDistance;
	}

	public void setLastUpdateMinDistance(LocalDateTime lastUpdateMinDistance) {
		this.lastUpdateMinDistance = lastUpdateMinDistance;
	}

	public LocalDateTime getLastUpdateAverage() {
		return lastUpdateAverage;
	}

	public void setLastUpdateAverage(LocalDateTime lastUpdateAverage) {
		this.lastUpdateAverage = lastUpdateAverage;
	}

	public boolean shouldUpdate(LocalDateTime dateToEvaluate) {
		LocalDateTime now = LocalDateTime.now();
		long diff = ChronoUnit.SECONDS.between(dateToEvaluate, now);
		return (diff > Configuration.SECONDS_TO_UPDATE_SINGLETON);
	}
	
	public void calcMaxDistance() {
		this.setMinDistanceCountry(this.getAllCountries()
			      .stream()
			      .max(Comparator.comparing(Country::getDistanceToArgentina))
			      .orElseThrow(NoSuchElementException::new));
	}
	
	public void calcMinDistance() {
		this.setMinDistanceCountry(this.getAllCountries()
			      .stream()
			      .min(Comparator.comparing(Country::getDistanceToArgentina))
			      .orElseThrow(NoSuchElementException::new));
	}
	
	public void calcAverage() {
		long TotalSum =	(long)this.getAllCountries()
			            .stream()
			            .mapToInt(Country::calcToAverage)
			            .sum() ;
		
		long totalRequestCount = (long)this.getAllCountries()
					            .stream()
					            .mapToInt(Country::getRequestCount)
					            .sum();
		this.setAverageDistanceCountries(TotalSum / totalRequestCount);
	}
	
	public void calculateAll() {
		this.calcMaxDistance();
		this.calcMinDistance();
		this.calcAverage();
		LocalDateTime now = LocalDateTime.now();
		this.setLastUpdateMaxDistance(now);
		this.setLastUpdateMinDistance(now);
		this.setLastUpdateAverage(now);
		this.setLastUpdateAllCountries(now);
	}
}
