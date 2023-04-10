package seedu.todolist.logic;

import org.junit.jupiter.api.Test;
import seedu.todolist.constants.Formats;
import seedu.todolist.exception.InvalidDateException;
import seedu.todolist.exception.InvalidDurationException;
import seedu.todolist.exception.InvalidEmailFormatException;
import seedu.todolist.exception.InvalidIdException;
import seedu.todolist.exception.InvalidPriorityException;
import seedu.todolist.exception.PassedDateException;
import seedu.todolist.model.Priority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ParserUtilTest {
    @Test
    void parseIdTest() {
        // Invalid ids should throw an exception
        final String invalidIds = "2.5 abc";
        assertThrows(InvalidIdException.class, () -> ParserUtil.parseId(invalidIds));

        // Valid ids get parsed successfully
        final String validIds = "3 17 84725";
        final HashSet<Integer> validIdHashSet = new HashSet<>(Arrays.asList(3, 17, 84725));
        try {
            assertEquals(ParserUtil.parseId(validIds), validIdHashSet);
        } catch (InvalidIdException e) {
            fail("Valid id was not successfully parsed.");
        }
    }

    @Test
    void parseDeadlineTest() {
        // Invalid dates should throw an exception
        final String[] invalidDeadlines = {"30/02/2023 16:40", "lalala", "10-50-2032 22:06", ""};
        for (String invalidDeadline : invalidDeadlines) {
            assertThrows(InvalidDateException.class, () -> ParserUtil.parseDeadline(invalidDeadline));
        }

        // Valid dates get parsed successfully
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final LocalDateTime[] validDeadlines = {now.plusDays(1), now.plusYears(10)};
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(Formats.TIME_IN_1);
        try {
            for (LocalDateTime validDeadline : validDeadlines) {
                assertEquals(ParserUtil.parseDeadline(validDeadline.format(inputFormatter)), validDeadline);
            }
        } catch (InvalidDateException e) {
            fail("Valid deadline was not successfully parsed.");
        } catch (PassedDateException e) {
            fail("Valid dateline was not successfully parsed.");
        }
    }

    @Test
    void parseEmailTest() {
        // Invalid emails should throw an exception
        final String[] invalidEmails = {"a@b@c@d.com", "example.com", "hello", "", "where@how"};
        for (String invalidEmail : invalidEmails) {
            assertThrows(InvalidEmailFormatException.class, () -> ParserUtil.parseEmail(invalidEmail));
        }

        // Valid emails get parsed successfully
        final String[] validEmails = {"xyz@xyz.com", "Idk@lalala.com", "legit@gmail.com"};
        try {
            for (String validEmail : validEmails) {
                assertEquals(ParserUtil.parseEmail(validEmail), validEmail);
            }
        } catch (InvalidEmailFormatException e) {
            fail("Valid email address was not successfully parsed.");
        }
    }

    @Test
    void parseRepeatTimesTest() {
        // Invalid repeat durations should throw an exception
        final String[] invalidDurations = {"$^8g", "1.36", "vb 2s"};
        for (String invalidDuration : invalidDurations) {
            assertThrows(InvalidDurationException.class,
                    () -> ParserUtil.parseRepeatTimes(invalidDuration, LocalDateTime.now()));
        }

        // If repeat duration is not provided, it should default to 0
        try {
            assertEquals(ParserUtil.parseRepeatTimes(null, LocalDateTime.now()), 0);
        } catch (InvalidDateException | InvalidDurationException e) {
            fail("Missing repeat duration was not automatically parsed as 0.");
        }

        // Valid repeat durations get parsed successfully
        final int[] validDurations = {3, 17, 84725};
        try {
            for (int validDuration : validDurations) {
                assertEquals(ParserUtil.parseRepeatTimes(Integer.toString(validDuration),
                        LocalDateTime.now()), validDuration);
            }
        } catch (InvalidDateException | InvalidDurationException e) {
            fail("Valid repeat duration was not successfully parsed.");
        }

        // Missing deadline should throw an exception
        for (int validDuration : validDurations) {
            assertThrows(InvalidDateException.class,
                    () -> ParserUtil.parseRepeatTimes(Integer.toString(validDuration), null));
        }
    }

    @Test
    void parsePriorityTest() {
        // Invalid priority levels should throw an exception
        final String[] invalidPriorities = {"4", "1.2", "hello", "?]|"};
        for (String invalidPriority : invalidPriorities) {
            assertThrows(InvalidPriorityException.class, () -> ParserUtil.parsePriority(invalidPriority));
        }

        try {
            // No priority provided gets converted to priority level None
            assertEquals(ParserUtil.parsePriority(null), Priority.NONE);

            // Valid priority levels get parsed successfully
            assertEquals(ParserUtil.parsePriority("0"), Priority.NONE);
            assertEquals(ParserUtil.parsePriority("1"), Priority.LOW);
            assertEquals(ParserUtil.parsePriority("2"), Priority.MEDIUM);
            assertEquals(ParserUtil.parsePriority("3"), Priority.HIGH);
        } catch (InvalidPriorityException e) {
            fail("Valid priority level was not successfully parsed.");
        }
    }
}
