package api;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Country;
import model.Ip;
import model.Response;
import service.ICountryService;
import utils.ExternalApi;
import utils.IpAddressValidator;
import utils.Configuration.UrlAPI;


@RequestMapping("api")
@RestController
public class CountryController {
	
	ExternalApi externalApi = new ExternalApi(UrlAPI.IP);
	RestTemplate restTemplate = new RestTemplate();
	Response response;
	
	@Autowired
    private ObjectMapper objectMapper;
	 
	@Autowired
  	private ICountryService countryService;
	
	@PostMapping
	public String getRequest(@RequestBody String ipAddress) throws JsonProcessingException, ParseException, JSONException {
		if(!IpAddressValidator.isValid(ipAddress)) {
			response = new Response(false,"El ip ingresado es incorrecto",null);
			return objectMapper.writeValueAsString(response);
		}
		
		externalApi.setUrl(UrlAPI.IP);
		String ipInformation = restTemplate.getForObject(externalApi.getUrl() + ipAddress, String.class); 
		JSONObject  jsonIpResult = new JSONObject(ipInformation);
		
		if(jsonIpResult.getString("countryCode3").isEmpty()) {
			response = new Response(false,"El ip ingresado no se encontró",null);
			return objectMapper.writeValueAsString(response);
		}
		
		Country country = new Country();
		country.setAlpha3Code(jsonIpResult.getString("countryCode3"));
		Ip ip = new Ip(ipAddress, country);
		
		return getInfoCountry(ip);
	}
	
	public String getInfoCountry(Ip ip) throws JsonProcessingException, JSONException, ParseException {
		externalApi.setUrl(UrlAPI.COUNTRY);
		Country country = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().getAlpha3Code(), Country.class);
		ip.setCountry(country);
		countryService.saveCountry(ip.getCountry());
		//return calculateRateUSD(ip); 
		return objectMapper.writeValueAsString(ip);
	}
	
	public void calculateRateUSD(Ip ip) throws JsonProcessingException, ParseException, JSONException {
		/*externalApi.setUrl(UrlAPI.RATE);
		String informationExchangeRate = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().createStringAllCurrencies(), String.class); 
		JSONObject  jsonExchangeRatesResult = new JSONObject(informationExchangeRate); 
		ip.getCountry().setAllRates(jsonExchangeRatesResult);
		response = new Response(true,"",ip);
		
		return objectMapper.writeValueAsString(response);*/
	}
}
