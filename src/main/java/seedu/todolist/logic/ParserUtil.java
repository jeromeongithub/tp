package seedu.todolist.logic;

import seedu.todolist.constants.Flags;
import seedu.todolist.constants.Formats;
import seedu.todolist.exception.InvalidBooleanException;
import seedu.todolist.exception.InvalidDateException;
import seedu.todolist.exception.InvalidDurationException;
import seedu.todolist.exception.InvalidEmailFormatException;
import seedu.todolist.exception.InvalidFrequencyException;
import seedu.todolist.exception.InvalidIdException;
import seedu.todolist.exception.InvalidPriorityException;
import seedu.todolist.exception.InvalidSortException;
import seedu.todolist.exception.PassedDateException;
import seedu.todolist.exception.ToDoListException;
import seedu.todolist.model.Priority;
import seedu.todolist.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Utility class for parsing arguments that are in the form of strings.
 */
public class ParserUtil {
    private ParserUtil() {
    }

    //@@author jeromeongithub
    /**
     * Parses the id string into an integer. Does not check if id is in task list.
     *
     * @param idList The string containing the list of ids.
     * @return The ids, as a HashSet of integers.
     * @throws InvalidIdException If any id cannot be parsed to an integer.
     */
    public static HashSet<Integer> parseId(String idList) throws InvalidIdException {
        if (idList.isEmpty()) {
            return new HashSet<>();
        }

        HashSet<Integer> idHashSet = new HashSet<>();
        for (String idString : idList.split(" ")) {
            try {
                int id = Integer.parseInt(idString);
                assert id >= 0 : "Parser should catch any negative values";
                idHashSet.add(id);
            } catch (NumberFormatException e) {
                throw new InvalidIdException(idString);
            }
        }
        return idHashSet;
    }

    //@@author RuiShengGit
    /**
     * Parses the priority string into an integer from 1 to 3, if it exists.
     * If the priority is null because it is not provided, it will default to 1.
     *
     * @param priorityString The priority string.
     * @return The priority, as an integer.
     * @throws InvalidPriorityException If the priority cannot be parsed to an integer, or if it is not from 1 to 3.
     */
    public static Priority parsePriority(String priorityString) throws InvalidPriorityException {
        if (priorityString == null) {
            return Priority.NONE;
        }

        try {
            int priority = Integer.parseInt(priorityString);
            switch (priority) {
            case 0:
                return Priority.NONE;
            case 1:
                return Priority.LOW;
            case 2:
                return Priority.MEDIUM;
            case 3:
                return Priority.HIGH;
            default:
                throw new InvalidPriorityException(priorityString);
            }
        } catch (NumberFormatException e) {
            throw new InvalidPriorityException(priorityString);
        }
    }

