//@@author jeromeongithub
package seedu.todolist.logic.command;

import seedu.todolist.model.Config;
import seedu.todolist.model.Task;
import seedu.todolist.model.TaskList;
import seedu.todolist.ui.Ui;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * Command class that will display the progress of tasks in the given TaskList object that are due this week.
 */
public class ProgressBarCommand extends Command {
    private static final int PROGRESS_BAR_SECTIONS = 50;

    Predicate<Task> isInThisWeek;

    /**
     * Constructs a ProgressBarCommand that will display the tasks that are due this week,
     * and a progress bar. "This week" is defined as from this Monday to this Sunday.
     */
    public ProgressBarCommand() {
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        // Before Monday (start of the week - 1st day)
        LocalDate beforeStartOfWeek = LocalDate.now().with(fieldISO, 1).minusDays(1);
        // After Sunday (end of the week - 7th day)
        LocalDate afterEndOfWeek = beforeStartOfWeek.plusDays(8);
        isInThisWeek = Task.afterDate(beforeStartOfWeek).and(Task.beforeDate(afterEndOfWeek));
    }

    /**
     * Displays a summary of tasks due this week and a progress bar.
     *
     * @param taskList The task list to check for tasks that are due this week.
     * @param ui The Ui object used to display the tasks due this week and the progress bar.
     */
    @Override
    public void execute(TaskList taskList, Config config, Ui ui) {
        int tasksThisWeek = taskList.size(isInThisWeek);
        int completedTasksThisWeek = taskList.size(isInThisWeek.and(Task.isDonePredicate()));
        ui.printProgressBar(completedTasksThisWeek, tasksThisWeek,
                PROGRESS_BAR_SECTIONS, taskList.toString(isInThisWeek, Task.deadlineComparator));
    }
}
