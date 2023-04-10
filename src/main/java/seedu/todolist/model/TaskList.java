package seedu.todolist.model;

import seedu.todolist.constants.Formats;
import seedu.todolist.constants.Messages;
import seedu.todolist.exception.InvalidDateException;
import seedu.todolist.exception.InvalidIdException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A list of Task objects representing the current list of tasks.
 */
public class TaskList {
    private int allocatedIds = 0;
    private TreeMap<Integer, Task> tasks = new TreeMap<>();

    public void reset() {
        allocatedIds = 0;
        tasks.clear();
    }

    /**
     * Returns a stream of tasks with the given ids.
     *
     * @param ids The ids of the tasks to return.
     * @return A stream of tasks with the given ids.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    private Stream<Task> getTasks(HashSet<Integer> ids) throws InvalidIdException {
        for (int id : ids) {
            if (!tasks.containsKey(id)) {
                throw new InvalidIdException(id);
            }
        }
        return ids.stream().map(tasks::get);
    }

    private void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public String addTask(String description, LocalDateTime deadline, String email, TreeSet<String> tags,
                          int repeatTimes, Priority priority) {
        Task task = new Task(++allocatedIds, description, deadline, email, tags, repeatTimes, priority);
        tasks.put(allocatedIds, task);
        return task.toString();
    }

    /**
     * Deletes the task at the given ids of the task list.
     *
     * @param ids The ids of the task to be deleted.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String deleteTask(HashSet<Integer> ids) throws InvalidIdException {
        String taskString = joinStringStream(getTasks(ids).map(Task::toString));
        tasks.keySet().removeAll(ids);
        return taskString;
    }

    public String deleteTask(Predicate<Task> p) {
        String taskString = joinStringStream(tasks.values().stream().filter(p).map(Task::toString));
        tasks.values().removeIf(p);
        return taskString;
    }

    /**
     * Returns the number of tasks in this task list.
     *
     * @return The number of tasks in this task list.
     */
    public int size() {
        return tasks.size();
    }

    public int size(Predicate<Task> p) {
        return (int) tasks.values().stream().filter(p).count();
    }

