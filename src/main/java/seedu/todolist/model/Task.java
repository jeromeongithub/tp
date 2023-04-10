package seedu.todolist.model;

import seedu.todolist.constants.Formats;
import seedu.todolist.logic.FormatterUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * A class representing a task to be done, with various attributes like deadlines and completion status.
 */
public class Task {
    //@@author KedrianLoh
    /**
     * Comparator for sorting the task list by deadline, with older deadlines first.
     * Tasks without deadlines are placed at the bottom.
     */
    public static Comparator<Task> deadlineComparator = (task1, task2) -> {
        if (task1.getDeadline() == null) {
            return 1;
        }
        if (task2.getDeadline() == null) {
            return -1;
        }
        return task1.deadline.compareTo(task2.deadline);
    };

    /**
     * Comparator for sorting the task list by description, in lexicographic order.
     */
    public static Comparator<Task> descriptionComparator = Comparator.comparing(task -> task.description);

    /**
     * Comparator for sorting the task list by completion status, with incomplete tasks first.
     */
    public static Comparator<Task> doneComparator = Comparator.comparing(task -> task.isDone);

    //@@author clement559
    /**
     * Comparator for sorting the task list by priority, with higher priority first.
     * Tasks without priority are placed at the bottom.
     */
    // Uses enum ordinal to sort
    public static Comparator<Task> priorityComparator = Comparator.comparing(task -> task.priority);

    //@@author jeromeongithub
    int id;
    private boolean isDone = false;
    private String description;
    private String email;
    private LocalDateTime deadline;
    private int repeatTimes;
    private TreeSet<String> tags;
    private Priority priority;

    public Task(int id, String description, LocalDateTime deadline, String email, TreeSet<String> tags,
                int repeatTimes, Priority priority) {
        this.id = id;
        this.description = description;
        this.email = email;
        this.deadline = deadline;
        this.tags = tags;
        this.repeatTimes = repeatTimes;
        this.priority = priority;
    }

    /**
     * Checks if this task is still valid after loading it from the save file.
     * Certain attributes if deleted or edited to invalid values will cause the task to be invalid.
     *
     * @return If this task is still valid.
     */
    public boolean isValid() {
        if (id < 1) {
            // Id must be positive
            return false;
        }
        if (description == null || description.isEmpty()) {
            // Description must exist
            return false;
        }
        if (repeatTimes < 0 || (repeatTimes > 0 && deadline == null)) {
            // Recurrence count must be positive, unless there is no deadline, then it must be 0
            return false;
        }
        // Tag list and priority level must exist
        return tags != null && priority != null;
    }

    //@@author KedrianLoh
    /**
     * Converts this task into its summarised string representation, with only some attributes listed.
     *
     * @return The summarised string representation of this task.
     */
    public String toString() {
        String descriptionString = description.length() > Formats.MAX_DESC_LEN_FOR_LIST
                ? description.substring(0, Formats.MAX_DESC_LEN_FOR_LIST) + "..." : description;
        String isDoneString = isDone ? "X" : (isDue() ? "!" : " ");
        String taskString = String.format(Formats.TASK_STRING, id, isDoneString,
                priority.toDisplayString(), descriptionString);
        if (deadline != null) {
            taskString += "[Due by: " + FormatterUtil.getDeadlineAsString(deadline) + "]";
        }
        return taskString;
    }

    /**
     * Converts this task into its full string representation, with all attributes that exist listed.
     *
     * @return The full string representation of this task.
     */
    public String getFullInfo() {
        StringJoiner infoString = new StringJoiner(System.lineSeparator());
        infoString.add("ID:           " + id);
        infoString.add("Description:  " + description);
        infoString.add("Completed:    " + (isDone ? "Yes" : (isDue() ? "Overdue" : "No")));
        if (priority != Priority.NONE) {
            infoString.add("Priority:     " + priority.toString());
        }
        if (deadline != null) {
            infoString.add("Due:          " + FormatterUtil.getDeadlineAsString(deadline));
        }
        if (email != null) {
            infoString.add("Email:        " + email);
        }
        if (!tags.isEmpty()) {
            infoString.add("Tags:         " + FormatterUtil.getTagsAsString(tags));
        }
        if (repeatTimes > 0) {
            infoString.add("Repeat times: " + repeatTimes);
        }
        return infoString.toString();
    }

    /**
     * Checks if a task is due, which means the current time is equal to or after its deadline.
     *
     * @return If a task is due.
     */
    public boolean isDue() {
        return deadline != null && !deadline.isAfter(LocalDateTime.now());
    }

