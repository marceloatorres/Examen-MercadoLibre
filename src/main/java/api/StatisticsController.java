package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Country;
import service.ICountryService;

@RequestMapping("api/stats")
@RestController
public class StatisticsController {
	@Autowired
  	private ICountryService countryService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@PostMapping
	public String getRequest() throws JsonProcessingException {
		List<Country> countries = countryService.getAllCountries();
		return objectMapper.writeValueAsString(countries);
	}
	

	@PostMapping("/average")
	public String average() throws JsonProcessingException {
		return objectMapper.writeValueAsString(countryService.distanceAverage());
	}
	
	@PostMapping("/max")
	public String max() throws JsonProcessingException {
		return objectMapper.writeValueAsString(countryService.getMax());
	}
	
	@PostMapping("/min")
	public String min() throws JsonProcessingException {
		return objectMapper.writeValueAsString(countryService.getMin());
	}
}
