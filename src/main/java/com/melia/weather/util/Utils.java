package com.melia.weather.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	private static final DateTimeFormatter formatterApi = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

	public static boolean isStringValid(String str) {
		return str != null && str.length() > 0;
	}

	public static boolean isCollectionValid(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static LocalDate toLocalDate(String strDate) throws DateTimeParseException {
		if (Utils.isStringValid(strDate)) {
			return LocalDate.parse(strDate, formatter);
		}
		return null;
	}

	public static String formatLocalDateApi(LocalDate localDate) {
		if (localDate != null) {
			try {
				return localDate.format(formatterApi);
			} catch (DateTimeParseException exception) {
				log.error(exception.getMessage());
			}
		}

		return null;
	}
}
