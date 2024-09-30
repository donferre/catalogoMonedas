package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZoneId;

public class FechasUtil {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	// Método para convertir LocalDate a String
	public static String formatDate(LocalDate date) {
		return date != null ? date.format(DATE_FORMATTER) : null;
	}

	// Método para convertir LocalDateTime a String
	public static String formatDateTime(LocalDateTime dateTime) {
		return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
	}

	// Método para convertir String a LocalDate
	public static LocalDate parseDate(String dateStr) {
		return dateStr != null && !dateStr.isEmpty() ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
	}

	// Método para convertir String a LocalDateTime
	public static LocalDateTime parseDateTime(String dateTimeStr) {
		return dateTimeStr != null && !dateTimeStr.isEmpty() ? LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER)
				: null;
	}

	// Método para convertir Date a String
	public static String formatDate(Date date) {
		return date != null ? DATE_FORMATTER.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
				: null;
	}

	// Método para convertir String a Date
	public static Date parseToDate(String dateStr) {
		LocalDate localDate = parseDate(dateStr);
		return localDate != null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
	}
}
