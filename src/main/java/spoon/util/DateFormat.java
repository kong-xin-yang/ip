package spoon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import spoon.exception.InvalidFormatException;

/**
 * Handles the formatting of the date (+ time) user inputs and display outputs.
 */
public class DateFormat {
    /**
     * Stores both the date (+ time) and a boolean representing if time is included as a single data structure.
     */
    public record ParseResult(LocalDateTime dateTime, boolean includeTime) {}

    // Date input format handlers
    private static final DateTimeFormatter DATE_INPUT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_INPUT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    // Date output format handlers
    private static final DateTimeFormatter DATE_DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATE_TIME_DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Parses a string into a LocalDate or LocalDateTime.
     * Supports both "dd/MM/yyyy" and "dd/MM/yyyy HHmm".
     *
     * @return a ParseResult containing both the date (+ time) and a boolean representing if time is included.
     */
    public static ParseResult parse(String input) throws InvalidFormatException {
        // Parsing with date and time
        try {
            LocalDateTime dateTime = LocalDateTime.parse(input.trim(), DATE_TIME_INPUT);
            return new ParseResult(dateTime, true);
        } catch (DateTimeParseException ignored) {
            // Fall through
        }
        // Parsing with date only
        try {
            LocalDate date = LocalDate.parse(input.trim(), DATE_INPUT);
            return new ParseResult(date.atStartOfDay(), false);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("date/time (as dd/MM/yyyy or dd/MM/yyyy HHmm)");
        }
    }

    /**
     * Converts a LocalDateTime to a string for display.
     *
     * @param parsedDateTime date (+ time) and a boolean representing if time is included.
     * @return date (+ time) converted to string, in display format.
     */
    public static String toDisplay(ParseResult parsedDateTime) {
        if (parsedDateTime.includeTime()) {
            return parsedDateTime.dateTime().format(DATE_TIME_DISPLAY);
        }
        return parsedDateTime.dateTime().format(DATE_DISPLAY);
    }

    /**
     * Converts a LocalDateTime to a string for storage.
     *
     * @param parsedDateTime date (+ time) and a boolean representing if time is included.
     * @return date (+ time) converted to string, in storage (input) format.
     */
    public static String toStorage(ParseResult parsedDateTime) {
        if (parsedDateTime.includeTime()) {
            return parsedDateTime.dateTime().format(DATE_TIME_INPUT);
        }
        return parsedDateTime.dateTime().format(DATE_INPUT);
    }
}