    //@@author clement559
    /**
     * Parses the deadline string into a LocalDateTime object.
     * The deadline is allowed to be null as it is an optional parameter.
     *
     * @param deadline The deadline string.
     * @param afterThisDate The date-time that the parsed deadline cannot be before.
     *                      For adding tasks or editing deadlines, that will be the current date-time.
     * @return A LocalDateTime object that contains the date and time in the string,
     *         if it was not null, null otherwise.
     * @throws InvalidDateException If the string is not in a valid date time format,
     *                              or if the parsed date is before the current time.
     */
    public static LocalDateTime parseDeadline(String deadline, LocalDateTime afterThisDate)
            throws InvalidDateException, PassedDateException {
        if (deadline == null) {
            return null;
        }

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(Formats.TIME_IN_1 + Formats.TIME_IN_2)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime date = LocalDateTime.parse(deadline, inputFormatter);

            if (!date.isAfter(afterThisDate)) {
                throw new PassedDateException(deadline);
            }
            return date;
        } catch (DateTimeParseException e) {
            throw new InvalidDateException(deadline);
        }
    }

    public static LocalDateTime parseDeadline(String deadline) throws InvalidDateException, PassedDateException {
        return parseDeadline(deadline, LocalDateTime.now());
    }

    //@@author RuiShengGit
    /**
     * Parses the email string, checking that is a valid email address.
     * The email is allowed to be null as it is an optional parameter.
     *
     * @param email The email address string.
     * @return The email address, if it was not null, null otherwise.
     * @throws InvalidEmailFormatException If the email address is invalid.
     */
    public static String parseEmail(String email) throws InvalidEmailFormatException {
        if (email == null) {
            return null;
        }

        String mandatoryUsername = "^[a-zA-Z0-9_+&*-]+(?:\\.";
        String mandatoryAddressSign = "[a-zA-Z0-9_+&*-]+)*@";
        String mandatoryDomain = "(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$";
        String emailRegex = mandatoryUsername + mandatoryAddressSign + mandatoryDomain;
        if (!Pattern.compile(emailRegex).matcher(email).matches()) {
            throw new InvalidEmailFormatException(email);
        }
        return email;
    }

    //@@author ERJUNZE
    /**
     * Parses the string of multiple tags into a hashset of tag strings.
     * Tags are separated from each other by a space.
     *
     * @param tags The string containing multiple tags.
     * @return A hashset containing the extracted tags.
     */
    public static TreeSet<String> parseTags(String tags) {
        if (tags == null || tags.isEmpty()) {
            return new TreeSet<>();
        }
        return new TreeSet<>(Arrays.asList(tags.split(" ")));
    }

    /**
     * Counts the number of trues in an array of boolean values.
     *
     * @param bools The boolean array.
     * @return The number of trues in the boolean array.
     */
    public static int countTrue(boolean... bools) {
        int count = 0;
        for (boolean b : bools) {
            count += b ? 1 : 0;
        }
        return count;
    }

    //@@author clement559
    /**
     * Parses the repeat duration of the task, which is in number of weeks.
     * A repeat duration can be specified only if deadline is not null.
     * If the repeat duration is null because it is not provided, it will default to 0.
     *
     * @param repeatTimes The repeat duration string.
     * @param deadline The deadline of the task.
     * @return The repeat duration of the task as an integer, if the deadline has been provided.
     * @throws InvalidDateException If the deadline is null.
     * @throws InvalidDurationException If the repeat duration cannot be parsed to an integer.
     */
    public static int parseRepeatTimes(String repeatTimes, LocalDateTime deadline)
            throws InvalidDateException, InvalidDurationException {
        if (repeatTimes == null) {
            return 0;
        }

        if (deadline == null) {
            throw new InvalidDateException("A deadline must be provided to use the repeat option.");
        }

        try {
            return Integer.parseInt(repeatTimes);
        } catch (NumberFormatException e) {
            throw new InvalidDurationException(repeatTimes);
        }
    }

    /**
     * Parses the frequency to be set in the config.
     *
     * @param frequencyString The frequency, as a string.
     * @return The frequency, as an integer, if it was not null, -1 otherwise.
     * @throws InvalidFrequencyException If the priority cannot be parsed to an integer.
     */
    public static int parseFrequency(String frequencyString, int minAllowed) throws InvalidFrequencyException {
        if (frequencyString == null) {
            return -1;
        }

        try {
            int frequency = Integer.parseInt(frequencyString);
            assert frequency >= 0 : "Parser should catch any negative values";
            if (frequency < minAllowed) {
                throw new InvalidFrequencyException(frequencyString);
            }
            return frequency;
        } catch (NumberFormatException e) {
            throw new InvalidFrequencyException(frequencyString);
        }
    }

    /**
     * Parses a boolean argument.
     * "1", "y", "yes", "t", "true" are considered true, while "0", "n", "no", "f", "false" are considered false.
     *
     * @param boolString The boolean argument.
     * @return Whether the argument was recognized as true or false.
     * @throws InvalidBooleanException If the argument is not considered as either true or false.
     */
    public static boolean parseBoolean(String boolString) throws InvalidBooleanException {
        switch (boolString.toLowerCase()) {
        case "1":
        case "y":
        case "yes":
        case "t":
        case "true":
            return true;
        case "0":
        case "n":
        case "no":
        case "f":
        case "false":
            return false;
        default:
            throw new InvalidBooleanException(boolString);
        }
    }

    /**
     * Parses the boolean filter options, which can either be true or false.
     *
     * @param args Hashmap containing filter flags and their arguments, if any.
     * @return Null, if no filters are provided, otherwise, a predicate matching all the given filters.
     * @throws ToDoListException If the argument for any filter is invalid.
     */
    private static Predicate<Task> parseBooleanFilters(HashMap<Flags, String> args) throws ToDoListException {
        Predicate<Task> predicate = task -> true;
        if (args.containsKey(Flags.FILTER_DONE)) {
            predicate = predicate.and(parseBoolean(args.get(Flags.FILTER_DONE))
                    ? Task.isDonePredicate() : Predicate.not((Task.isDonePredicate())));
        }
        if (args.containsKey(Flags.FILTER_OVERDUE)) {
            predicate = predicate.and(parseBoolean(args.get(Flags.FILTER_OVERDUE))
                    ? Task.isOverdue() : Predicate.not(Task.isOverdue()));
        }
        if (args.containsKey(Flags.REPEAT)) {
            predicate = predicate.and(parseBoolean(args.get(Flags.REPEAT))
                    ? Task.isRepeating() : Predicate.not(Task.isRepeating()));
        }
        return predicate;
    }

    //@@author KedrianLoh
    /**
     * Parses the filter options.
     *
     * @param args Hashmap containing filter flags and their arguments, if any.
     * @return Null, if no filters are provided, otherwise, a predicate matching all the given filters.
     * @throws ToDoListException If the argument for any filter is invalid.
     */
    public static Predicate<Task> parseFilter(HashMap<Flags, String> args) throws ToDoListException {
        if (Collections.disjoint(args.keySet(), Flags.FILTER_FLAGS)) {
            // No filter flags are present
            return null;
        }

        Predicate<Task> predicate = parseBooleanFilters(args);
        // Parse the non-boolean filters, which take in arguments
        if (args.containsKey(Flags.DESCRIPTION)) {
            predicate = predicate.and(Task.matchesDescription(args.get(Flags.DESCRIPTION)));
        }
        if (args.containsKey(Flags.EMAIL)) {
            predicate = predicate.and(Task.matchesEmail(parseEmail(args.get(Flags.EMAIL))));
        }
        if (args.containsKey(Flags.FILTER_BEFORE)) {
            predicate = predicate.and(Task.beforeDeadline(
                    parseDeadline(args.get(Flags.FILTER_BEFORE), LocalDateTime.MIN)));
        }
        if (args.containsKey(Flags.FILTER_AFTER)) {
            predicate = predicate.and(Task.afterDeadline(
                    parseDeadline(args.get(Flags.FILTER_AFTER), LocalDateTime.MIN)));
        }
        if (args.containsKey(Flags.TAG)) {
            predicate = predicate.and(Task.matchesTags(parseTags(args.get(Flags.TAG))));
        }
        if (args.containsKey(Flags.PRIORITY)) {
            predicate = predicate.and(Task.matchesPriority(parsePriority(args.get(Flags.PRIORITY))));
        }
        return predicate;
    }

    /**
     * Parses the sort option.
     *
     * @param sortType The sort option as a string.
     * @return The comparator matching the sort option.
     * @throws InvalidSortException If the string did not match any sort option.
     */
    public static Comparator<Task> parseSort(String sortType) throws InvalidSortException {
        switch (sortType) {
        case "due":
            return Task.deadlineComparator;
        case "prio":
            return Task.priorityComparator;
        case "desc":
            return Task.descriptionComparator;
        case "done":
            // Sort overdue tasks before non-overdue incomplete tasks
            return Task.doneComparator.thenComparing(Task.deadlineComparator);
        default:
            throw new InvalidSortException(sortType);
        }
    }
}
