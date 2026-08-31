package spoon.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import spoon.exception.InvalidFormatException;

class DateFormatTest {

    @Nested
    class ParseTests {

        @Test
        void parse_dateOnly_returnsMidnightWithIncludeTimeFalse() throws InvalidFormatException {
            DateFormat.ParseResult result = DateFormat.parse("01/01/0001");

            assertEquals(LocalDateTime.of(1, 1, 1, 0, 0), result.dateTime());
            assertFalse(result.includeTime());
        }

        @Test
        void parse_dateTime_returnsExactTimeWithIncludeTimeTrue() throws InvalidFormatException {
            DateFormat.ParseResult result = DateFormat.parse("01/01/0001 0101");

            assertEquals(LocalDateTime.of(1, 1, 1, 1, 1), result.dateTime());
            assertTrue(result.includeTime());
        }

        @Test
        void parse_withLeadingAndTrailingWhitespace_parsesSuccessfully() throws InvalidFormatException {
            DateFormat.ParseResult result = DateFormat.parse("   01/01/0001 0101   ");

            assertEquals(LocalDateTime.of(1, 1, 1, 1, 1), result.dateTime());
            assertTrue(result.includeTime());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "0001-01-01",               // ISO format (yyyy-MM-dd)
                "01-01-0001",               // Hyphenated
                "01/01/01",                 // 2-digit year
                "Jan 01 0001",              // Output format
                "Jan 01 0001, 1:01pm",      // Output format with
                "01/12/0001 1:01am",        // Invalid time syntax
                "32/01/0001",               // Out-of-bounds day
                "01/13/0001",               // Out-of-bounds month
                "invalid-date",             // Text input
                ""                          // Empty string
        })
        void parse_invalidFormats_throwsInvalidFormatException(String invalidInput) {
            assertThrows(InvalidFormatException.class, () -> DateFormat.parse(invalidInput));
        }
    }

    @Nested
    class ToDisplayTests {

        @Test
        void toDisplay_parseResultWithoutTime_formatsAsDateOnly() {
            DateFormat.ParseResult result = new DateFormat.ParseResult(
                    LocalDateTime.of(1, 1, 1, 0, 0), false
            );

            assertEquals("Jan 01 0001", DateFormat.toDisplay(result));
        }

        @Test
        void toDisplay_parseResultWithTime_formatsWithTimeAndAmPm() {
            DateFormat.ParseResult morningResult = new DateFormat.ParseResult(
                    LocalDateTime.of(1, 1, 1, 1, 1), true
            );
            DateFormat.ParseResult eveningResult = new DateFormat.ParseResult(
                    LocalDateTime.of(1, 1, 1, 13, 1), true
            );

            assertEquals("Jan 01 0001, 1:01am", DateFormat.toDisplay(morningResult));
            assertEquals("Jan 01 0001, 1:01pm", DateFormat.toDisplay(eveningResult));
        }

        @Test
        void toDisplay_localDateDirectly_formatsAsDateOnly() {
            LocalDate date = LocalDate.of(1, 1, 1);
            assertEquals("Jan 01 0001", DateFormat.toDisplay(date));
        }
    }

    @Nested
    class ToStorageTests {

        @Test
        void toStorage_withoutTime_returnsDateInputPattern() {
            DateFormat.ParseResult result = new DateFormat.ParseResult(
                    LocalDateTime.of(1, 1, 1, 1, 1), false
            );

            assertEquals("01/01/0001", DateFormat.toStorage(result));
        }

        @Test
        void toStorage_withTime_returnsDateTimeInputPattern() {
            DateFormat.ParseResult result = new DateFormat.ParseResult(
                    LocalDateTime.of(1, 1, 1, 1, 1), true
            );

            assertEquals("01/01/0001 0101", DateFormat.toStorage(result));
        }
    }

    @Nested
    class DateEvaluationTests {

        @Test
        void isDueOn_sameDayDifferentTime_returnsTrue() {
            LocalDateTime deadline = LocalDateTime.of(1, 1, 1, 1, 1);
            LocalDate targetDate = LocalDate.of(1, 1, 1);

            assertTrue(DateFormat.isDueOn(deadline, targetDate));
        }

        @Test
        void isDueOn_differentDay_returnsFalse() {
            LocalDateTime deadline = LocalDateTime.of(1, 1, 1, 1, 1);
            LocalDate targetDate = LocalDate.of(1, 1, 2);

            assertFalse(DateFormat.isDueOn(deadline, targetDate));
        }

        @Test
        void isOccurringOn_targetDateInsideRange_returnsTrue() {
            LocalDateTime start = LocalDateTime.of(1, 1, 10, 1, 1);
            LocalDateTime end = LocalDateTime.of(1, 1, 20, 1, 1);

            assertTrue(DateFormat.isOccurringOn(start, end, LocalDate.of(1, 1, 10))); // Start boundary
            assertTrue(DateFormat.isOccurringOn(start, end, LocalDate.of(1, 1, 15))); // Middle
            assertTrue(DateFormat.isOccurringOn(start, end, LocalDate.of(1, 1, 20))); // End boundary
        }

        @Test
        void isOccurringOn_targetDateOutsideRange_returnsFalse() {
            LocalDateTime start = LocalDateTime.of(1, 1, 10, 1, 1);
            LocalDateTime end = LocalDateTime.of(1, 1, 20, 1, 1);

            assertFalse(DateFormat.isOccurringOn(start, end, LocalDate.of(1, 1, 9)));  // Before
            assertFalse(DateFormat.isOccurringOn(start, end, LocalDate.of(1, 1, 21))); // After
        }

        @Test
        void isDueBy_targetDateOnOrAfterTaskDate_returnsTrue() {
            LocalDateTime taskDate = LocalDateTime.of(1, 1, 10, 1, 1);

            assertTrue(DateFormat.isDueBy(taskDate, LocalDate.of(1, 1, 10))); // Exact day
            assertTrue(DateFormat.isDueBy(taskDate, LocalDate.of(1, 1, 21))); // After
        }

        @Test
        void isDueBy_targetDateBeforeTaskDate_returnsFalse() {
            LocalDateTime taskDate = LocalDateTime.of(1, 1, 10, 1, 1);

            assertFalse(DateFormat.isDueBy(taskDate, LocalDate.of(1, 1, 9))); // Before
        }
    }
}