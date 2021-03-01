package com.melia.weather.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.melia.weather.service.WeatherForecastService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class WeatherRestControllerTest {

	@Mock
	private WeatherForecastService weatherForecastService;

	@InjectMocks
	private WeatherRestController weatherRestController;

	@Test
	void getWeatherCity_shouldReturnStringWithWeather() {
		String city = "Madrid";
		LocalDate localDate = null;
		String strDate = null;
		when(weatherForecastService.getCityWeather(city, localDate)).thenReturn("Heavy Rain");
		assertEquals(weatherRestController.getWeatherCity(city, strDate), "Heavy Rain");
	}

	@Test
	void getWeatherCity_shouldReturnNotFoundError_whenIncorrectFormatStringDate() {
		String city = "Madrid";
		String strDate = "03-21-2021";
		try {
			weatherRestController.getWeatherCity(city, strDate);
		} catch (ResponseStatusException e) {
			assertEquals(e.getRawStatusCode(), HttpStatus.NOT_FOUND.value());
		}
	}

}
