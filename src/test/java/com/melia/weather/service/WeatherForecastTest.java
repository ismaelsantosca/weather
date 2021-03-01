package com.melia.weather.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melia.weather.client.WeatherClient;
import com.melia.weather.model.City;
import com.melia.weather.model.ConsolidatedWeatherResponse;
import com.melia.weather.model.Weather;
import com.melia.weather.util.Utils;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class WeatherForecastTest {

	@Mock
	private WeatherClient weatherClient;

	@InjectMocks
	private WeatherForecastService weatherForecastService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getCityWeather_shouldReturnStringWithWeather() {

		List<City> cities = new ArrayList<>();
		City city = City.builder().title("Madrid").locationType("City").woeid("766273").lattLong("40.420300,-3.705770").build();
		cities.add(city);

		LocalDate date = LocalDate.now().plusDays(3);
		String applicableDate = Utils.formatLocalDateApi(date);

		List<Weather> weathers = new ArrayList<>();
		Weather weather = Weather.builder().id(6507523071803392L).weatherStateName("Heavy Cloud").weatherStateAbbr("hc")
				.wind_direction_compass("ESE").created("2021-02-28T18:36:54.799619Z").applicableDate(applicableDate)
				.minTemp(5.195).maxTemp(15.06).theTemp(12.665).windSpeed(5.144732407733502).windDirection(115.79762873890843)
				.airPressure(1021.0).humidity(57D).visibility(13.86993706752565).predictability(71).build();
		weathers.add(weather);

		ConsolidatedWeatherResponse consolidatedWeatherResponse = new ConsolidatedWeatherResponse();
		consolidatedWeatherResponse.setWeatherList(weathers);

		when(weatherClient.getCity("Madrid")).thenReturn(cities);
		when(weatherClient.getWeather("766273")).thenReturn(consolidatedWeatherResponse);

		assertEquals(weatherForecastService.getCityWeather("Madrid", date), "Heavy Cloud");
	}

	@Test
	void getCityWeather_shouldReturnStringWithWeatherToday_whenDateIsNull() {

		List<City> cities = new ArrayList<>();
		City city = City.builder().title("Madrid").locationType("City").woeid("766273").lattLong("40.420300,-3.705770").build();
		cities.add(city);

		String strToday = Utils.formatLocalDateApi(LocalDate.now());

		List<Weather> weathers = new ArrayList<>();
		weathers.add(Weather.builder().id(6507523071803392L).weatherStateName("Light Rain").weatherStateAbbr("hc")
				.wind_direction_compass("ESE").created("2021-02-28T18:36:54.799619Z").applicableDate(strToday).minTemp(5.195)
				.maxTemp(15.06).theTemp(12.665).windSpeed(5.144732407733502).windDirection(115.79762873890843).airPressure(1021.0)
				.humidity(57D).visibility(13.86993706752565).predictability(71).build());

		weathers.add(Weather.builder().id(6507523071803392L).weatherStateName("Heavy Cloud").weatherStateAbbr("hc")
				.wind_direction_compass("ESE").created("2021-02-28T18:36:54.799619Z")
				.applicableDate(Utils.formatLocalDateApi(LocalDate.now().plusDays(1))).minTemp(5.195).maxTemp(15.06)
				.theTemp(12.665).windSpeed(5.144732407733502).windDirection(115.79762873890843).airPressure(1021.0).humidity(57D)
				.visibility(13.86993706752565).predictability(71).build());

		ConsolidatedWeatherResponse consolidatedWeatherResponse = new ConsolidatedWeatherResponse();
		consolidatedWeatherResponse.setWeatherList(weathers);

		when(weatherClient.getCity("Madrid")).thenReturn(cities);
		when(weatherClient.getWeather("766273")).thenReturn(consolidatedWeatherResponse);

		assertEquals(weatherForecastService.getCityWeather("Madrid", null), "Light Rain");
	}

	@Test
	void getCityWeather_shouldReturnStringEmpty_whenDateIsOutOfRange() {
		assertEquals(weatherForecastService.getCityWeather("Madrid", LocalDate.now().plusDays(8)), "");
		assertEquals(weatherForecastService.getCityWeather("Madrid", LocalDate.now().minusDays(1)), "");
	}

	@Test
	void getCityWeather_shouldReturnEmpty_whenStringCityIsNull() {
		assertEquals(weatherForecastService.getCityWeather(null, LocalDate.now().plusDays(3)), "");
	}

}
