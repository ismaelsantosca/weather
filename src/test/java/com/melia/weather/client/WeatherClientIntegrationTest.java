package com.melia.weather.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
class WeatherClientIntegrationTest {

	@Autowired
	private WireMockServer mockBooksService;

	@Autowired
	private WeatherClient weatherClient;

	@BeforeEach
	void setUp() throws IOException {
		CityMock.setupMockCityResponse(mockBooksService);
		WeatherMock.setupMockWeatherResponse(mockBooksService);
	}

	@Test
	public void whenGetCity_thenCityShouldBeReturned() {
		assertFalse(weatherClient.getCity("Madrid").isEmpty());
	}

	@Test
	public void whenGetCity_thenTheCorrectCityShouldBeReturned() {
		assertTrue(weatherClient.getCity("Madrid").stream().anyMatch(c -> c.getWoeid().equals("766273")));
	}

	@Test
	public void whenGetWeather_thenCityShouldBeReturned() {
		assertFalse(weatherClient.getWeather("766273").getWeatherList().isEmpty());
	}

	@Test
	public void whenGetWeather_thenTheCorrectWeatherShouldBeReturned() {
		assertTrue(weatherClient.getWeather("766273").getWeatherList().stream()
				.anyMatch(w -> w.getApplicableDate().equals("2021-03-01") && w.getWeatherStateName().equals("Heavy Rain")));
	}

}
