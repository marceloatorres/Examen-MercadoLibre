package model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
	
	/*@ElementCollection
	@CollectionTable(
	        name="timeZones",
	        joinColumns=@JoinColumn(name="alpha3Code")
	)
	private List<TimeZone> timezones;
	private List<Language> languages;
	private List<Currency> currencies;*/
	//@Column(name = "lat", nullable = false)
	//private double lat;
	//@Column(name = "lgn", nullable = false)
	//private double lgn;
	
	/*public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLgn() {
		return lgn;
	}
	public void setLgn(double lgn) {
		this.lgn = lgn;
	}*/
	public int getDistanceToArgentina() {
		return distanceToArgentina;
	}
	public void setDistanceToArgentina(int distanceToArgentina) {
		this.distanceToArgentina = distanceToArgentina;
	}

	/*public List<TimeZone> getTimezones() {
		return timezones;
	}
	public void setTimezones(List<TimeZone> timezones) {
		this.timezones = timezones;
	}*/
	
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
	/*public List<Language> getLanguages() {
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
	public List<Double> getLatlng() {
		return latlng;
	}
	public void setLatlng(List<Double> latlng) {
		this.latlng = latlng;
	}*/
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
	
	/*public String createStringAllCurrencies() {
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
		Double priceUSD = jsonRatesResult.getJSONObject("rates").getDouble(Configuration.CODERATETOEXCHANGE);
	     for(int i = 0; i < this.getCurrencies().size(); i++) {
	    	 Double priceCurrentCurrency = (double) -1;
	    	 if(jsonRatesResult.getJSONObject("rates").has(this.getCurrencies().get(i).getCode())) {
		    	 priceCurrentCurrency = jsonRatesResult.getJSONObject("rates").getDouble(this.getCurrencies().get(i).getCode());
	    	 }
	    	 ExchangeRate rate = new ExchangeRate(jsonRatesResult.getString("date"), ExchangeRate.getExchangeRate(priceUSD,priceCurrentCurrency));
	    	 this.getCurrencies().get(i).setExchangeRate(rate);
	     }
	}
	
	public static double distance(double lat2, double lon2, double el1, double el2) {
		double lat1 = Configuration.LATCOUNTRYORIGIN; 
		double lon1 = Configuration.LNGCOUNTRYORIGIN;
	    final int R = Configuration.EARTHRADIUS;
	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; 
	    double height = el1 - el2;
	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
	    return Math.sqrt(distance);
	}*/
	
}
