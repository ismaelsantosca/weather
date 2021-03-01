package com.melia.weather.client;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class WeatherMock {

	public static void setupMockWeatherResponse(WireMockServer mockService) throws IOException {
		mockService.stubFor(WireMock.get(WireMock.urlMatching("/[0-9]*"))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(copyToString(
								WeatherMock.class.getClassLoader().getResourceAsStream("payload/get-weather-response.json"),
								defaultCharset()))));
	}
}
