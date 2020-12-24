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
import utils.Configuration;
import utils.Configuration.UrlAPI;

@RequestMapping("api/country")
@RestController
public class CountryController {
	ExternalApi externalApi = new ExternalApi(UrlAPI.IP);
	RestTemplate restTemplate = new RestTemplate();
	Response response;
	
	@Autowired
    private ObjectMapper objectMapper;
	 
	@Autowired
  	private ICountryService countryService;
	
	@PostMapping("/info")
	public String getRequest(@RequestBody String ipAddress) throws Exception {
		try {
			if(!Ip.isValid(ipAddress)) {
				response = new Response(false,"El ip ingresado es incorrecto",null);
				return objectMapper.writeValueAsString(response);
			}
			
			externalApi.setUrl(UrlAPI.IP);
			String ipInformation = restTemplate.getForObject(externalApi.getUrl() + ipAddress, String.class); 
			JSONObject  jsonIpResult = new JSONObject(ipInformation);
			
			if(jsonIpResult.getString(Configuration.NAME_PROPERTY_COUNTRY_CODE).isEmpty()) {
				response = new Response(false,"El ip ingresado no se encontró",null);
				return objectMapper.writeValueAsString(response);
			}
			
			Ip ip = new Ip(ipAddress, new Country());
			ip.getCountry().setAlpha3Code(jsonIpResult.getString(Configuration.NAME_PROPERTY_COUNTRY_CODE));
			return getInfoCountry(ip);
		}catch(Exception e){
			response = new Response(false,"Ocurrió un error al obtener la información de la dirección IP.",null);
			return objectMapper.writeValueAsString(response);
		}
		
	}
	
	public String getInfoCountry(Ip ip) throws Exception {
		try {
			Country country = countryService.updateCountry(ip.getCountry().getAlpha3Code());
			if(country == null) {
				externalApi.setUrl(UrlAPI.COUNTRY);
				String informationCountry = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().getAlpha3Code(), String.class);
				country = new Country(informationCountry);
			}
			ip.setCountry(country);
			return calculateRateUSD(ip); 
		}catch(Exception e) {
			response = new Response(false,"Ocurrió un error al obtener la información del país.",null);
			return objectMapper.writeValueAsString(response);
		}
	}
	
	public String calculateRateUSD(Ip ip) throws JsonProcessingException, ParseException, JSONException {
		try {
			externalApi.setUrl(UrlAPI.RATE);
			String informationExchangeRate = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().createStringAllCurrencies(), String.class); 
			JSONObject  jsonExchangeRatesResult = new JSONObject(informationExchangeRate); 
			ip.getCountry().setAllRates(jsonExchangeRatesResult);
			countryService.saveCountry(ip.getCountry());
			response = new Response(true,"",ip);
			return objectMapper.writeValueAsString(response);
		}catch(Exception e) {
			response = new Response(false,"Ocurrió un error al calcular el valor de cambio de moneda.",null);
			return objectMapper.writeValueAsString(response);
		}
		
	}
	
}
