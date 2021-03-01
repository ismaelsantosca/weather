package com.melia.weather.client;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class CityMock {

	public static void setupMockCityResponse(WireMockServer mockService) throws IOException {
		mockService.stubFor(WireMock.get(WireMock.urlMatching("/search/\\?query=([A-Z-a-z]*)"))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(copyToString(
								CityMock.class.getClassLoader().getResourceAsStream("payload/get-city-response.json"),
								defaultCharset()))));
	}
}
