package model;

public class Currency {
	private String code;
	private String name;
	private String symbol;
	private ExchangeRate exchangeRate; 

	public String getCode() {
		return code;
	}
	public ExchangeRate getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(ExchangeRate rate) {
		this.exchangeRate = rate;
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
	
}
