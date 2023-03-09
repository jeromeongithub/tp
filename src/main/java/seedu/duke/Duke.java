package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.CommandParser;
import seedu.duke.task.TaskList;

import java.util.Scanner;

public class Duke {
    private static boolean isInUse = true;

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.printWelcomeMessage();
        Scanner in = new Scanner(System.in);
        TaskList taskList = Storage.loadData("./data.txt", ui);
        while (isInUse) {
            String userInput = in.nextLine();
            Command parsedCommand = CommandParser.parseCommand(userInput);
            parsedCommand.execute(taskList, ui);
            Storage.saveData("./data.txt", taskList, ui);
            if (parsedCommand.isExit()) {
                in.close();
            }
            isInUse = !parsedCommand.isExit();
        }
    }
}

