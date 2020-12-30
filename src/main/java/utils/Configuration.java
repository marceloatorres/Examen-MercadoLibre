package utils;

public class Configuration {
	public static String CURRENCY_CODE_TO_EXCHANGE = "USD";
	public static String URL_API_IP = "https://api.ip2country.info/ip?";
	public static String URL_API_COUNTRY = "https://restcountries.eu/rest/v2/alpha/";
	public static String URL_API_RATE_EXCHANGE = "http://data.fixer.io/api/latest?access_key=86bda81848f4b198301d4391c3a80227&symbols=" + Configuration.CURRENCY_CODE_TO_EXCHANGE + ",";
	public static String NAME_PROPERTY_CURRENCY_RATE= "rate";
	public static String NAME_PROPERTY_COUNTRY_CODE= "countryCode3";
	public static float  LAT_COUNTRY_ORIGIN = (float)-34.6083;
	public static float LNG_COUNTRY_ORIGIN = (float)-58.3712;
	public static int EARTH_RADIUS = 6371;
	public static int SECONDS_TO_UPDATE_SINGLETON = 10;
	public static String REGEX_IP_ADDRESS = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])"  + 
											"\\." + "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])" + 
											"\\." + "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])" + 
											"\\." + "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
	public static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm";
}
