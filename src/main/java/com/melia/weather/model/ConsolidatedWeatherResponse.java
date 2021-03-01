package com.melia.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsolidatedWeatherResponse {

	@JsonProperty("consolidated_weather")
	private List<Weather> weatherList;
}