    //@@author jeromeongithub
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public TreeSet<String> getTags() {
        return tags;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public Priority getPriority() {
        return priority;
    }

    public String setDone(boolean isDone) {
        this.isDone = isDone;
        return toString();
    }

    public String setDescription(String description) {
        this.description = description;
        return toString();
    }

    public String setEmail(String email) {
        this.email = email;
        return toString();
    }

    public String setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        if (deadline == null) {
            repeatTimes = 0;
        }
        return toString();
    }

    public String setRepeatTimes(int newRepeatTimes) {
        repeatTimes = newRepeatTimes;
        return toString();
    }

    public String setTags(TreeSet<String> tags) {
        this.tags = tags;
        return toString();
    }

    public String setPriority(Priority priority) {
        this.priority = priority;
        return toString();
    }

    /**
     * Adds the tags requested by the user to this task.
     *
     * @param tags The set containing the tags that the user wants to add to this task.
     * @return The summarised string representation of this task.
     */
    public String addTags(TreeSet<String> tags) {
        this.tags.addAll(tags);
        return toString();
    }

    /**
     * Removes the tags requested by the user from this task.
     *
     * @param tags The set containing the tags that the user wants to remove from this task.
     * @return The summarised string representation of this task.
     */
    public String removeTags(TreeSet<String> tags) {
        this.tags.removeAll(tags);
        return toString();
    }

    /**
     * Creates a predicate for checking if a task's deadline is before a given date.
     * Used for the progress command.
     *
     * @param date The date to compare to.
     * @return A predicate for checking if the task's deadline is before a given date.
     */
    public static Predicate<Task> beforeDate(LocalDate date) {
        return task -> task.deadline != null && task.deadline.toLocalDate().isBefore(date);
    }

    /**
     * Creates a predicate for checking if a task's deadline is after a given date.
     * Used for the progress command.
     *
     * @param date The date to compare to.
     * @return A predicate for checking if the task's deadline is after a given date.
     */
    public static Predicate<Task> afterDate(LocalDate date) {
        return task -> task.deadline != null && task.deadline.toLocalDate().isAfter(date);
    }

    /**
     * Creates a predicate for checking if a task is overdue, which means it is incomplete and due.
     *
     * @return A predicate for checking if a task is overdue.
     */
    //@@author clement559
    public static Predicate<Task> isOverdue() {
        return task -> !task.isDone && task.isDue();
    }

    /**
     * Creates a predicate for checking if a task is completed.
     *
     * @return A predicate for checking if a task is completed.
     */
    public static Predicate<Task> isDonePredicate() {
        return task -> task.isDone;
    }

    //@@author KedrianLoh
    /**
     * Creates a predicate for checking if a task's description contains a given string.
     *
     * @param description The string to check for.
     * @return A predicate for checking if a task's description contains a given string.
     */
    public static Predicate<Task> matchesDescription(String description) {
        return task -> task.description.contains(description);
    }

    /**
     * Creates a predicate for checking if a task has the given email address.
     *
     * @param email The email address to check for.
     * @return A predicate for checking if a task has the given email address.
     */
    public static Predicate<Task> matchesEmail(String email) {
        return task -> task.email != null && task.email.equals(email);
    }

    /**
     * Creates a predicate for checking if a task's deadline is before a given date-time.
     *
     * @param deadline The date-time to compare to.
     * @return A predicate for checking if the task's deadline is before a given date-time.
     */
    public static Predicate<Task> beforeDeadline(LocalDateTime deadline) {
        return task -> task.deadline != null && task.deadline.isBefore(deadline);
    }

    /**
     * Creates a predicate for checking if a task's deadline is after a given date-time.
     *
     * @param deadline The date-time to compare to.
     * @return A predicate for checking if the task's deadline is after a given date-time.
     */
    public static Predicate<Task> afterDeadline(LocalDateTime deadline) {
        return task -> task.deadline != null && task.deadline.isAfter(deadline);
    }

    /**
     * Creates a predicate for checking if a task is a recurring task.
     *
     * @return A predicate for checking if a task is a recurring task.
     */
    public static Predicate<Task> isRepeating() {
        return task -> task.repeatTimes > 0;
    }

    /**
     * Creates a predicate for checking if a task has all the given tags.
     *
     * @param tags The list of tags to check for.
     * @return A predicate for checking if a task has all the given tags.
     */
    public static Predicate<Task> matchesTags(TreeSet<String> tags) {
        return task -> task.tags.containsAll(tags);
    }

    /**
     * Creates a predicate for checking if a task has the given priority level.
     *
     * @param priority The priority level to check for.
     * @return A predicate for checking if a task has the given priority level.
     */
    public static Predicate<Task> matchesPriority(Priority priority) {
        return task -> task.priority.equals(priority);
    }
}
