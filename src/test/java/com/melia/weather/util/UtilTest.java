package com.melia.weather.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void isStringValid_shouldReturnTrue() {
		String test = "test";
		assertTrue(Utils.isStringValid(test));
	}

	@Test
	void isStringValid_shouldReturnFalse_whenStringIsEmpty() {
		String test = "";
		assertFalse(Utils.isStringValid(test));
	}

	@Test
	void isStringValidWith_shouldReturnFalse_whenStringIsNull() {
		String test = null;
		assertFalse(Utils.isStringValid(test));
	}

	@Test
	void isCollectionValid_shouldReturnTrue() {
		List<String> strings = Arrays.asList("test1", "test2");
		assertTrue(Utils.isCollectionValid(strings));
	}

	@Test
	void isCollectionValid_shouldReturnFalse_whenCollectionIsEmpty() {
		List<String> strings = new ArrayList<>();
		assertFalse(Utils.isCollectionValid(strings));
	}

	@Test
	void isCollectionValid_shouldReturnFalse_whenCollectionIsNull() {
		List<String> strings = null;
		assertFalse(Utils.isCollectionValid(strings));
	}

	@Test
	void toLocalDate_shouldReturnLocalDate() {
		String strDate = "03-03-21";
		LocalDate localDate = LocalDate.of(2021, 3, 3);
		assertTrue(Utils.toLocalDate(strDate).isEqual(localDate));
	}

	@Test
	void toLocalDate_shouldReturnDateTimeParseException_whenIncorrectFormatStringDate() {
		String strDate = "03-21-2021";
		try {
			Utils.toLocalDate(strDate);
		} catch (DateTimeParseException ex) {
			assertNotNull(ex);
		}
	}

	@Test
	void toLocalDate_shouldReturnNull_whenStringDateIsNull() {
		String strDate = null;
		assertNull(Utils.toLocalDate(strDate));
	}

	@Test
	void formatLocalDateApi_shouldReturnStringDate() {
		String strDate = "2021-03-03";
		LocalDate localDate = LocalDate.of(2021, 3, 3);
		assertEquals(Utils.formatLocalDateApi(localDate), strDate);
	}

	@Test
	void formatLocalDateApi_shouldReturnNull_whenLocalDateIsNull() {
		LocalDate localDate = null;
		assertNull(Utils.formatLocalDateApi(localDate));
	}

}