    //@@author clement559
    /**
     * Joins a stream of strings together with numbering (#1, #2, etc...).
     *
     * @param stream The stream of strings to join together.
     * @return A single string composed of numbered strings.
     */
    private String joinStringStream(Stream<String> stream) {
        AtomicInteger count = new AtomicInteger(1);
        return stream.map(string -> String.format(Formats.TASK_STRING_INDEXED, count.getAndIncrement(), string))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Converts the task list into its string representation.
     *
     * @return String representation of the task list.
     */
    public String toString() {
        return joinStringStream(tasks.values().stream().map(Task::toString));
    }

    /**
     * Filters the task list using a predicate before converting it into its string representation.
     *
     * @param p The predicate to filter the task list with.
     * @return Filtered string representation of the task list.
     */
    public String toString(Predicate<Task> p) {
        return joinStringStream(tasks.values().stream().filter(p).map(Task::toString));
    }

    //@@author KedrianLoh
    /**
     * Sorts the task list using a comparator before converting it into its sorted string representation.
     *
     * @param c The comparator to sort the task list with.
     * @return Sorted string representation of the task list.
     */
    public String toString(Comparator<Task> c) {
        return joinStringStream(tasks.values().stream().sorted(c).map(Task::toString));
    }

    /**
     * Filters the task list using a predicate and comparator before converting it
     * into its sorted string representation.
     *
     * @param p The predicate to filter the task list with.
     * @param c The comparator to sort the task list with.
     * @return Filtered string representation of the task list.
     */
    public String toString(Predicate<Task> p, Comparator<Task> c) {
        return joinStringStream(tasks.values().stream().filter(p).sorted(c).map(Task::toString));
    }

    /**
     * Filters the task list using the given ids before converting it into its string representation.
     *
     * @param ids The ids to filter the task list with.
     * @return Filtered string representation of the task list.
     * @throws InvalidIdException If there is no task with any of the given ids.
     */
    public String toString(HashSet<Integer> ids) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(Task::toString));
    }

    /**
     * Filters the task list using the given ids and comparator before converting it
     * into its sorted string representation.
     *
     * @param ids The ids to filter the task list with.
     * @param c The comparator to sort the task list with.
     * @return Filtered string representation of the task list.
     * @throws InvalidIdException If there is no task with any of the given ids.
     */
    public String toString(HashSet<Integer> ids, Comparator<Task> c) throws InvalidIdException {
        return joinStringStream(getTasks(ids).sorted(c).map(Task::toString));
    }

    /**
     * Gets the full information of all tasks with the given ids.
     *
     * @param ids The ids of the tasks to get the full information of.
     * @return A combined string of the full information of all tasks with the given ids.
     * @throws InvalidIdException If there is no task with any of the given ids.
     */
    public String getFullInfo(HashSet<Integer> ids) throws InvalidIdException {
        return getTasks(ids).map(Task::getFullInfo)
                .collect(Collectors.joining(System.lineSeparator() + Messages.LINE + System.lineSeparator()));
    }

    /**
     * Gets the full information of all tasks fulfilling the given predicate.
     *
     * @param p The predicate used to filter the task list.
     * @return A combined string of the full information of all tasks fulfilling the given predicate.
     */
    public String getFullInfo(Predicate<Task> p) {
        return tasks.values().stream().filter(p).map(Task::getFullInfo)
                .collect(Collectors.joining(System.lineSeparator() + Messages.LINE + System.lineSeparator()));
    }

    //@@author ERJUNZE
    /**
     * Gets the list of tags that tasks in the task list have.
     *
     * @return A TreeSet containing all the tags that tasks in the task list have.
     */
    public TreeSet<String> getAllTags() {
        TreeSet<String> tags = new TreeSet<>();
        tasks.values().forEach(task -> tags.addAll(task.getTags()));
        return tags;
    }

    /**
     * Sets the description for the tasks with the given ids.
     *
     * @param ids The ids of the tasks to set the description for.
     * @param description The description to set for the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String setDescription(HashSet<Integer> ids, String description) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.setDescription(description)));
    }

    /**
     * Sets the description for the tasks fulfilling the given predicate.
     *
     * @param p The predicate that tasks need to fulfil to have their description updated.
     * @param description The description to set for the tasks.
     * @return A string of all the targeted tasks.
     */
    public String setDescription(Predicate<Task> p, String description) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setDescription(description)));
    }

    /**
     * Sets the tags for the tasks with the given ids.
     *
     * @param ids The ids of the tasks to set the tags for.
     * @param tags The list of tags to set for the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String setTags(HashSet<Integer> ids, TreeSet<String> tags) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.setTags(tags)));
    }

    /**
     * Sets the tags for the tasks fulfilling the given predicate.
     *
     * @param p The predicate that tasks need to fulfil to have their tags updated.
     * @param tags The list of tags to set for the tasks.
     * @return A string of all the targeted tasks.
     */
    public String setTags(Predicate<Task> p, TreeSet<String> tags) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setTags(tags)));
    }

    //@@author RuiShengGit
    /**
     * Sets the email address for the tasks with the given ids.
     *
     * @param ids The ids of the tasks to set the email address for.
     * @param email The email address to set for the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String setEmail(HashSet<Integer> ids, String email) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.setEmail(email)));
    }

    /**
     * Sets the email address for the tasks fulfilling the given predicate.
     *
     * @param p The predicate that tasks need to fulfil to have their email address updated.
     * @param email The email address to set for the tasks.
     * @return A string of all the targeted tasks.
     */
    public String setEmail(Predicate<Task> p, String email) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setEmail(email)));
    }

    /**
     * Sets the priority level for the tasks with the given ids.
     *
     * @param ids The ids of the tasks to set the priority level for.
     * @param priority  The priority level to set for the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String setPriority(HashSet<Integer> ids, Priority priority) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.setPriority(priority)));
    }

    /**
     * Sets the priority level for the tasks fulfilling the given predicate.
     *
     * @param p The predicate that tasks need to fulfil to have their priority level updated.
     * @param priority The priority level to set for the tasks.
     * @return A string of all the targeted tasks.
     */
    public String setPriority(Predicate<Task> p, Priority priority) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setPriority(priority)));
    }

    /**
     * Sets the completion status for the tasks with the given ids.
     *
     * @param ids The ids of the tasks to set the completion status for.
     * @param isDone The completion status to set for the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String setDone(HashSet<Integer> ids, boolean isDone) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.setDone(isDone)));
    }

    /**
     * Sets the completion status for the tasks fulfilling the given predicate.
     *
     * @param p The predicate that tasks need to fulfil to have their completion status updated.
     * @param isDone The completion status to set for the tasks.
     * @return A string of all the targeted tasks.
     */
    public String setDone(Predicate<Task> p, boolean isDone) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setDone(isDone)));
    }

    //@@author clement559
    /**
     * Sets deadline for the task ids provided.
     *
     * @param ids The ids of the tasks to set the deadline for.
     * @param deadline The deadline to be set for the tasks.
     * @return A stream of tasks with the given ids.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String setDeadline(HashSet<Integer> ids, LocalDateTime deadline) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.setDeadline(deadline)));
    }

    public String setDeadline(Predicate<Task> p, LocalDateTime deadline) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setDeadline(deadline)));
    }

    /**
     * Sets repeat duration for the task ids provided if the task contains a deadline.
     *
     * @param ids The ids of the tasks to set the repeat duration for.
     * @param repeatTimes The repeat duration to be set for the tasks.
     * @return A string of all the targeted tasks
     * @throws InvalidIdException If there is no task with any of the provided ids.
     * @throws InvalidDateException If the task with the provided ids does not have a deadline.
     */
    public String setRepeatTimes(HashSet<Integer> ids, int repeatTimes)
            throws InvalidDateException, InvalidIdException {
        Task noDeadlineTask = getTasks(ids).filter(task -> task.getDeadline() == null).findFirst().orElse(null);
        if (noDeadlineTask != null) {
            throw new InvalidDateException("Task with ID " + noDeadlineTask.id + " has no deadline.");
        }
        return joinStringStream(getTasks(ids).map(task -> task.setRepeatTimes(repeatTimes)));
    }

    /**
     * Sets repeat duration for the task ids provided if the task contains a deadline.
     *
     * @param p The ids of the tasks to set the repeat duration for.
     * @param repeatTimes The repeat duration to be set for the tasks.
     * @return A string of all the targeted tasks
     * @throws InvalidDateException If the task with the provided ids does not have a deadline.
     */
    public String setRepeatTimes(Predicate<Task> p, int repeatTimes) throws InvalidDateException {
        Task noDeadlineTask = tasks.values().stream().filter(p).
                filter(task -> task.getDeadline() == null).findFirst().orElse(null);
        if (noDeadlineTask != null) {
            throw new InvalidDateException("Task with ID " + noDeadlineTask.id + " has no deadline.");
        }
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.setRepeatTimes(repeatTimes)));
    }

    //@@author jeromeongithub
    /**
     * Adds the given tags to the tasks with the given ids.
     *
     * @param ids The ids of the tasks to add the tags to.
     * @param tags The list of tags to add to the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String addTags(HashSet<Integer> ids, TreeSet<String> tags) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.addTags(tags)));
    }

    /**
     * Adds the given tags to the tasks fulfilling the given predicate.
     *
     * @param p The predicate that tasks need to fulfil to have tags added to them.
     * @param tags The list of tags to add to the tasks.
     * @return A string of all the targeted tasks.
     */
    public String addTags(Predicate<Task> p, TreeSet<String> tags) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.addTags(tags)));
    }

    /**
     * Removes the given tags from the tasks with the given ids.
     *
     * @param ids The ids of the tasks to remove the tags from.
     * @param tags The list of tags to remove from the tasks.
     * @return A string of all the targeted tasks.
     * @throws InvalidIdException If there is no task with any of the provided ids.
     */
    public String removeTags(HashSet<Integer> ids, TreeSet<String> tags) throws InvalidIdException {
        return joinStringStream(getTasks(ids).map(task -> task.removeTags(tags)));
    }

    /**
     * Removes the given tags from the tasks with the given ids.
     *
     * @param p The predicate that tasks need to fulfil to have tags removed from them.
     * @param tags The list of tags to remove from the tasks.
     * @return A string of all the targeted tasks.
     */
    public String removeTags(Predicate<Task> p, TreeSet<String> tags) {
        return joinStringStream(tasks.values().stream().filter(p).map(task -> task.removeTags(tags)));
    }

    /**
     * Checks if this task list is still valid after loading it from the save file.
     * Certain attributes if deleted or edited to invalid values will cause the task list to be invalid.
     *
     * @return If this task list is still valid.
     */
    public boolean isValid() {
        if (!tasks.isEmpty() && tasks.lastKey() > allocatedIds) {
            // Cannot have id larger than number of allocated ids
            return false;
        }
        for (Map.Entry<Integer, Task> e : tasks.entrySet()) {
            // Id key must match the task id
            if (e.getKey() != e.getValue().getId() || !e.getValue().isValid()) {
                return false;
            }
        }
        return true;
    }

    //@@author clement559
    /**
     * Checks through the list of tasks if there are any new repeating tasks to be created.
     * Creates the new tasks if the original deadline of the task has passed.
     *
     * @param config Configuration file containing repeat frequency specified by the user.
     */
    public void checkRepeatingTasks(Config config) {
        ArrayList<Task> tasksToBeAdded = new ArrayList<>();
        for (Task task : tasks.values()) {
            int repeatTimes = task.getRepeatTimes();
            LocalDateTime deadline = task.getDeadline();
            // Check if this is a recurring task that is past its deadline
            if (repeatTimes == 0 || !task.isDue()) {
                continue;
            }

            // Remove recur duration from this task to avoid duplicates on next check
            task.setRepeatTimes(0);
            while (repeatTimes > 0 && !deadline.isAfter(LocalDateTime.now())) {
                // Calculate new deadline and recur duration
                deadline = deadline.plusDays(config.getRepeatFrequency());
                repeatTimes--;
                // Hold new task in temp array to avoid concurrent modification exception
                tasksToBeAdded.add(new Task(++allocatedIds, task.getDescription(), deadline,
                        task.getEmail(), task.getTags(), 0, task.getPriority()));
            }
            tasksToBeAdded.get(tasksToBeAdded.size() - 1).setRepeatTimes(repeatTimes);
        }

        for (Task task : tasksToBeAdded) {
            addTask(task);
        }
        config.setLastChecked(LocalDateTime.now());
    }
}
