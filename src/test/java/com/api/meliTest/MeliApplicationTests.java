package com.api.meliTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import model.Country;
import model.Currency;
import model.Ip;
import utils.Configuration;

@SpringBootTest
public class MeliApplicationTests {

	@Test
	public void isNecesaryUpdateCurrencyObject() {
		Currency currency = new Currency("ARS","Peso Argentino","$","2020-12-26", (float)0.1);
		assertThat(currency.shouldUpdate()).isEqualTo(true);
	}
	
	@Test
	public void isNecesaryUpdateCurrencyObjectToday() {
		String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Currency currency = new Currency("ARS","Peso Argentino","$",now, (float)0.1);
		assertThat(currency.shouldUpdate()).isEqualTo(false);
	}

	@Test
	public void checkCalculateExchageRate() {
		float result = (float) Currency.calculateRate((float)1.2, (float)3.0);
		assertThat(result).isEqualTo((float)0.4);
	}
	
	@Test
	public void checkDistanceToSameCountry() {
		Country country = new Country();
		country.setLat(Configuration.LAT_COUNTRY_ORIGIN);
		country.setLng(Configuration.LNG_COUNTRY_ORIGIN);
		
		country.setDistanceCalculated();
		assertThat(country.getDistanceToOtherPlace()).isEqualTo(0);
	}
	
	@Test
	public void checkDistanceToOtherCountry() {
		Country country = new Country();
		country.setLat(-30);
		country.setLng(-35);
		
		country.setDistanceCalculated();
		assertThat(country.getDistanceToOtherPlace()).isEqualTo(2249);
	}
	
	@Test
	public void checkValidIp() {
		assertThat(Ip.isValid("200.5.1.1")).isEqualTo(true);
	}
	
	@Test
	public void checkInvalidIp() {
		assertThat(Ip.isValid("256.30.6.9")).isEqualTo(false);
	}
	
}
