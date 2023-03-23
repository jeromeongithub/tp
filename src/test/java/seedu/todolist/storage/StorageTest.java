package seedu.todolist.storage;

import org.junit.jupiter.api.Test;
import seedu.todolist.logic.Parser;
import seedu.todolist.logic.command.Command;
import seedu.todolist.task.TaskList;
import seedu.todolist.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class StorageTest {
    private static final String PROPER_SAVE_FILE = "./src/test/data/properSaveFile.txt";
    private static final String INVALID_SAVE_FILE = "./src/test/data/invalidSaveFile.txt";
    private static final String TEST_DATA_FOLDER = "./src/test/data";
    private TaskList taskList = new TaskList();
    private Ui ui = new Ui();
    private Parser parser = new Parser();
    private Storage storage = new Storage(PROPER_SAVE_FILE);

    @Test
    void saveAndLoadData_properFilePath_success() {
        try {
            String sampleInput1 = "add bubu -due 18-02-2022 18:00";
            Command command1 = parser.parseCommand(sampleInput1);
            command1.execute(taskList, ui);
            String sampleInput2 = "add baba -due 18-04-2023 12:00";
            Command command2 = parser.parseCommand(sampleInput2);
            command2.execute(taskList, ui);
            storage.saveData(taskList); // save modified task list object
            TaskList testTaskList = storage.loadData(); // load the saved task list into another task list object
            boolean isActualSaveEqualExpectedSave = true;
            for (int i = 0; i < taskList.size(); i++) {
                String string1 = taskList.getTask(i).toString();
                String string2 = testTaskList.getTask(i).toString();
                if (!(string1.equals(string2))) { // need to compare the strings since the 2 objects are not equal
                    isActualSaveEqualExpectedSave = false;
                }
            }

            if (taskList.size() != testTaskList.size()) {
                isActualSaveEqualExpectedSave = false; // size of the task lists should be the same too
            }

            assertEquals(true, isActualSaveEqualExpectedSave);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void saveData_directoryAsFilePath_exceptionThrown() { // if the path exists but is a directory, not a regular file
        try {
            Storage storageDirectory = new Storage(TEST_DATA_FOLDER);
            storageDirectory.saveData(taskList);
            fail(); // the test should not reach this line
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void loadData_fileWithInvalidData_exceptionThrown() {
        try {
            Storage storageInvalid = new Storage(INVALID_SAVE_FILE);
            taskList = storageInvalid.loadData();
            fail(); // should not reach this line
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
