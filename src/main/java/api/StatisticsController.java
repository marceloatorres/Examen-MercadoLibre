package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Country;
import model.Response;
import service.ICountryService;

@RequestMapping("api/stats")
@RestController
public class StatisticsController {
	@Autowired
  	private ICountryService countryService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private Response response;
	
	@PostMapping
	public String getRequest() throws JsonProcessingException {
		try {
			List<Country> countries = countryService.getAllCountries();
			return objectMapper.writeValueAsString(countries);
		} catch(Exception e) {
			response = new Response(false,"Ocurrió un error obtener datos de los países", null);
			return objectMapper.writeValueAsString(response);
		}
	}
	

	@PostMapping("/average")
	public String average() throws JsonProcessingException {
		try {
			return objectMapper.writeValueAsString(countryService.distanceAverage());
		}catch(Exception e) {
			response = new Response(false,"Ocurrió un error obtener el promedio de distancias", null);
			return objectMapper.writeValueAsString(response);
		}
	}
	
	@PostMapping("/max")
	public String max() throws JsonProcessingException {
		try {
			return objectMapper.writeValueAsString(countryService.getMax());
		}catch(Exception e){
			response = new Response(false,"Ocurrió un error obtener el país con máxima distancia", null);
			return objectMapper.writeValueAsString(response);	
		}
	}
	
	@PostMapping("/min")
	public String min() throws JsonProcessingException {
		try {
			return objectMapper.writeValueAsString(countryService.getMin());
		}catch(Exception e){
			response = new Response(false,"Ocurrió un error obtener el país con mínima distancia", null);
			return objectMapper.writeValueAsString(response);	
		}
	}
}
