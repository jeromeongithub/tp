package seedu.todolist.logic.command;

import seedu.todolist.exception.InvalidIndexException;
import seedu.todolist.ui.Ui;
import seedu.todolist.task.TaskList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

//@@author RuiShengGit
public class MarkTaskCommand extends Command{
    public static final String KEYWORD = "mark";
    public static final HashSet<String> FLAGS = new HashSet<>(Arrays.asList(KEYWORD));

    private int index;

    public MarkTaskCommand(HashMap<String, String> args) throws InvalidIndexException {
        try {
            index = Integer.parseInt(args.get(KEYWORD)) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidIndexException();
        }
        assert index >= 0 : "Invalid index contained in variable";
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws InvalidIndexException {
        String taskString = taskList.setDone(index, true);
        ui.printMarkTaskMessage(taskString);
    }
}