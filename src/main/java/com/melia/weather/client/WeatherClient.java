package com.melia.weather.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.melia.weather.model.City;
import com.melia.weather.model.ConsolidatedWeatherResponse;

@FeignClient(name = "weather-service", url = "${metaweather.url}")
public interface WeatherClient {

	@GetMapping(value = "/search/")
	List<City> getCity(@RequestParam("query") String query);

	@GetMapping(value = "/{woeid}")
	ConsolidatedWeatherResponse getWeather(@PathVariable String woeid);
}
