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
import utils.ExternalApi;
import utils.IpAddressValidator;
import utils.ExternalApi.UrlAPI;


@RequestMapping("api")
@RestController
public class CountryController {
	
	ExternalApi externalApi = new ExternalApi(UrlAPI.IP);
	RestTemplate restTemplate = new RestTemplate();
	Response response;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@PostMapping
	public String getRequest(@RequestBody String ipAddress) throws JsonProcessingException, ParseException, JSONException {

		if(!IpAddressValidator.isValid(ipAddress)) {
			response = new Response(false,"El ip ingresado es incorrecto",null);
			return objectMapper.writeValueAsString(response);
		}
		
		externalApi.setUrl(UrlAPI.IP);
		Ip ipResult = restTemplate.getForObject(externalApi.getUrl() + ipAddress, Ip.class); 
		
		if(ipResult.getCountryCode3() == "") {
			response = new Response(false,"El ip ingresado no se encontró",null);
			return objectMapper.writeValueAsString(response);
		}
		
		ipResult.setIp(ipAddress);
		return getInfoCountry(ipResult);
	}
	
	public String getInfoCountry(Ip ip) throws JsonProcessingException, JSONException, ParseException {
		externalApi.setUrl(UrlAPI.COUNTRY);
		Country country = restTemplate.getForObject(externalApi.getUrl() + ip.getCountryCode3(), Country.class);
		ip.setCountry(country);
		return calculateRateUSD(ip); 
	}
	
	public String calculateRateUSD(Ip ip) throws JsonProcessingException, ParseException, JSONException {
		externalApi.setUrl(UrlAPI.RATE);
		String rate = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().getAllCurrencies(), String.class); 
		JSONObject  jsonRatesResult = new JSONObject(rate); 
		ip.getCountry().setAllRates(jsonRatesResult);
		response = new Response(true,"",ip);
		return objectMapper.writeValueAsString(response);
	}
}
