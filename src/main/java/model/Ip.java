package model;

import java.util.regex.Pattern;

import utils.Configuration;

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
	
	public static boolean isValid(String ipAddress) {
		String IP_REGEXP = Configuration.REGEX_IP_ADDRESS;
		Pattern IP_PATTERN = Pattern.compile(IP_REGEXP);
		return IP_PATTERN.matcher(ipAddress).matches();
	}
}
