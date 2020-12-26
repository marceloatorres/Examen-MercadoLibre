package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.google.gson.Gson;
import utils.Configuration;
@Entity
@Table(name = "countries")
@EntityListeners(AuditingEntityListener.class)
public class Country {
	@Id
	@Column(name = "alpha3Code", nullable = false, unique = true)
	private String alpha3Code;
	
	@Column(name = "alpha2Code", nullable = false)
	private String alpha2Code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "capital", nullable = false)
	private String capital;
	
	@Column(name = "flag", nullable = false)
	private String flag;
	
	@Column(name = "distanceToArgentina", nullable = false)
	private int distanceToArgentina;
	
	@ElementCollection
	private List<String> timezones;
	
	@ElementCollection
	private List<Language> languages;
	
	@ElementCollection
	private List<Currency> currencies;
	
	@Column(name = "lat", nullable = false)
	private double lat;
	
	@Column(name = "lng", nullable = false)
	private double lng;
	
	@Column(name = "requestCount", nullable = false)
	private int requestCount = 1;
	
	public Country() {
		
	}
	
	public Country(String json) throws JSONException {
		Gson gson = new Gson();
	    Country country = gson.fromJson(json.toString(), Country.class);
		this.alpha3Code = country.alpha3Code;
		this.alpha2Code = country.alpha2Code;
		this.name = country.name;
		this.capital = country.capital;
		this.flag = country.flag;
		this.distanceToArgentina = country.distanceToArgentina;
		this.timezones = country.timezones;
		this.languages = country.languages;
		this.currencies = country.currencies;
		JSONObject  jsonCountryResult = new JSONObject(json); 
		if(jsonCountryResult.has("latlng")) {
			JSONArray latlng = jsonCountryResult.getJSONArray("latlng");
			this.setLatLngAndCalculate(latlng.getDouble(0),latlng.getDouble(1)) ;
		}
	}
	
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public int getDistanceToArgentina() {
		return distanceToArgentina;
	}
	public void setDistanceToArgentina(int distanceToArgentina) {
		this.distanceToArgentina = distanceToArgentina;
	}

	public List<String> getTimezones() {
		return timezones;
	}
	
	public void setTimezones(List<String> timezones) {
		this.timezones = timezones;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCapital() {
		return capital;
	}
	
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public List<Language> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	
	public List<Currency> getCurrencies() {
		return currencies;
	}
	
	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	
	public String getAlpha2Code() {
		return alpha2Code;
	}
	
	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}
	
	public String getAlpha3Code() {
		return alpha3Code;
	}
	
	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}
	
	public void increaseRequestCount() {
		this.requestCount +=1;
	}
	
	public String createStringAllCurrencies() {
		String queryCurrencies = "";
	     for(int i = 0; i < this.getCurrencies().size(); i++) {
	    	 if(i > 0) {
	    		 queryCurrencies += ",";
	    	 }
	    	 queryCurrencies =  this.getCurrencies().get(i).getCode();
	     }
	     return queryCurrencies;
	}
	
	public void setAllRates(JSONObject jsonRatesResult) throws JSONException {
		Double priceUSD = jsonRatesResult.getJSONObject("rates").getDouble(Configuration.CURRENCY_CODE_TO_EXCHANGE);
	     for(int i = 0; i < this.getCurrencies().size(); i++) {
	    	 Double priceCurrentCurrency = (double) -1;
	    	 if(jsonRatesResult.getJSONObject("rates").has(this.getCurrencies().get(i).getCode())) {
		    	 priceCurrentCurrency = jsonRatesResult.getJSONObject("rates").getDouble(this.getCurrencies().get(i).getCode());
	    	 }
	    	 this.getCurrencies().get(i).setDateExchange(jsonRatesResult.getString("date"));
	    	 this.getCurrencies().get(i).setExchangeRate(Currency.calculateRate(priceUSD,priceCurrentCurrency));
	     }
	}
	
	public void setLatLngAndCalculate(double lat, double lng) {
		this.setLat(lat);
		this.setLng(lng);
		this.setDistanceCalculated();
	}
	
	public void setDistanceCalculated() {
	    double lat1 = Configuration.LAT_COUNTRY_ORIGIN; 
		double lng1 = Configuration.LNG_COUNTRY_ORIGIN;
	    double earthRadius = Configuration.EARTH_RADIUS; 
	    double dLat = Math.toRadians(this.getLat()-lat1);
	    double dLng = Math.toRadians(this.getLng()-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(this.getLat())) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    this.distanceToArgentina = (int)dist;
	}
	
	public int calcToAverage() {
		return this.distanceToArgentina * this.requestCount;
	}
	
	public boolean shouldUpdateRateCurrency(){
		boolean updateRate = false;
		if(this.getCurrencies() != null) {
			for(int i = 0 ; i < this.getCurrencies().size(); i++) {
				Currency currentCurrency = this.getCurrencies().get(i);
				if(currentCurrency.getDateExchange() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					String str = currentCurrency.getDateExchange() + " 00:00";
					LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
					long diff = ChronoUnit.DAYS.between(dateTime, LocalDateTime.now());
					if(diff > 0) {
						updateRate = true;
						break;
					}	
				}else {
					updateRate = true;
				}
			}
		}
		return updateRate;
	}
	
}
