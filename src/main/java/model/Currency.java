package model;

import javax.persistence.Embeddable;
@Embeddable 
public class Currency {
	private String code;
	private String name;
	private String symbol;
	private String dateExchange;
	private Double exchangeRate;
	
	public Currency() {
	}
	
	public Currency(String code, String name, String symbol, String dateExchange, Double exchangeRate) {
	super();
	this.code = code;
	this.name = name;
	this.symbol = symbol;
	this.dateExchange = dateExchange;
	this.exchangeRate = exchangeRate;
}

	public String getDateExchange() {
		return dateExchange;
	}

	public void setDateExchange(String dateExchange) {
		this.dateExchange = dateExchange;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public static double calculateRate(Double priceUSD, Double priceCurrency) {
		if(priceCurrency > 0)
			return priceUSD / priceCurrency;
		else
			return (double) -1;
	}
	
}
