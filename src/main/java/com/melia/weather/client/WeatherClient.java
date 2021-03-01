package com.melia.weather.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.melia.weather.model.City;
import com.melia.weather.model.ConsolidatedWeatherResponse;

@FeignClient(name = "weather-service", url = "${metaweather.url}")
public interface WeatherClient {

	@RequestMapping(method = RequestMethod.GET, value = "/search/")
	List<City> getCity(@RequestParam("query") String query);

	@RequestMapping(method = RequestMethod.GET, value = "/{woeid}")
	ConsolidatedWeatherResponse getWeather(@PathVariable String woeid);
}
