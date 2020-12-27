package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Country;
import response.FactoryResponse;
import response.IResponse;
import service.ICountryService;
import utils.TypeResponse;

@RequestMapping("api/stats")
@RestController
public class StatisticsController {
	@Autowired
  	private ICountryService countryService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	FactoryResponse factoryResponse = new FactoryResponse();
	
	@PostMapping
	public String getRequest() throws JsonProcessingException {
		IResponse response;
		try {
			List<Country> countries = countryService.getAllCountries();
			response = factoryResponse.getObjectResponse(TypeResponse.STATISTIC,true,"", countries);
		} catch(Exception e) {
			response = factoryResponse.getObjectResponse(TypeResponse.STATISTIC,false,"Ocurrió un error obtener datos de los países", null);
		}
		return objectMapper.writeValueAsString(response);
	}

	@PostMapping("/average")
	public String average() throws JsonProcessingException {
		IResponse response;
		try {
			Long average = countryService.distanceAverage();
			response = factoryResponse.getObjectResponse(TypeResponse.LONG,true,"", average);
		}catch(Exception e) {
			response = factoryResponse.getObjectResponse(TypeResponse.LONG,false,"Ocurrió un error obtener el promedio de distancias", null);
		}
		return objectMapper.writeValueAsString(response);
	}
	
	@PostMapping("/max")
	public String max() throws JsonProcessingException {
		IResponse response;
		try {
			Country countryMaxDistance = countryService.getMax();
			response = factoryResponse.getObjectResponse(TypeResponse.COUNTRY,true,"", countryMaxDistance);
		}catch(Exception e){
			response = factoryResponse.getObjectResponse(TypeResponse.COUNTRY,false,"Ocurrió un error obtener el país con máxima distancia", null);
		}
		return objectMapper.writeValueAsString(response);	
	}
	
	@PostMapping("/min")
	public String min() throws JsonProcessingException {
		IResponse response;
		try {
			Country countryMaxDistance = countryService.getMin();
			response =  factoryResponse.getObjectResponse(TypeResponse.COUNTRY,true,"", countryMaxDistance);
		}catch(Exception e){
			response =  factoryResponse.getObjectResponse(TypeResponse.COUNTRY,false,"Ocurrió un error obtener el país con mínima distancia", null);
		}
		return objectMapper.writeValueAsString(response);
	}
}
