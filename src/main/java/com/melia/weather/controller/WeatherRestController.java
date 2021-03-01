package com.melia.weather.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.melia.weather.service.WeatherForecastService;
import com.melia.weather.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherRestController {

	@Autowired
	private WeatherForecastService weatherForecastService;

	@GetMapping(value = { "/{cityName}/{date}", "/{cityName}" })
	public String getWeatherCity(@PathVariable String cityName, @PathVariable(required = false) String date) {
		LocalDate predictionDay = null;
		if (Utils.isStringValid(date)) {
			try {
				predictionDay = Utils.toLocalDate(date);
			} catch (DateTimeParseException e) {
				log.error(e.getMessage());
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect date", e);
			}
		}
		return weatherForecastService.getCityWeather(cityName, predictionDay);
	}

}
