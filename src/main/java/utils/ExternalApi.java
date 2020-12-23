package utils;

import utils.Configuration.UrlAPI;

public class ExternalApi {

	private String url;
	
	public ExternalApi(UrlAPI url) {
		this.url = selectUrl(url);
	}
	
	public String selectUrl(UrlAPI url) {
		switch(url) {
		case IP:
			return "https://api.ip2country.info/ip?";
		case COUNTRY:
			return "https://restcountries.eu/rest/v2/alpha/";
		case RATE:
			return "http://data.fixer.io/api/latest?access_key=86bda81848f4b198301d4391c3a80227&symbols=" + Configuration.CURRENCY_CODE_TO_EXCHANGE + ",";
		default:
			return "";
		}
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(UrlAPI url) {
		this.url = selectUrl(url);
	}

}
