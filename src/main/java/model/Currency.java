package model;

public class Currency {
	private String code;
	private String name;
	private String symbol;
	private Double priceUSD;
	// aca por ahí me sirve ponerle una class rate asi sé el precio y la fecha de cotización
	
	public Double getPriceUSD() {
		return priceUSD;
	}
	
	public void setPriceUSD(Double priceUSD, Double priceCurrency ) {
		if(priceCurrency > 0)
			this.priceUSD =  priceUSD / priceCurrency;
		else
			this.priceUSD =  (double) -1;
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
	
}
