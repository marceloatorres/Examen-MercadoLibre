package utils;

public class Configuration {
	public enum UrlAPI {
		  IP,
		  COUNTRY,
		  RATE
		}
	
	public static String CURRENCY_CODE_TO_EXCHANGE = "USD";
	public static String NAME_PROPERTY_CURRENCY_RATE= "rate";
	public static String NAME_PROPERTY_COUNTRY_CODE= "countryCode3";
	public static int LAT_COUNTRY_ORIGIN = -34;
	public static int LNG_COUNTRY_ORIGIN = -64;
	public static int EARTH_RADIUS = 6371;
	public static int SECONDS_TO_UPDATE_SINGLETON = 10;
	public static String REGEX_IP_ADDRESS = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])"  + "\\." + "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])" + "\\." + "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])" + "\\." + "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
}
