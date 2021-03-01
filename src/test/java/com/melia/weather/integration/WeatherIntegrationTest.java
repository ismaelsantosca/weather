package com.melia.weather.integration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.melia.weather.client.CityMock;
import com.melia.weather.client.WeatherMock;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 9561)
@AutoConfigureMockMvc
class WeatherIntegrationTest {

	@Autowired
	private WireMockServer wireMockServer;

	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	void setUp() throws IOException {
		CityMock.setupMockCityResponse(wireMockServer);
		WeatherMock.setupMockWeatherResponse(wireMockServer);
	}

	@Test
	public void whenGetWeather_thenWeatherShouldBeReturned() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/weather/Madrid")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mvcResult.getResponse().getContentAsString(), "Heavy Rain");
	}

	@Test
	public void whenGetWeatherWithIncorrectFormatDate_thenNotFoundExceptionShouldBeReturned() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/weather/Madrid/23-23-21")).andExpect(status().is(404))
				.andReturn();

		assertEquals(mvcResult.getResponse().getContentAsString(), "");
	}

}
