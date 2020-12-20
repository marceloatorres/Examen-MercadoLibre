package model;

public class Ip {
	private String ip;
	private Country country;

	public String getIp() {
		return ip;
	}
	
	public Ip(String ip, Country country) {
		super();
		this.ip = ip;
		this.country = country;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
}
