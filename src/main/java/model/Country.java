package model;

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
	
	@Column(name = "distanceToOtherPlace", nullable = false)
	private int distanceToOtherPlace;
	
	@ElementCollection
	private List<String> timezones;
	
	@ElementCollection
	private List<Language> languages;
	
	@ElementCollection
	private List<Currency> currencies;
	
	@Column(name = "lat", nullable = false)
	private float lat;
	
	@Column(name = "lng", nullable = false)
	private float lng;
	
	@Column(name = "requestCount", nullable = false)
	private long requestCount = 1;
	
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
		this.distanceToOtherPlace = country.distanceToOtherPlace;
		this.timezones = country.timezones;
		this.languages = country.languages;
		this.currencies = country.currencies;
		JSONObject  jsonCountryResult = new JSONObject(json); 
		if(jsonCountryResult.has("latlng")) {
			JSONArray latlng = jsonCountryResult.getJSONArray("latlng");
			this.setLatLngAndCalculate((float)latlng.getDouble(0),(float)latlng.getDouble(1)) ;
		}
	}
	
	public long getRequestCount() {
		return requestCount;
	}
	
	public void setRequestCount(long requestCount) {
		this.requestCount = requestCount;
	}
	
	public float getLat() {
		return lat;
	}
	
	public void setLat(float lat) {
		this.lat = lat;
	}
	
	public float getLng() {
		return lng;
	}
	
	public void setLng(float lng) {
		this.lng = lng;
	}
	
	public int getDistanceToOtherPlace() {
		return distanceToOtherPlace;
	}
	public void setDistanceToOtherPlace(int distanceToOtherPlace) {
		this.distanceToOtherPlace = distanceToOtherPlace;
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
	
	public boolean setAllRates(JSONObject jsonRatesResult){
		try {
			float rateOtherCurrency = (float)jsonRatesResult.getJSONObject("rates").getDouble(Configuration.CURRENCY_CODE_TO_EXCHANGE);
		     for(int i = 0; i < this.getCurrencies().size(); i++) {
		    	 if(jsonRatesResult.getJSONObject("rates").has(this.getCurrencies().get(i).getCode())) {
		    		 float rateCurrentCurrency;
		    		 rateCurrentCurrency = (float)jsonRatesResult.getJSONObject("rates").getDouble(this.getCurrencies().get(i).getCode());
			    	 this.getCurrencies().get(i).setDateExchange(jsonRatesResult.getString("date"));
			    	 this.getCurrencies().get(i).setExchangeRate(Currency.calculateRate(rateOtherCurrency,rateCurrentCurrency));
		    	 }
		     }
		     return true;
		}catch (Exception e){
			return false;
		}
	}
	
	public void setLatLngAndCalculate(float lat, float lng) {
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

	    this.distanceToOtherPlace = (int)dist;
	}
	
	public long calcToAverage() {
		return this.distanceToOtherPlace * this.requestCount;
	}
	
	public boolean shouldUpdateRateCurrency(){
		boolean updateRate = false;
		if(this.getCurrencies() != null) {
			for(int i = 0 ; i < this.getCurrencies().size(); i++) {
				Currency currentCurrency = this.getCurrencies().get(i);
				if(currentCurrency.shouldUpdate()) {
					updateRate = true;
					break;
				}
			}
		}
		return updateRate;
	}
	
}
