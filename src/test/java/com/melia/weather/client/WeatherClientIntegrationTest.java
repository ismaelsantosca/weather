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
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 9561)
class WeatherClientIntegrationTest {

	@Autowired
	private WireMockServer wireMockServer;

	@Autowired
	private WeatherClient weatherClient;

	@BeforeEach
	void setUp() throws IOException {
		CityMock.setupMockCityResponse(wireMockServer);
		WeatherMock.setupMockWeatherResponse(wireMockServer);
	}

	@Test
	void whenGetCity_thenCityShouldBeReturned() {
		assertFalse(weatherClient.getCity("Madrid").isEmpty());
	}

	@Test
	void whenGetCity_thenTheCorrectCityShouldBeReturned() {
		assertTrue(weatherClient.getCity("Madrid").stream().anyMatch(c -> c.getWoeid().equals("766273")));
	}

	@Test
	void whenGetWeather_thenCityShouldBeReturned() {
		assertFalse(weatherClient.getWeather("766273").getWeatherList().isEmpty());
	}

	@Test
	void whenGetWeather_thenTheCorrectWeatherShouldBeReturned() {
		assertTrue(weatherClient.getWeather("766273").getWeatherList().stream()
				.anyMatch(w -> w.getApplicableDate().equals("2021-03-01") && w.getWeatherStateName().equals("Heavy Rain")));
	}

}
