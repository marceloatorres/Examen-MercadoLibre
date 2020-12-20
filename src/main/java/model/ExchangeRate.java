package model;


public class ExchangeRate {
	private String date;
	private Double rate;
	
	public ExchangeRate(String date, Double priceUSD) {
		this.date = date;
		this.rate = priceUSD;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double priceUSD, Double priceCurrency ) {
		if(priceCurrency > 0)
			this.rate =  priceUSD / priceCurrency;
		else
			this.rate =  (double) -1;
	}
	
	public static double getExchangeRate(Double priceUSD, Double priceCurrency) {
		if(priceCurrency > 0)
			return priceUSD / priceCurrency;
		else
			return (double) -1;
	}
}

