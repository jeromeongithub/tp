package seedu.todolist.logic.command;

import seedu.todolist.constants.Flags;
import seedu.todolist.exception.InvalidIdException;
import seedu.todolist.logic.ParserUtil;
import seedu.todolist.ui.Ui;
import seedu.todolist.task.TaskList;

import java.util.HashMap;

//@@author RuiShengGit
public class UnmarkTaskCommand extends Command{
    public static final Flags[] EXPECTED_FLAGS = {Flags.COMMAND_UNMARK};

    private int id;

    public UnmarkTaskCommand(HashMap<Flags, String> args) throws InvalidIdException {
        id = ParserUtil.parseId(args.get(Flags.COMMAND_UNMARK));
        assert id >= 0 : "Invalid id contained in variable";
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws InvalidIdException {
        String taskString = taskList.setDone(id, false);
        ui.printUnmarkTaskMessage(taskString);
    }
}
