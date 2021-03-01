package com.melia.weather.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melia.weather.client.WeatherClient;
import com.melia.weather.model.City;
import com.melia.weather.model.ConsolidatedWeatherResponse;
import com.melia.weather.model.Weather;
import com.melia.weather.util.Utils;

@Service
public class WeatherForecastService {

	@Autowired
	private WeatherClient weatherClient;

	public String getCityWeather(String cityName, LocalDate dateTime) {
		LocalDate predictionDay = dateTime != null ? dateTime : LocalDate.now();
		if (isDateRangeValid(predictionDay)) {
			List<City> cities = weatherClient.getCity(cityName);
			if (Utils.isCollectionValid(cities) && Utils.isStringValid(cities.get(0).getWoeid())) {
				ConsolidatedWeatherResponse consolidatedWeather = weatherClient.getWeather(cities.get(0).getWoeid());
				if (consolidatedWeather != null && Utils.isCollectionValid(consolidatedWeather.getWeatherList())) {
					final String strPredictDay = Utils.formatLocalDateApi(predictionDay);
					Optional<Weather> weather = consolidatedWeather.getWeatherList().stream()
							.filter(w -> isSameDate(strPredictDay, w.getApplicableDate())).findFirst();
					return weather.orElse(new Weather()).getWeatherStateName();
				}
			}
		}
		return "";
	}

	private boolean isDateRangeValid(LocalDate localDate) {
		if (localDate != null) {
			long diffDays = ChronoUnit.DAYS.between(LocalDate.now(), localDate);
			return diffDays >= 0 && diffDays < 6;
		}

		return false;
	}

	private boolean isSameDate(String d1, String d2) {
		return d1 != null && d1.equals(d2);
	}

}
