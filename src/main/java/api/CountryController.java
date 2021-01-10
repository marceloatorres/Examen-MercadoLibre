package api;

import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Country;
import model.Ip;
import repository.SingletonCountry;
import response.FactoryResponse;
import response.IResponse;
import service.ICountryService;
import utils.ExternalApi;
import utils.TypeResponse;
import utils.Configuration;
import utils.UrlAPI;

@RequestMapping("api/country")
@RestController
public class CountryController {
	ExternalApi externalApi = new ExternalApi(UrlAPI.IP);
	RestTemplate restTemplate = new RestTemplate();
	FactoryResponse factoryResponse = new FactoryResponse();
	private ReentrantLock mutexToPersist = new ReentrantLock();
	
	@Autowired
    private ObjectMapper objectMapper;
	 
	@Autowired
  	private ICountryService countryService;
	
	@PostMapping("/info")
	@Async("taskExecutor")
	public String getIpInformation(@RequestBody String ipAddress) throws Exception {
		IResponse response;
		try {
			if(!Ip.isValid(ipAddress)) {
				response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"La dirección ip ingresada es incorrecta.",null); 
				return objectMapper.writeValueAsString(response);
			}
			
			//Se busca en API externa los datos de la ip ingresada
			externalApi.setUrl(UrlAPI.IP);
			String ipInformation = restTemplate.getForObject(externalApi.getUrl() + ipAddress, String.class); 
			JSONObject  jsonIpResult = new JSONObject(ipInformation);
			
			//Verifico si no recibí datos de la Api externa
			if(jsonIpResult.getString(Configuration.NAME_PROPERTY_COUNTRY_CODE).isEmpty()) {
				response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"La dirección ip ingresada no se encontró.",null);
				return objectMapper.writeValueAsString(response);
			}
			
			Ip ip = new Ip(ipAddress, new Country());
			ip.getCountry().setAlpha3Code(jsonIpResult.getString(Configuration.NAME_PROPERTY_COUNTRY_CODE));
			return getInfoCountry(ip);
		}catch(Exception e){
			response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"Ocurrió un error al obtener información de la dirección ip ingresada.",null);
			return objectMapper.writeValueAsString(response);
		}
		
	}
	
	public String getInfoCountry(Ip ip) throws Exception {
		IResponse response;
		try {
			//Antes informacion del pais en la API externa lo busco primero en mi objeto singleto sino en mi bd
			SingletonCountry singletonCountry = SingletonCountry.getSingletonCountry();
			Country country = null;
			if(singletonCountry.getAllCountries() != null)
				country = singletonCountry.findCountryByAlpha3Code(ip.getCountry().getAlpha3Code());
			
			if(country == null)
				country = countryService.getCountryByAlphaCode(ip.getCountry().getAlpha3Code());
					
			boolean countryFound = (country!= null);
			if(!countryFound) {
				externalApi.setUrl(UrlAPI.COUNTRY);
				String informationCountry = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().getAlpha3Code(), String.class);
				country = new Country(informationCountry);
			}
			
			ip.setCountry(country);
			return calculateRateUSD(ip,countryFound); 
		}catch(Exception e) {
			response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"Ocurrió un error al obtener información del país.",null);
			return objectMapper.writeValueAsString(response);
		}
	}
	
	public String calculateRateUSD(Ip ip, boolean countryFound) throws JsonProcessingException{
		IResponse response;
		boolean updateRateSuccess = true;
		try {
			//Verifico que sea necesario actualizar las tarifas de cambio, si ya se consulto para el mismo dia utilizo el singleton
			if(ip.getCountry().shouldUpdateRateCurrency()) {
				externalApi.setUrl(UrlAPI.RATE);
				String informationExchangeRate = restTemplate.getForObject(externalApi.getUrl() + ip.getCountry().createStringAllCurrencies(), String.class); 
				JSONObject  jsonExchangeRatesResult = new JSONObject(informationExchangeRate); 
				updateRateSuccess = ip.getCountry().setAllRates(jsonExchangeRatesResult);
			}
			
			if(!updateRateSuccess) {
				response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"Ocurrió un error al actualizar el valor de cambio de moneda.",null);
				return objectMapper.writeValueAsString(response);
			}
			
			if(persistToDataBase(countryFound, ip.getCountry())) {
				response = factoryResponse.getObjectResponse(TypeResponse.IP,true,"",ip);
			}else {
				response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"Ocurrió un error en la persistencia de datos.",null);
			}
			
			return objectMapper.writeValueAsString(response);
		}catch(Exception e) {
			response = factoryResponse.getObjectResponse(TypeResponse.IP,false,"Ocurrió un error al obtener el valor de cambio de moneda.",null);
			return objectMapper.writeValueAsString(response);
		}
	}
	
	public boolean persistToDataBase(boolean countryFound, Country country) {
		try {
			//Verificó si el país ya existía para actualizar, sino creo el nuevo registro.
			if(countryFound) {
				countryService.updateRequestCount(country.getAlpha3Code());
			}else {
				mutexToPersist.lock();
				Country countryAux;
				countryAux = countryService.getCountryByAlphaCode(country.getAlpha3Code());
						
				if(countryAux == null)
					countryService.saveCountry(country);
				else
					countryService.updateRequestCount(country.getAlpha3Code());
				
				mutexToPersist.unlock();
			}
			return true;
		}catch (Exception e){
			if(mutexToPersist.isLocked())
				mutexToPersist.unlock();
			return false;
		}
	}
	
}
