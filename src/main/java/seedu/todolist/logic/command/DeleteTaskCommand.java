package seedu.todolist.logic.command;

import seedu.todolist.exception.InvalidIndexException;
import seedu.todolist.ui.Ui;
import seedu.todolist.task.TaskList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

//@@author RuiShengGit
public class DeleteTaskCommand extends Command {
    public static final String KEYWORD = "delete";
    public static final HashSet<String> FLAGS = new HashSet<>(Arrays.asList(KEYWORD));

    private int index;

    public DeleteTaskCommand(HashMap<String, String> args) throws InvalidIndexException {
        try {
            index = Integer.parseInt(args.get(KEYWORD)) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidIndexException();
        }
        assert index >= 0: "Invalid index contained in variable";
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws InvalidIndexException {
        String taskString = taskList.deleteTask(index);
        ui.printDeleteTaskMessage(taskString);
    }
}