package seedu.todolist;

import seedu.todolist.exception.FailedLoadException;
import seedu.todolist.exception.ToDoListException;
import seedu.todolist.logic.Parser;
import seedu.todolist.logic.command.Command;
import seedu.todolist.storage.Storage;
import seedu.todolist.task.TaskList;
import seedu.todolist.ui.Ui;

public class ToDoListManager {
    private boolean isRunning = true;
    private Parser parser = new Parser();
    private Storage storage = new Storage(Storage.DEFAULT_SAVE_PATH);
    private TaskList taskList = new TaskList();
    private Ui ui = new Ui();

    public ToDoListManager() {
        ui.printWelcomeMessage();

        if (storage.isNewSave()) {
            ui.printNewSaveMessage();
            return;
        }
        try {
            taskList = storage.loadData();
            ui.printLoadSaveMessage(taskList.size());
        } catch (FailedLoadException e) {
            ui.printError(e);
        }
    }

    public void run() {
        while (isRunning) {
            String inputCommand = ui.getUserInput();
            try {
                Command command = parser.parseCommand(inputCommand);
                command.execute(taskList, ui);
                storage.saveData(taskList);
                isRunning = !command.shouldExit();
            } catch (ToDoListException e) {
                ui.printError(e);
            }
        }
        ui.close();
    }
}
