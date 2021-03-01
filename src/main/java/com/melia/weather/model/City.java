package com.melia.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class City {

	private String title;

	@JsonProperty("location_type")
	private String locationType;

	private String woeid;

	@JsonProperty("latt_long")
	private String lattLong;
}
