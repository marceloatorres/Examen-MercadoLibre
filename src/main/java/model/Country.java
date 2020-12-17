package model;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
public class Country {

	private String name;
	private String capital	;
	private List<String> timezones;
	private String flag;
	private List<Language> languages;
	private List<Currency> currencies;
	private List<Integer> latlng;
	private String alpha2Code;
	private String alpha3Code;
	
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
	public List<Integer> getLatlng() {
		return latlng;
	}
	public void setLatlng(List<Integer> latlng) {
		this.latlng = latlng;
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
	
	public String getAllCurrencies() {
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
		Double priceUSD = jsonRatesResult.getJSONObject("rates").getDouble("USD");
	     for(int i = 0; i < this.getCurrencies().size(); i++) {
	    	 Double priceCurrentCurrency = (double) -1;
	    	 if(jsonRatesResult.getJSONObject("rates").has(this.getCurrencies().get(i).getCode())) {
		    	 priceCurrentCurrency = jsonRatesResult.getJSONObject("rates").getDouble(this.getCurrencies().get(i).getCode());
	    	 }
	    	 this.getCurrencies().get(i).setPriceUSD(priceUSD,priceCurrentCurrency);
	     }
	}
	
}
