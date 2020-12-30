package utils;
public class ExternalApi {
	private String url;
	
	public ExternalApi(UrlAPI url) {
		this.url = selectUrl(url);
	}
	
	public String selectUrl(UrlAPI url) {
		switch(url) {
			case IP:
				return Configuration.URL_API_IP;
			case COUNTRY:
				return Configuration.URL_API_COUNTRY;
			case RATE:
				return Configuration.URL_API_RATE_EXCHANGE;
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
