package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.persistence.Embeddable;

import utils.Configuration;
@Embeddable 
public class Currency {
	private String code;
	private String name;
	private String symbol;
	private String dateExchange;
	private float exchangeRate;
	
	public Currency() {
		
	}
	
	public Currency(String code, String name, String symbol, String dateExchange, float exchangeRate) {
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

	public float getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(float exchangeRate) {
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
	
	public static float calculateRate(float rateOtherCurrency, float rateCurrentCurrency) {
		if(rateCurrentCurrency > 0)
			return rateOtherCurrency / rateCurrentCurrency;
		else
			return (float) -1;
	}
	
	public boolean shouldUpdate() {
		if(this.getDateExchange() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Configuration.FORMAT_DATETIME);
			String str = this.getDateExchange() + " 00:00";
			LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
			long diff = ChronoUnit.DAYS.between(dateTime, LocalDateTime.now());
			if(diff > 0) {
				return true;
			}	
		}
		
		return (this.getDateExchange() == null);
	}
	
}
