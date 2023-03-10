package seedu.duke.command;

import seedu.duke.ui.Ui;
import seedu.duke.task.TaskList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class EditDeadlineCommand extends Command  {
    public static final String KEYWORD = "editdeadline";
    public static final HashSet<String> FLAGS = new HashSet<>(Arrays.asList(KEYWORD, "-d"));
    public static int index;
    public static String deadline;


    public EditDeadlineCommand(String[] splitInput) {

        HashMap<String, String> args = CommandParser.getArguments(splitInput, FLAGS);
        index = Integer.parseInt(args.get(KEYWORD)) - 1;
        deadline = CommandParser.formatDateTime(args.get("-d"));
    }
    @Override
    public void execute(TaskList taskList, Ui ui) {
        if (deadline.isEmpty()) {
            ui.printDateTimeError();
        } else {
            String taskItem = taskList.editDeadline(index, deadline);
            ui.printEditDeadlineNotification(taskItem);
        }

    }
}
