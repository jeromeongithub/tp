package seedu.todolist.task;

import seedu.todolist.exception.InvalidIndexException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * A list of Task objects representing the current list of tasks.
 */
public class TaskList implements Serializable {
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Converts the task list into its string representation.
     *
     * @return String representation of the task list.
     */
    public String toString() {
        StringJoiner taskListString = new StringJoiner(System.lineSeparator());
        for (int i = 0; i < size(); i++) {
            taskListString.add((i + 1) + ". " + tasks.get(i).toString());
        }
        return taskListString.toString();
    }

    /**
     * Returns the number of tasks in this task list.
     *
     * @return The number of tasks in this task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given index of the task list.
     *
     * @param index The index of the task to return.
     *              Must be between 1 and the size of the task list.
     * @return The task at the given index of the task list.
     */
    public Task getTask(int index) throws InvalidIndexException {
        if (index < 0 || index > tasks.size() - 1) {
            throw new InvalidIndexException();
        }
        return tasks.get(index);
    }

    /**
     * Appends the given task to the task list.
     *
     * @param task The task to be added to the task list.
     * @return String representation of the task that was added.
     */
    public String addTask(Task task) {
        tasks.add(task);
        return task.toString();
    }

    /**
     * Sets the completion status of the task at the given index of the task list.
     *
     * @param index The index of the task whose completion status should be set.
     *              Must be between 1 and the size of the task list.
     * @param isDone Whether the task should be marked as completed.
     * @return String representation of the task whose completion status was set.
     */
    public String setDone(int index, boolean isDone) throws InvalidIndexException {
        if (index < 0 || index > tasks.size() - 1) {
            throw new InvalidIndexException();
        }
        tasks.get(index).setDone(isDone);
        assert tasks.get(index).isDone() == isDone;
        return tasks.get(index).toString();
    }

    /**
     * Deletes the task at the given index of the task list.
     *
     * @param index The index of the task to be deleted.
     *              Must be between 1 and the size of the task list.
     * @return String representation of the task that was deleted.
     */
    public String deleteTask(int index) throws InvalidIndexException {
        if (index < 0 || index > size() - 1) {
            throw new InvalidIndexException();
        }
        String taskString = tasks.get(index).toString();
        tasks.remove(index);
        return taskString;
    }

    /**
     * Replace deadline of task at the given index of the task list.
     *
     * @param index The index of the task whose deadline should be changed.
     *              Must be between 1 and the size of the task list.
     * @return String representation of the task whose deadline was changed.
     */
    public String editDeadline(int index, LocalDateTime deadline) throws InvalidIndexException {
        if (index < 0 || index > tasks.size() - 1) {
            throw new InvalidIndexException();
        }
        tasks.get(index).setDeadline(deadline);
        return tasks.get(index).toString();
    }

    //@@author KedrianLoh
    /**
     * Sorts the ArrayList tasks, by the corresponding Task deadlines
     */
    public void sortByDeadline() {
        tasks.sort(Task.taskDeadlineComparator);
    }
}
