package com.melia.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

	private Long id;

	@JsonProperty("weather_state_name")
	private String weatherStateName;

	@JsonProperty("weather_state_abbr")
	private String weatherStateAbbr;

	@JsonProperty("wind_direction_compass")
	private String wind_direction_compass;

	private String created;

	@JsonProperty("applicable_date")
	private String applicableDate;

	@JsonProperty("min_temp")
	private Double minTemp;

	@JsonProperty("max_temp")
	private Double maxTemp;

	@JsonProperty("the_temp")
	private Double theTemp;

	@JsonProperty("wind_speed")
	private Double windSpeed;

	@JsonProperty("wind_direction")
	private Double windDirection;

	@JsonProperty("air_pressure")
	private Double airPressure;

	private Double humidity;

	private Double visibility;

	private Integer predictability;

}
